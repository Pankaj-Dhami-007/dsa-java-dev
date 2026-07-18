package springboot_deep_drive.caching.springboot.local;

/*
 * ============================================================================
 *  SPRING BOOT LOCAL CACHE — COMPLETE SERVICE EXAMPLE
 *  Shows a real-world ProductService with all caching patterns together.
 * ============================================================================
 *
 *  WHAT THIS COVERS:
 *  - @Cacheable with sync=true (thundering herd protection)
 *  - @CachePut with #result (auto-generated IDs)
 *  - @CacheEvict single + allEntries
 *  - @Caching (multiple cache updates)
 *  - @CacheConfig (class-level defaults)
 *  - Custom key generation (composite keys)
 *  - Conditional caching (condition + unless)
 *  - Cache eviction on scheduled task
 *
 *  SCENARIO: Product Catalog Service
 *  - Products by ID, SKU, Category
 *  - Search results cached
 *  - Stock counts cached (volatile, short TTL)
 *  - Bulk import clears search caches
 *
 * ============================================================================
 *  CACHE NAMES & CONFIGURATION
 * ============================================================================
 *
 *  ┌─────────────────┬──────────┬──────────────────────────┬──────────┐
 *  │ Cache Name      │ Size     │ TTL                      │ Purpose  │
 *  ├─────────────────┼──────────┼──────────────────────────┼──────────┤
 *  │ products        │ 50,000   │ 1 hour                   │ By ID    │
 *  │ products:sku    │ 50,000   │ 1 hour                   │ By SKU   │
 *  │ categories      │ 1,000    │ 6 hours                  │ Category │
 *  │ productSearch   │ 10,000   │ 10 minutes               │ Search   │
 *  │ stock           │ 100,000  │ 1 minute                 │ Stock    │
 *  └─────────────────┴──────────┴──────────────────────────┴──────────┘
 *
 * ============================================================================
 *  COMPLETE SERVICE CODE (as comments — Spring dependency not available)
 * ============================================================================
 *
 *  @Service
 *  @CacheConfig(cacheNames = "products")
 *  @Slf4j
 *  public class ProductCachingService {
 *
 *      private final ProductRepository repository;
 *
 *      // ════════════════════════════════════════════════════════════
 *      //  READ OPERATIONS
 *      // ════════════════════════════════════════════════════════════
 *
 *      // Get by ID — with thundering herd protection
 *      @Cacheable(key = "#id", sync = true)
 *      public Product getProduct(Long id) {
 *          log.info("Fetching product {} from DB", id);
 *          return repository.findById(id).orElse(null);
 *      }
 *
 *      // Get by SKU — different cache name, custom key
 *      @Cacheable(cacheNames = "products:sku", key = "#sku", unless = "#result == null")
 *      public Product getProductBySku(String sku) {
 *          return repository.findBySku(sku);
 *      }
 *
 *      // Get products by category — condition + unless
 *      @Cacheable(cacheNames = "categories",
 *                 key = "#categoryId + ':' + #page",
 *                 condition = "#page < 100",
 *                 unless = "#result == null or #result.isEmpty()")
 *      public List<Product> getProductsByCategory(Long categoryId, int page) {
 *          return repository.findByCategory(categoryId, PageRequest.of(page, 20));
 *      }
 *
 *      // ════════════════════════════════════════════════════════════
 *      //  WRITE OPERATIONS
 *      // ════════════════════════════════════════════════════════════
 *
 *      // Create — cache by ID + SKU
 *      @Caching(put = {
 *          @CachePut(key = "#result.id"),
 *          @CachePut(cacheNames = "products:sku", key = "#result.sku")
 *      })
 *      public Product createProduct(Product product) {
 *          return repository.save(product);
 *      }
 *
 *      // Update — cache by ID, evict search
 *      @Caching(
 *          put = @CachePut(key = "#product.id"),
 *          evict = @CacheEvict(cacheNames = "productSearch", allEntries = true)
 *      )
 *      public Product updateProduct(Product product) {
 *          return repository.save(product);
 *      }
 *
 *      // ════════════════════════════════════════════════════════════
 *      //  DELETE OPERATIONS
 *      // ════════════════════════════════════════════════════════════
 *
 *      // Delete single — evict from all related caches
 *      @Caching(evict = {
 *          @CacheEvict(key = "#id"),
 *          @CacheEvict(cacheNames = "products:sku", key = "#sku"),
 *          @CacheEvict(cacheNames = "productSearch", allEntries = true)
 *      })
 *      public void deleteProduct(Long id, String sku) {
 *          repository.deleteById(id);
 *      }
 *
 *      // ════════════════════════════════════════════════════════════
 *      //  BULK / ADMIN OPERATIONS
 *      // ════════════════════════════════════════════════════════════
 *
 *      // Bulk import — evict everything (cache will warm up lazily)
 *      @CacheEvict(cacheNames = {"products", "products:sku",
 *                                 "categories", "productSearch"},
 *                  allEntries = true, beforeInvocation = true)
 *      @Transactional
 *      public void bulkImport(List<Product> products) {
 *          repository.saveAll(products);
 *      }
 *
 *      // ════════════════════════════════════════════════════════════
 *      //  SCHEDULED TASKS
 *      // ════════════════════════════════════════════════════════════
 *
 *      // Nightly cache refresh for stale categories
 *      @CacheEvict(cacheNames = "categories", allEntries = true)
 *      @Scheduled(cron = "0 0 3 * * ?")
 *      public void refreshCategoryCache() {
 *          log.info("Categories cache cleared for nightly refresh");
 *      }
 *  }
 *
 * ============================================================================
 *  CAFFEINE CONFIG FOR THIS SERVICE
 * ============================================================================
 *
 *  @Configuration
 *  public class CacheConfig {
 *
 *      @Bean
 *      public CacheManager cacheManager() {
 *          SimpleCacheManager cm = new SimpleCacheManager();
 *
 *          cm.setCaches(Arrays.asList(
 *              buildCache("products", 50_000, 1, TimeUnit.HOURS),
 *              buildCache("products:sku", 50_000, 1, TimeUnit.HOURS),
 *              buildCache("categories", 1_000, 6, TimeUnit.HOURS),
 *              buildCache("productSearch", 10_000, 10, TimeUnit.MINUTES),
 *              buildCache("stock", 100_000, 1, TimeUnit.MINUTES)
 *          ));
 *
 *          return cm;
 *      }
 *
 *      private CaffeineCache buildCache(String name, int maxSize,
 *                                         int ttl, TimeUnit unit) {
 *          return new CaffeineCache(name, Caffeine.newBuilder()
 *              .maximumSize(maxSize)
 *              .expireAfterWrite(ttl, unit)
 *              .recordStats()
 *              .build());
 *      }
 *  }
 *
 * ============================================================================
 *  PERFORMANCE TARGETS (For this service)
 * ============================================================================
 *
 *  ┌─────────────────┬──────────┬──────────┬──────────┬──────────┐
 *  │ Operation       │ Without  │ With     │ Improve  │ Hit Rate │
 *  │                 │ Cache    │ Cache    │          │ Target   │
 *  ├─────────────────┼──────────┼──────────┼──────────┼──────────┤
 *  │ getProduct()    │ 50ms     │ <1ms     │ 50x      │ > 95%    │
 *  │ getBySku()      │ 40ms     │ <1ms     │ 40x      │ > 90%    │
 *  │ getByCategory() │ 80ms     │ <1ms     │ 80x      │ > 85%    │
 *  │ search()        │ 200ms    │ <5ms     │ 40x      │ > 80%    │
 *  │ stock check()   │ 10ms     │ <1ms     │ 10x      │ > 99%    │
 *  └─────────────────┴──────────┴──────────┴──────────┴──────────┘
 *
 * ============================================================================
 *  INTERVIEW: "Design a caching strategy for an e-commerce product API"
 * ============================================================================
 *
 *  SCRIPT:
 *  "For an e-commerce product API, I'd design a multi-tier cache:
 *
 *   TIER 1 — Local Cache (Caffeine):
 *     - Product details by ID: 50K entries, 1h TTL, sync=true
 *     - Category listings: 1K entries, 6h TTL
 *     - Stock counts: 100K entries, 1min TTL
 *
 *   TIER 2 — Distributed Cache (Redis — later):
 *     - User sessions, cart data (shared across instances)
 *     - Search index (too large for local)
 *
 *   CACHE STRATEGY:
 *     - Reads: @Cacheable with sync=true (thundering herd protection)
 *     - Writes: @CachePut for product updates (eager refresh)
 *     - Deletes: @CacheEvict cascade (product + sku + search caches)
 *     - Bulk: @CacheEvict(allEntries=true) before import
 *
 *   MONITORING:
 *     - recordStats() on Caffeine
 *     - Micrometer exports hitRate, evictionCount, loadPenalty
 *     - Target: 95%+ hit rate for products, 80%+ for search
 *
 *   If hit rate drops below 90%, I increase cache size or extend TTL.
 *   If eviction rate is high, I check if cache is too small or data is
 *   being scanned (one-time reads polluting the cache)."
 */

public class ProductCachingServiceExample {

    static final String COMPLETE_SERVICE = """
        @Service
        @CacheConfig(cacheNames = \"products\")
        public class ProductCachingService {

            // ═══════════════ READ ═══════════════

            @Cacheable(key = \"#id\", sync = true)
            public Product getProduct(Long id) { ... DB ... }

            @Cacheable(cacheNames = \"products:sku\", key = \"#sku\")
            public Product getBySku(String sku) { ... DB ... }

            @Cacheable(cacheNames = \"categories\",
                       key = \"#catId + ':' + #page\",
                       condition = \"#page < 100\")
            public List<Product> getByCategory(Long catId, int page) { ... }

            // ═══════════════ WRITE ═══════════════

            @Caching(put = {
                @CachePut(key = \"#result.id\"),
                @CachePut(cacheNames = \"products:sku\", key = \"#result.sku\")
            })
            public Product createProduct(Product p) { ... DB ... }

            @Caching(
                put = @CachePut(key = \"#p.id\"),
                evict = @CacheEvict(cacheNames = \"productSearch\",
                                    allEntries = true)
            )
            public Product updateProduct(Product p) { ... DB ... }

            // ═══════════════ DELETE ═══════════════

            @Caching(evict = {
                @CacheEvict(key = \"#id\"),
                @CacheEvict(cacheNames = \"products:sku\", key = \"#sku\"),
                @CacheEvict(cacheNames = \"productSearch\",
                            allEntries = true)
            })
            public void deleteProduct(Long id, String sku) { ... DB ... }

            // ═══════════════ BULK ═══════════════

            @CacheEvict(cacheNames = {\"products\", \"products:sku\",
                                       \"categories\", \"productSearch\"},
                        allEntries = true, beforeInvocation = true)
            @Transactional
            public void bulkImport(List<Product> products) { ... DB ... }

            // ═══════════════ SCHEDULED ═══════════════

            @CacheEvict(cacheNames = \"categories\", allEntries = true)
            @Scheduled(cron = \"0 0 3 * * ?\")
            public void nightlyRefresh() { }
        }
        """;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   PRODUCT CATALOG — Complete Caching Demo");
        System.out.println("============================================");

        System.out.println("\n=== Cache Configuration ===");
        System.out.println("  ┌─────────────────┬──────────┬──────────────┬──────────┐");
        System.out.println("  │ Cache Name      │ Max Size │ TTL          │ Strategy │");
        System.out.println("  ├─────────────────┼──────────┼──────────────┼──────────┤");
        System.out.println("  │ products        │ 50,000   │ 1 hour       │ sync     │");
        System.out.println("  │ products:sku    │ 50,000   │ 1 hour       │ standard │");
        System.out.println("  │ categories      │ 1,000    │ 6 hours      │ lazy     │");
        System.out.println("  │ productSearch   │ 10,000   │ 10 minutes   │ full     │");
        System.out.println("  │ stock           │ 100,000  │ 1 minute     │ short    │");
        System.out.println("  └─────────────────┴──────────┴──────────────┴──────────┘");

        System.out.println("\n=== Complete Service Implementation ===");
        System.out.println(COMPLETE_SERVICE);

        System.out.println("\n=== PERFORMANCE IMPROVEMENT ===");
        System.out.println("  ┌─────────────────┬──────────┬──────────┬──────────┐");
        System.out.println("  │ Operation       │ Without  │ With     │ Speedup  │");
        System.out.println("  ├─────────────────┼──────────┼──────────┼──────────┤");
        System.out.println("  │ getProduct()    │ 50ms     │ <1ms     │ 50x      │");
        System.out.println("  │ getBySku()      │ 40ms     │ <1ms     │ 40x      │");
        System.out.println("  │ getByCategory() │ 80ms     │ <1ms     │ 80x      │");
        System.out.println("  │ search()        │ 200ms    │ <5ms     │ 40x      │");
        System.out.println("  │ stock check()   │ 10ms     │ <1ms     │ 10x      │");
        System.out.println("  └─────────────────┴──────────┴──────────┴──────────┘");

        System.out.println("\n=== HIT RATE TARGETS ===");
        System.out.println("  products:      > 95% (hot data, frequently read)");
        System.out.println("  products:sku:  > 90% (SKU lookups common)");
        System.out.println("  categories:    > 85% (less frequent, paginated)");
        System.out.println("  productSearch: > 80% (many unique queries)");
        System.out.println("  stock:         > 99% (tiny TTL, very hot)");

        System.out.println("\n============================================");
        System.out.println("  All 7 local caching classes complete!");
        System.out.println("  Next: Redis distributed caching");
        System.out.println("============================================");
    }
}
