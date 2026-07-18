package springboot_deep_drive.springboot.config;

/*
================================================================
  WHY SPRING BOOT CAME AFTER SPRING CORE?
================================================================

Simple Answer:
--------------
Spring Core was great but developers had to do
TOO MUCH manual work. Spring Boot automated
that manual work.

================================================================
  5 BIG PROBLEMS IN SPRING CORE
================================================================

PROBLEM 1: Too much XML
-------------------------

In Spring Core, every bean needed XML:

<!-- Old Spring Core XML -->
<beans>
    <bean id="userService"
        class="com.app.UserService">
        <property name="dao" ref="userDao"/>
    </bean>

    <bean id="userDao"
        class="com.app.UserDao">
        <property name="ds" ref="dataSource"/>
    </bean>

    <bean id="dataSource"
        class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass"
            value="com.mysql.cj.jdbc.Driver"/>
    </bean>
</beans>

Problems:
- XML files became huge (1000+ lines)
- No error checking at compile time
- Hard to read and maintain
- Typo in XML = runtime error

================================================================
PROBLEM 2: Dependency version hell
-------------------------------------

Developers had to manually manage versions:

spring-core         ?.?.?
spring-context      ?.?.?
spring-orm          ?.?.?
hibernate-core      ?.?.?
mysql-connector     ?.?.?
jackson-databind    ?.?.?

Finding compatible versions was very hard.
Upgrading one library could break everything.

================================================================
PROBLEM 3: No embedded server
---------------------------------

Before Spring Boot, deployment was:

1. Write code
2. Build WAR file
3. Install Tomcat on server
4. Copy WAR to Tomcat's webapps folder
5. Start Tomcat
6. Check logs for errors

Slow and painful for development.

================================================================
PROBLEM 4: Everything had to be configured manually
------------------------------------------------------

In Spring Core, you wrote ALL this code:

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds =
            new DriverManagerDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/db");
        ds.setUsername("root");
        ds.setPassword("pass");
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf =
            new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        return sf;
    }

    @Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(
            sessionFactory().getObject()
        );
    }
}

Every project needed the same boilerplate code.

================================================================
PROBLEM 5: No production-ready features
------------------------------------------

Spring Core did not have:
- Health checks (is my app alive?)
- Metrics (how many requests?)
- External configuration (profiles)
- Monitoring endpoints

Developers had to build all this from scratch.

================================================================
  HOW SPRING BOOT SOLVED EVERY PROBLEM
================================================================

Spring Boot Solutions:

+------------------------+--------------------------------------+
| Problem                | Spring Boot Solution                 |
+------------------------+--------------------------------------+
| Too much XML           | @EnableAutoConfiguration             |
|                        | (beans created automatically)        |
+------------------------+--------------------------------------+
| Version hell           | Spring Boot Starters                 |
|                        | (pre-configured dependency bundles)  |
+------------------------+--------------------------------------+
| No embedded server     | Embedded Tomcat / Jetty / Undertow   |
|                        | (just run main() - server starts)    |
+------------------------+--------------------------------------+
| Manual config          | Auto Configuration                   |
|                        | (Spring Boot detects and configures) |
+------------------------+--------------------------------------+
| No production features | Actuator + Metrics + Health          |
|                        | (ready endpoints out of the box)     |
+------------------------+--------------------------------------+

================================================================
  EVOLUTION OF SPRING CONFIGURATION
================================================================

Phase 1: XML Configuration (Spring 1.x - 2.x)
-------------------------------------------------

applicationContext.xml (100s of lines)

How to load:
  ApplicationContext ctx =
      new ClassPathXmlApplicationContext(
          "beans.xml"
      );

Phase 2: Annotation + XML (Spring 2.5 - 3.x)
-------------------------------------------------

@Component, @Service, @Repository used
But XML still needed for component scanning

How to load:
  <context:component-scan
      base-package="com.app"/>

Phase 3: Java Config (Spring 3.0 - 4.x)
------------------------------------------

@Configuration + @Bean annotations
No XML needed

How to load:
  ApplicationContext ctx =
      new AnnotationConfigApplicationContext(
          AppConfig.class
      );

Phase 4: Spring Boot (Spring 4.0+)
--------------------------------------

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

That's it. Spring Boot does the rest.

================================================================
  BEFORE vs AFTER SPRING BOOT
================================================================

BEFORE Spring Boot (Spring Core):
----------------------------------

1. Add dependencies manually (5-10 JARs)
2. Create applicationContext.xml
3. Configure DataSource + SessionFactory + TxManager
4. Configure ViewResolver + MessageSource
5. Build WAR
6. Deploy to external Tomcat
7. Start server

Total time: 2-3 days to setup

AFTER Spring Boot:
-------------------

1. Add spring-boot-starter-web (ONE dependency)
2. Write main class with @SpringBootApplication
3. Write your business code
4. Run main()

Total time: 5 minutes to setup

================================================================
  REAL EXAMPLE - Spring Core vs Spring Boot
================================================================

Spring Core Setup (30+ lines of config):
------------------------------------------

<!-- web.xml -->
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>
        org.springframework.web.servlet.DispatcherServlet
    </servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/spring-config.xml</param-value>
    </init-param>
</servlet>

<!-- spring-config.xml -->
<mvc:annotation-driven/>
<context:component-scan base-package="com.app"/>
<bean class="org.springframework.web.servlet.view.
    InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/views/"/>
    <property name="suffix" value=".jsp"/>
</bean>

Spring Boot Setup (ZERO config lines):
----------------------------------------

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

Spring Boot automatically:
- Creates DispatcherServlet
- Starts Tomcat on port 8080
- Configures Jackson for JSON
- Configures ViewResolver
- Sets up error handling
- Registers message converters

================================================================
  SUMMARY
================================================================

Spring Core was the foundation.
Spring Boot built on top to remove pain points.

Think of it as:
  Spring Core = Engine (powerful but needs assembly)
  Spring Boot = Car (engine pre-assembled, ready to drive)

Spring Boot did NOT invent new concepts.
It AUTOMATED existing Spring concepts.

================================================================
*/

public class WhySpringBootAfterSpringCore {

}
