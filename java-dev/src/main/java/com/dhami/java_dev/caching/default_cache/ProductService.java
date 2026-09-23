package com.dhami.java_dev.caching.default_cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    // There are two concepts we're learning:
    // cache manager manages caches  like products, users , employees
    // CacheManager is not the actual cache data itself.
    // It is the component through which Spring gets/manages individual caches.
    // It provides a common entry point to all your application's caches.

    // The abstraction gives you a common API: get, put, evict, clear
    // But providers can have different capabilities and semantics ex. Simple memory, Caffeine, Redis
    // don't have identical characteristics.

    private final CacheManager cacheManager;

    public ProductService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void testCache() {
        System.out.println(" cache provider " + cacheManager.getClass());

        Cache cache = cacheManager.getCache("products");
        // Give me the cache region named permissions


        cache.put("all", "products");

        Cache.ValueWrapper value = cache.get("all");
        System.out.println("Value: " + value.get());

    }

    public List<String> getPermissions() {

        Cache cache = cacheManager.getCache("permissions");
        Cache.ValueWrapper cached = cache.get("all");

        if (cached != null) {
            System.out.println("CACHE HIT");
            System.out.println(cached);
            return (List<String>) cached.get();
        }
        System.out.println("CACHE MISS");
        List<String> permissions = loadPermissionsFromDatabase();

        cache.put("all", permissions);
        System.out.println(permissions);
        return permissions;
    }

    private List<String> loadPermissionsFromDatabase() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return List.of("READ", "WRITE", "DELETE");
    }


    // Now caching with annotations

    // Spring Cache Abstraction , Cache Provider

    // Spring Cache -> Defines the programming model:
    //@Cacheable
    //@CachePut
    //@CacheEvict
    //CacheManager
    //Cache

    // Provider -> Actually stores the data:
    //Simple Map
    //Caffeine
    //Redis
    //etc.


    // The key identifies the specific cached result.
    // Spring can generate keys automatically
    // Spring has a default key-generation mechanism.
    // Why explicitly specify keys?
    // -> Because sometimes you want complete control. ex. Use the method's id parameter as the cache key.

    // What is #id? -> This is Spring Expression Language (SpEL).
    // the # means: -> Access a method parameter. ex. @Cacheable(value = "employees", key = "#id")

    @Cacheable(value = "permissions", key = "'all'")
    public List<String> getPermissionsWithCachingAnnotation() {
        System.out.println("DATABASE CALLED");
        return loadPermissionsFromDatabase();
    }

    @CachePut(value = "permissions", key = "'all'")
    public List<String> refreshPermissions() {
        System.out.println("REFRESHING FROM DATABASE");
        return loadPermissionsFromDatabase(); // hamesha chalega + cache update karega
    }

    @CacheEvict(value = "permissions", allEntries = true)
    public int clearCache() {
        System.out.println("Cache cleared");
        return 0;
    }

}

/**


               YOUR CODE
                  ↓
       Spring Cache Abstraction
                  ↓
             CacheManager
                  ↓
        ┌─────────┴─────────┐
        ↓                   ↓
     Caffeine              Redis
        ↓                   ↓
       JVM                Redis Server

 */

/**

Spring Caching — Rules to Keep in Mind

 @Cacheable only works on public methods — because Spring's proxy can only
 intercept public method calls.

 Never call a cached method from within the same class (self-invocation) —
 because this bypasses the proxy, so caching logic never runs.

 Always call cached methods from a different bean — because the proxy only
 intercepts calls coming from outside the class.

 @EnableCaching must be present on a @Configuration class — because without it
 Spring never creates the caching proxy infrastructure.

 The class containing cached methods must be a Spring bean — because new creates a raw
 object with no proxy, so no caching.

 @Cacheable skips the method entirely on a cache hit — because the proxy
 returns the cached value without invoking the target method.

 @CachePut always runs the method and updates the cache — because its
 purpose is to refresh the cache, not to serve from it.

 @CacheEvict removes entries from the cache — because stale data must be invalidated
 when the source changes.

 Cache key defaults to the method parameters — because Spring uses SimpleKeyGenerator on
 arguments by default.

 Return type must not be void for @Cacheable — because there is no value to
 store in the cache otherwise.

 Default cache manager is ConcurrentMapCacheManager (in-memory) — because Spring Boot
 auto-configures a simple ConcurrentHashMap when no provider is set.

 Different caches need different names — because each cache name maps to a
 separate storage region in the CacheManager.

 @Cacheable and @CachePut cannot be used together on the same method — because one
 skips execution while the other always runs, causing conflicting behavior.

 @CacheEvict can be combined with @Cacheable on the same method — because evict-then-load
 is a common cache refresh pattern

 Same method with different arguments produces different cache entries — because
 the key is derived from the arguments by default.

 Null return values are cached by default — because Spring stores whatever the
 method returns unless told otherwise.

 Use condition to decide whether to cache at all, and unless to decide whether to store
 the result — because condition is checked before execution and unless after.

 Self-invocation breaks @Cacheable, @CacheEvict, @CachePut, @Transactional, and @Async —
 because all of them rely on the same proxy interception mechanism.

 Thread-safety depends on the underlying cache provider — because ConcurrentMapCacheManager
 is thread-safe but custom caches may not be.

 Cache TTL, eviction policy, and size limits come from the provider — because the default
 ConcurrentMapCacheManager has no eviction or expiry at all.

 Caching does not work in constructors or @PostConstruct — because the proxy is not
 fully initialized when those run.

 Proxy type matters (JDK vs CGLIB) — because JDK proxies need interfaces while
 CGLIB proxies the class directly

 Final methods and final classes cannot be proxied — because CGLIB cannot override
 final methods or subclass final classes.

 Multiple cache names in one annotation check all caches before executing — because
 Spring looks in each cache in order and returns on the first hit.

 sync = true on @Cacheable prevents cache stampede — because it locks the cache so
 only one thread computes the value on a miss.


 */

/**

Cache name vs Cache key

 value = "permissions"  → which cache?
 key   = "all"          → which entry inside that cache?


 CacheManager
     │
     └── permissions
           │
           ├── "all"       → [READ, WRITE, DELETE]
           ├── "ADMIN"     → [...]
           └── "ACCOUNTANT"→ [...]

 The key identifies the specific cached result.

 with multiple parameter

 @Cacheable(
    value = "leaves",
    key = "#userId + ':' + #year + ':' + #month"
)

 userId = 101
year   = 2026
month  = 9

 -> 101:2026:9

 leaves
   │
   ├── 101:2026:9 → [...]
   ├── 101:2026:8 → [...]
   ├── 102:2026:9 → [...]
   └── 103:2026:9 → [...]

 even cleaner approach

 key = "#userId + '-' + #year + '-' + #month"

 101-2026-9 -> becomes the key.

 golden rule ->
 Your cache key must distinguish every input combination that can produce a different result.

 */
