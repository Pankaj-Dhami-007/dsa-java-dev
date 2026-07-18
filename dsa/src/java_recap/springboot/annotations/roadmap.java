package java_recap.springboot.annotations;

/*

=========================================================
            SPRING BOOT ANNOTATIONS ROADMAP
=========================================================

Goal:
------
Learn Spring Boot deeply for:
- Interviews
- Real Projects
- Enterprise Backend Development
- Microservices

We will learn:
---------------
1. What annotation does
2. Why it is used
3. Internal Spring working
4. Real project use case
5. Interview questions
6. Tricky scenarios
7. Common mistakes
8. Runtime behavior

=========================================================
            HOW WE WILL LEARN EACH TOPIC
=========================================================

For EVERY annotation we will study:

1. Definition
2. Why used
3. Internal working
4. Real project example
5. Full code example
6. Interview questions
7. Common mistakes
8. Related annotation differences
9. Runtime understanding
10. Tricky interview scenarios

=========================================================
                PHASE 1
        CORE SPRING ANNOTATIONS
=========================================================

Topics:
--------

@Component
@Service
@Repository
@Controller
@RestController
@Bean
@Configuration
@Autowired
@Qualifier
@Primary
@Value
@Scope
@Lazy

Concepts:
----------
- IOC Container
- Dependency Injection
- Bean Creation
- Bean Lifecycle
- Component Scanning

=========================================================
                PHASE 2
    SPRING BOOT APPLICATION ANNOTATIONS
=========================================================

Topics:
--------

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@ConfigurationProperties

Concepts:
----------
- Auto Configuration
- Bootstrapping
- Startup Flow
- Package Scanning

=========================================================
                PHASE 3
        REST API ANNOTATIONS
=========================================================

Topics:
--------

@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping

@PathVariable
@RequestParam
@RequestBody
@ResponseBody
@ResponseStatus
@RequestHeader
@CrossOrigin

Concepts:
----------
- REST APIs
- HTTP Methods
- Request Handling
- JSON Serialization

=========================================================
                PHASE 4
        VALIDATION ANNOTATIONS
=========================================================

Topics:
--------

@Valid
@Validated

@NotNull
@NotBlank
@NotEmpty
@Size
@Min
@Max
@Email
@Pattern
@Past
@Future

Concepts:
----------
- DTO Validation
- Hibernate Validator
- Input Validation

=========================================================
                PHASE 5
    EXCEPTION HANDLING ANNOTATIONS
=========================================================

Topics:
--------

@ControllerAdvice
@RestControllerAdvice
@ExceptionHandler
@ResponseStatus

Concepts:
----------
- Global Exception Handling
- Centralized Error Management

=========================================================
                PHASE 6
            JPA ANNOTATIONS
=========================================================

Topics:
--------

@Entity
@Table
@Id
@GeneratedValue
@Column
@Transient

@OneToOne
@OneToMany
@ManyToOne
@ManyToMany

@JoinColumn
@JoinTable

@CreationTimestamp
@UpdateTimestamp

@Enumerated
@Lob
@Embedded
@Embeddable

Concepts:
----------
- ORM
- Entity Mapping
- Relationships
- Lazy vs Eager Loading

=========================================================
                PHASE 7
        TRANSACTION ANNOTATIONS
=========================================================

Topics:
--------

@Transactional
@EnableTransactionManagement

Concepts:
----------
- ACID Properties
- Rollback
- Propagation
- Isolation Level

=========================================================
                PHASE 8
        SPRING SECURITY
=========================================================

Topics:
--------

@EnableWebSecurity
@PreAuthorize
@PostAuthorize
@Secured
@RolesAllowed

Concepts:
----------
- Authentication
- Authorization
- JWT Security
- RBAC

=========================================================
                PHASE 9
                AOP
=========================================================

Topics:
--------

@Aspect
@Before
@After
@AfterReturning
@AfterThrowing
@Around
@EnableAspectJAutoProxy

Concepts:
----------
- Logging
- Cross Cutting Concerns
- Proxy Mechanism

=========================================================
                PHASE 10
                CACHING
=========================================================

Topics:
--------

@EnableCaching
@Cacheable
@CachePut
@CacheEvict

Concepts:
----------
- Redis
- In-memory Cache
- Performance Optimization

=========================================================
                PHASE 11
            SCHEDULING
=========================================================

Topics:
--------

@EnableScheduling
@Scheduled

Concepts:
----------
- Cron Jobs
- Fixed Delay
- Fixed Rate

=========================================================
                PHASE 12
                ASYNC
=========================================================

Topics:
--------

@EnableAsync
@Async

Concepts:
----------
- Async Programming
- Background Tasks
- Multithreading

=========================================================
                PHASE 13
            KAFKA / MESSAGING
=========================================================

Topics:
--------

@KafkaListener
@EnableKafka
@RabbitListener

Concepts:
----------
- Event Driven Architecture
- Consumers
- Producers

=========================================================
                PHASE 14
                TESTING
=========================================================

Topics:
--------

@SpringBootTest
@WebMvcTest
@DataJpaTest
@MockBean
@Mock
@InjectMocks

Concepts:
----------
- Unit Testing
- Integration Testing
- Mocking

=========================================================
                PHASE 15
            SWAGGER / OPENAPI
=========================================================

Topics:
--------

@Operation
@Tag
@Schema
@ApiResponse

Concepts:
----------
- API Documentation
- Swagger UI

=========================================================
                PHASE 16
                LOMBOK
=========================================================

Topics:
--------

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

Concepts:
----------
- Boilerplate Reduction
- Builder Pattern

=========================================================
                PHASE 17
            MICROSERVICES
=========================================================

Topics:
--------

@EnableDiscoveryClient
@FeignClient
@EnableFeignClients
@LoadBalanced
@RefreshScope

Concepts:
----------
- Eureka
- Feign Client
- Config Server
- Load Balancing

=========================================================
                PHASE 18
        ADVANCED HIBERNATE
=========================================================

Topics:
--------

@Formula
@Where
@SQLDelete
@Filter
@BatchSize

Concepts:
----------
- Soft Delete
- Query Optimization

=========================================================
                PHASE 19
        PROFILE & ENVIRONMENT
=========================================================

Topics:
--------

@Profile
@TestPropertySource
@PropertySource

Concepts:
----------
- Dev / Prod Configurations
- Environment Based Beans

=========================================================
                PHASE 20
        MISC IMPORTANT ANNOTATIONS
=========================================================

Topics:
--------

@PostConstruct
@PreDestroy
@Order
@DependsOn

Concepts:
----------
- Bean Lifecycle
- Initialization
- Bean Ordering

=========================================================
        FINAL INTERVIEW PREPARATION
=========================================================

We will also learn:

1. Tricky Interview Questions
2. Internal Spring Working
3. Bean Lifecycle
4. Proxy Mechanism
5. Transaction Internals
6. Circular Dependency
7. Real Project Structure

=========================================================
            RECOMMENDED LEARNING ORDER
=========================================================

1. Core Spring
2. REST APIs
3. Validation
4. Exception Handling
5. JPA
6. Transactions
7. Security
8. AOP
9. Async
10. Caching
11. Kafka
12. Testing
13. Microservices

=========================================================
                    FINAL GOAL
=========================================================

After completing this roadmap you should be able to:

- Crack Spring Boot Interviews
- Build Production APIs
- Understand Internal Spring Working
- Build Enterprise Applications
- Work on Microservices
- Design Scalable Backend Systems

=========================================================
                    START COMMAND
=========================================================

Start with:

"Start Phase 1 Core Spring Annotations"

=========================================================

*/

public class roadmap {
}