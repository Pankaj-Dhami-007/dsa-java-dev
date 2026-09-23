package com.dhami.java_dev.caching.caffeine_cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmployeeConfigService {

    @Cacheable(value = "employeeConfig", key = "#employeeId")
    public Map<String, String> getEmployeeConfig(Long employeeId) {

        System.out.println("DATABASE CALLED FOR EMPLOYEE: " + employeeId);
        return loadEmployeeConfigFromDatabase(employeeId);
    }

    @CachePut(value = "employeeConfig", key = "#employeeId")
    public Map<String, String> refreshEmployeeConfig(Long employeeId) {

        System.out.println("REFRESHING FROM DATABASE: " + employeeId);
        return loadEmployeeConfigFromDatabase(employeeId);
    }

    @CacheEvict(value = "employeeConfig", key = "#employeeId")
    public void clearEmployeeConfig(Long employeeId) {
        System.out.println(" CACHE CLEARED FOR EMPLOYEE: " + employeeId);
    }

    private Map<String, String> loadEmployeeConfigFromDatabase(Long employeeId) {

        System.out.println("Loading employee configuration from database...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Map.of(
                "employeeId", String.valueOf(employeeId),
                "theme", "dark",
                "language", "english",
                "dateFormat", "dd-MM-yyyy",
                "currency", "INR"
        );
    }
}
