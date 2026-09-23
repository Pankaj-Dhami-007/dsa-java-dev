package com.dhami.java_dev;

import com.dhami.java_dev.caching.caffeine_cache.EmployeeConfigService;
import com.dhami.java_dev.caching.default_cache.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
public class JavaDevApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(JavaDevApplication.class, args);

		ProductService productService = applicationContext.getBean(ProductService.class);// get proxy obj
//		productService.getPermissions();
//		productService.getPermissions();

		//productService.testCache();
//
//		System.out.println("===== CALL 1 =====");
//        System.out.println(productService.getPermissionsWithCachingAnnotation());
//
//		System.out.println("===== CALL 2 =====");
//		System.out.println(productService.getPermissionsWithCachingAnnotation());
//
////		System.out.println("===== REFRESH =====");
////		System.out.println(productService.refreshPermissions());
//
//		System.out.println("===== CLEAR CACHE =====");
//		System.out.println(productService.clearCache());
//
//		System.out.println("===== CALL 3 =====");
//		System.out.println(productService.getPermissionsWithCachingAnnotation());





//		PermissionService permissionService = applicationContext.getBean(PermissionService.class);
//
//		System.out.println("===== CALL 1 =====");
//		permissionService.getPermissionsWithCachingAnnotation();
//
//		System.out.println("===== CALL 2 =====");
//		permissionService.getPermissionsWithCachingAnnotation();
//
//		System.out.println("===== REFRESH =====");
//		permissionService.refreshPermissions();
//
//		System.out.println("===== CALL 3 =====");
//		permissionService.getPermissionsWithCachingAnnotation();
//
//		System.out.println("===== EVICT =====");
//		permissionService.clearCache();
//
//		System.out.println("===== CALL 4 =====");
//		permissionService.getPermissionsWithCachingAnnotation();


		EmployeeConfigService service = applicationContext.getBean(EmployeeConfigService.class);

		System.out.println("===== EMPLOYEE 101 - CALL 1 =====");
		System.out.println(service.getEmployeeConfig(101L));

		System.out.println("===== EMPLOYEE 101 - CALL 2 =====");
		System.out.println(service.getEmployeeConfig(101L));


		System.out.println("===== EMPLOYEE 102 - CALL 1 =====");
		System.out.println(service.getEmployeeConfig(102L));

		System.out.println("===== EMPLOYEE 101 - CALL 3 =====");
		System.out.println(service.getEmployeeConfig(101L));

	}

}
