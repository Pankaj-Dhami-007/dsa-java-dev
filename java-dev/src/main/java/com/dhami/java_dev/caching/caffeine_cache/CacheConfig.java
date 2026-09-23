package com.dhami.java_dev.caching.caffeine_cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {


//    @Bean
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                .expireAfterWrite(10, TimeUnit.MINUTES) // TTL
//                .maximumSize(500)); // Max size
//        return cacheManager;
//    }

//    @Bean
//    public Caffeine<Object, Object> caffeineConfig() {
//        return Caffeine.newBuilder()
//                .maximumSize(1000)
//                .expireAfterWrite(10, TimeUnit.MINUTES);
//    }
//
//    @Bean
//    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
//
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(caffeine);
//        return cacheManager;
//    }

    // Approach C — Per-cache customization (best for real projects):

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cm = new CaffeineCacheManager();


        // Default spec — sab non-registered caches ke liye
        cm.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(5, TimeUnit.MINUTES));

        // registerCustomCache(name, cache)
        cm.registerCustomCache("products",
                Caffeine.newBuilder()
                        .maximumSize(1000)
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .build());

        cm.registerCustomCache("users",
                Caffeine.newBuilder()
                        .maximumSize(500)
                        .expireAfterWrite(1, TimeUnit.HOURS)
                        .build());

        return cm;
    }
}

/**

registerCustomCache(name, cache)->>
 CaffeineCacheManager by default saare caches ko same spec deta hai.
 registerCustomCache usse override kar deta hai — matlab ek specific cache name
 ke liye alag Caffeine cache de deta hai.

 cm.registerCustomCache("products", <caffeine_cache_object>);
                       ↑                    ↑
                    cache ka naam      actual Cache object

 1. "products" → ye wahi naam hai jo tu @Cacheable("products") me use karega.
 Dono match hone chahiye, warna Spring naya default cache bana dega.

 2. Caffeine.newBuilder()...build() → ye ek actual com.github.benmanes.caffeine.cache.Cache object return karta hai.


 .maximumSize(1000)   ->>
Cache me max 1000 entries rakh sakta hai.
Jab 1001st entry aati hai → Caffeine evict kar deta hai sabse kam useful entry ko.
Eviction policy: Window TinyLFU (Caffeine ka apna algorithm — LRU aur LFU ka hybrid).
Agar ye set nahi kiya → cache unbounded ho jaata hai → memory leak risk.
1000 ka matlab: 1000 keys (entries), 1000 bytes nahi.
Kyu zaroori hai: Bina size limit ke, agar tu 10 lakh unique keys daal dega, JVM ka heap full ho jaayega → OutOfMemoryError.

 */

/**

Caffeine vs CaffeineCacheManager ->>>

 Quick Difference
Caffeine = Caffeine library ka core class — actual cache banata hai aur data store karta hai.
CaffeineCacheManager = Spring ka wrapper — multiple Caffeine caches ko manage karta hai aur @Cacheable se jodta hai.

 * Caffeine is a third-party library class, while CaffeineCacheManager is a Spring Framework class.
 * Caffeine builds a single cache, whereas CaffeineCacheManager manages multiple caches (a registry).
 * Caffeine works without Spring, but CaffeineCacheManager requires Spring context.
 * Caffeine does not understand @Cacheable, but CaffeineCacheManager does — it acts as the bridge.
 * Caffeine.newBuilder() returns a builder, while CaffeineCacheManager implements Spring's CacheManager interface.
 * Caffeine has methods like put, getIfPresent, invalidate, whereas CaffeineCacheManager has getCache(name), getCacheNames().
 * Spring injects CacheManager (interface), and CaffeineCacheManager is one of its implementations (others: RedisCacheManager, ConcurrentMapCacheManager).
 * registerCustomCache(name, caffeineCache) is how you plug a Caffeine cache into the Spring manager.
 *
 * Without CaffeineCacheManager, @Cacheable annotations won't work — even if Caffeine is on the classpath.
 * Caffeine handles eviction, TTL, size limits, while CaffeineCacheManager handles cache lookup, naming, and Spring integration.
 *
 * Analogy
 * Caffeine = almirah (cupboard) — stores data.
 * CaffeineCacheManager = librarian — knows which almirah to open for which request.
 * @Cacheable("products") = user request — librarian routes it to the correct almirah.
 */

/**

Caffeine is the actual cache implementation that stores data,
while CaffeineCacheManager is Spring's wrapper that manages multiple Caffeine caches
and integrates them with @Cacheable annotations.

 CaffeineCacheManager internally holds a ConcurrentMap<String, Cache> of Caffeine caches —
 one per cache name — and returns the right one when Spring calls getCache(name).
 */


/*

Analogy

Socho JDK ek pre-installed app hai phone me (jaise Camera).
Caffeine ek Play Store se download ki gayi app hai (jaise WhatsApp).
Dono Android apps hi hain — same platform pe chalti hain.
Lekin Camera pre-installed hai, WhatsApp alag se install karni padi.
WhatsApp "third-party app" hai — lekin wo Android app hi hai, iOS ka nahi.
Caffeine = third-party Java library. Java me likhi hai, JVM pe chalti hai, lekin JDK me pre-installed nahi hai.
 */