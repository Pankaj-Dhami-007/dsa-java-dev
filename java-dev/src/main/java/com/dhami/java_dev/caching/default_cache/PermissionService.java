package com.dhami.java_dev.caching.default_cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Cacheable(
            value = "permissions",
            key = "'all'"
    )
    public List<String> getPermissionsWithCachingAnnotation() {

        System.out.println("DATABASE CALLED");

        return loadPermissionsFromDatabase();
    }

    @CachePut(
            value = "permissions",
            key = "'all'"
    )
    public List<String> refreshPermissions() {

        System.out.println("REFRESHING DATABASE");

        return loadPermissionsFromDatabase();
    }

    @CacheEvict(
            value = "permissions",
            key = "'all'"
    )
    public void clearCache() {

        System.out.println("CACHE EVICTED");
    }

        private List<String> loadPermissionsFromDatabase() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return List.of("READ", "WRITE", "DELETE");
    }
}
