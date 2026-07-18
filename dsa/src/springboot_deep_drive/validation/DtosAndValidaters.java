package springboot_deep_drive.validation;

/*
 * ═══════════════════════════════════════════════════════════════════════
 *              DTOs & VALIDATION — COMPLETE DEVELOPER GUIDE
 * ═══════════════════════════════════════════════════════════════════════
 *
 * ───────────────────────────────────────────────────────────────────────
 * 1. WHAT IS A DTO?
 * ───────────────────────────────────────────────────────────────────────
 *
 * DTO = Data Transfer Object. A plain Java class that carries data between
 * layers (Client → Controller → Service → DB and back).
 *
 *   ┌──────────┐   JSON   ┌────────────┐   DTO   ┌──────────┐   Entity  ┌────────┐
 *   │  Client  │◀────────▶│ Controller │◀───────▶│ Service  │◀────────▶│   DB   │
 *   │  (curl)  │  JSON    │   (DTO)    │  DTO    │ (Entity) │  Entity  │ (Table)│
 *   └──────────┘          └────────────┘         └──────────┘          └────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 2. WHY USE DTOs? (Problems They Solve)
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ❌ Problem 1: Entity exposes internal DB details to client
 *      @Entity User { passwordHash, createdAt, updatedAt, id }
 *      If you send Entity directly → client sees passwordHash!
 *
 *   ✅ DTO hides sensitive/internal fields
 *      UserResponse { id, name, email }  ← passwordHash excluded
 *
 *   ❌ Problem 2: Client sends fields that don't match Entity
 *      SignupRequest { name, email, password, confirmPassword }
 *      Entity User { name, email, passwordHash }
 *      → confirmPassword doesn't exist in Entity
 *
 *   ✅ DTO matches the request/response contract exactly
 *      SignupRequest has confirmPassword — validated, then ignored
 *
 *   ❌ Problem 3: Circular references in JPA (lazy loading errors)
 *      User → List<Order>, Order → User (infinite JSON)
 *
 *   ✅ DTO breaks circular references (flatten or cherry-pick fields)
 *
 *   ❌ Problem 4: Different clients need different data
 *      GET /api/users      → { id, name, email }
 *      GET /api/users/5    → { id, name, email, orders, address }
 *      PUT /api/users/5    → { name, email }  (can't change id)
 *
 *   ✅ Separate DTOs per use case
 *      UserSummary, UserDetail, UpdateUserRequest
 *
 * ───────────────────────────────────────────────────────────────────────
 * 3. DTO vs ENTITY — When to use what
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬──────────────────────────┬──────────────────────────┐
 *   │               │ DTO                       │ Entity                   │
 *   ├──────────────┼──────────────────────────┼──────────────────────────┤
 *   │ Purpose       │ Transfer data between     │ Map to DB table          │
 *   │               │ layers                     │                          │
 *   │ Location      │ Controller / Service      │ Repository only          │
 *   │               │ boundary                  │                          │
 *   │ Annotations   │ @Getter, @Setter,         │ @Entity, @Table,         │
 *   │               │ @NotBlank, @Email         │ @Column, @Id             │
 *   │ Exposed to    │ YES — client sees this    │ NO — never expose to    │
 *   │ client?       │                           │ client                   │
 *   │ Validation    │ YES — @Valid on DTO       │ NO — validate at         │
 *   │               │                           │ service layer if needed  │
 *   │ Example       │ CreateUserRequest         │ User, Order, Product    │
 *   └──────────────┴──────────────────────────┴──────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 4. VALIDATION IN SPRING BOOT — THE STACK
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌─────────────────────────────────────────────────────────────────────┐
 *   │  CLIENT                                                             │
 *   │  curl -X POST /api/users -d '{"email":"bad"}'                      │
 *   └──────────┬──────────────────────────────────────────────────────────┘
 *              │
 *              ▼
 *   ┌──────────────────────┐
 *   │  Tomcat              │  Parses HTTP bytes → HttpServletRequest
 *   └──────────┬───────────┘
 *              │
 *              ▼
 *   ┌──────────────────────┐
 *   │  Spring MVC           │  DispatcherServlet → Controller method
 *   │  (@Valid on DTO)      │  Detects @Valid + @RequestBody
 *   └──────────┬───────────┘
 *              │
 *              ▼
 *   ┌──────────────────────┐
 *   │  Jakarta Validation   │  JSR-380 API (interface only)
 *   │  (@NotBlank, @Email)  │  Defines annotations
 *   └──────────┬───────────┘
 *              │
 *              ▼
 *   ┌──────────────────────┐
 *   │  Hibernate Validator  │  Actual implementation
 *   │  (org.hibernate.      │  Checks constraints, collects violations
 *   │   validator)          │
 *   └──────────┬───────────┘
 *              │
 *              ├── Valid? → Controller method executes
 *              │
 *              └── Invalid?
 *                  └── MethodArgumentNotValidException
 *                           │
 *                           ▼
 *                  └── @ControllerAdvice catches it
 *                           │
 *                           ▼
 *                      Returns 400 + field errors
 *
 * ───────────────────────────────────────────────────────────────────────
 * 5. PRACTICAL EXAMPLE — Full DTO + Validation Setup
 * ───────────────────────────────────────────────────────────────────────
 *
 *   // ── Request DTO ────────────────────────────────────────────────
 *   public class CreateUserRequest {
 *
 *       @NotBlank(message = "Name is required")
 *       private String name;
 *
 *       @NotBlank
 *       @Email(message = "Invalid email format")
 *       private String email;
 *
 *       @NotBlank
 *       @Size(min = 8, message = "Password must be at least 8 chars")
 *       private String password;
 *
 *       @NotBlank
 *       private String confirmPassword;
 *   }
 *
 *   // ── Controller ─────────────────────────────────────────────────
 *   @RestController
 *   @RequestMapping("/api/users")
 *   public class UserController {
 *
 *       @PostMapping
 *       public UserResponse create(
 *               @Valid @RequestBody CreateUserRequest request) {
 *
 *           // If request is INVALID → MethodArgumentNotValidException
 *           // thrown BEFORE this method executes
 *
 *           User user = userService.create(request);
 *           return UserResponse.from(user);
 *       }
 *   }
 *
 *   // ── Response DTO ───────────────────────────────────────────────
 *   public class UserResponse {
 *       private Long id;
 *       private String name;
 *       private String email;
 *
 *       public static UserResponse from(User user) {
 *           UserResponse dto = new UserResponse();
 *           dto.id = user.getId();
 *           dto.name = user.getName();
 *           dto.email = user.getEmail();
 *           return dto;
 *       }
 *   }
 *
 *   // ── Service ────────────────────────────────────────────────────
 *   @Service
 *   public class UserService {
 *       public User create(CreateUserRequest request) {
 *           User user = new User();
 *           user.setName(request.getName());
 *           user.setEmail(request.getEmail());
 *           user.setPasswordHash(hash(request.getPassword()));
 *           return userRepository.save(user);
 *       }
 *   }
 *
 *   // ── Global Exception Handler ────────────────────────────────────
 *   @ControllerAdvice
 *   public class ValidationExceptionHandler {
 *
 *       @ExceptionHandler(MethodArgumentNotValidException.class)
 *       public ResponseEntity<Map<String, String>> handleValidation(
 *               MethodArgumentNotValidException ex) {
 *
 *           Map<String, String> errors = new HashMap<>();
 *           ex.getBindingResult().getFieldErrors().forEach(error ->
 *               errors.put(error.getField(), error.getDefaultMessage())
 *           );
 *           return ResponseEntity.badRequest().body(errors);
 *       }
 *   }
 *
 *   // ── What client receives on validation failure ──────────────────
 *   // HTTP 400
 *   // {
 *   //   "name": "Name is required",
 *   //   "email": "Invalid email format",
 *   //   "password": "Password must be at least 8 chars"
 *   // }
 *
 * ───────────────────────────────────────────────────────────────────────
 * 6. MOST COMMON VALIDATION ANNOTATIONS (One-line each)
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌────────────────────┬────────────────────────────────────────────────┐
 *   │ Annotation          │ What it does (one line)                       │
 *   ├────────────────────┼────────────────────────────────────────────────┤
 *   │ @NotBlank           │ String must not be null AND must have at       │
 *   │                     │ least one non-whitespace character             │
 *   │ @NotEmpty           │ String/Collection/Map must not be null or      │
 *   │                     │ empty (size > 0)                              │
 *   │ @NotNull            │ Value must not be null                        │
 *   │ @Size(min,max)      │ Length must be between min and max            │
 *   │ @Min(value)         │ Number must be ≥ value                       │
 *   │ @Max(value)         │ Number must be ≤ value                       │
 *   │ @Email              │ String must be a valid email format           │
 *   │ @Pattern(regexp)    │ String must match the regex                   │
 *   │ @Positive           │ Number must be > 0                           │
 *   │ @PositiveOrZero     │ Number must be ≥ 0                           │
 *   │ @Negative           │ Number must be < 0                           │
 *   │ @NegativeOrZero     │ Number must be ≤ 0                           │
 *   │ @Past               │ Date must be in the past                     │
 *   │ @PastOrPresent      │ Date must be past or today                   │
 *   │ @Future             │ Date must be in the future                   │
 *   │ @FutureOrPresent    │ Date must be future or today                 │
 *   │ @Digits(int,fraction)│ Number must have specified int/fraction digits│
 *   │ @AssertTrue         │ Boolean must be true                         │
 *   │ @AssertFalse        │ Boolean must be false                        │
 *   └────────────────────┴────────────────────────────────────────────────┘
 *
 * ───────────────────────────────────────────────────────────────────────
 * 7. @Valid vs @Validated
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────┬──────────────────────────┬──────────────────────────┐
 *   │               │ @Valid                    │ @Validated               │
 *   ├──────────────┼──────────────────────────┼──────────────────────────┤
 *   │ From          │ Jakarta Validation         │ Spring                    │
 *   │               │ (jakarta.validation)       │ (org.springframework)    │
 *   │ Groups?       │ ❌ No                      │ ✅ Yes — specify groups  │
 *   │ Use in        │ @RequestBody +             │ @RequestBody +           │
 *   │ Controller    │ @RequestParam              │ @RequestParam            │
 *   │ Use in        │ Any class                  │ Any class                │
 *   │ Non-Controller│                           │ (Service, etc.)          │
 *   │ Typical       │ Most REST APIs             │ When you need groups     │
 *   │ Use           │                            │ (Create vs Update)       │
 *   └──────────────┴──────────────────────────┴──────────────────────────┘
 *
 *   Validation Groups Example:
 *
 *     public class CreateUserRequest {
 *         @NotBlank(groups = Create.class)
 *         private String password;
 *
 *         @NotBlank(groups = Update.class)  // not required on create
 *         private String phone;
 *     }
 *
 *     // In controller:
 *     @Validated(Create.class)  → only validates Create group
 *     @Validated(Update.class)  → only validates Update group
 *
 * ───────────────────────────────────────────────────────────────────────
 * 8. HOW VALIDATION WORKS INTERNALLY
 * ───────────────────────────────────────────────────────────────────────
 *
 *   When Spring MVC sees @Valid on a @RequestBody parameter:
 *
 *     1. RequestMappingHandlerAdapter detects @Valid annotation
 *     2. Creates DataBinder for the DTO object
 *     3. DataBinder.setValidator() sets the Validator (Hibernate Validator)
 *     4. DataBinder.validate() triggers validation
 *     5. Hibernate Validator reads constraints on DTO fields
 *     6. For each constraint (e.g., @NotBlank):
 *        a. Instantiates the corresponding ConstraintValidator
 *        b. Calls isValid(value, context)
 *        c. If invalid → adds ConstraintViolation to set
 *     7. ConstraintViolations → BindingResult (fieldErrors)
 *     8. If errors present → throws MethodArgumentNotValidException
 *     9. Your @ExceptionHandler catches it → returns 400
 *
 * ───────────────────────────────────────────────────────────────────────
 * 9. CUSTOM VALIDATION
 * ───────────────────────────────────────────────────────────────────────
 *
 *   Step 1: Create custom annotation
 *
 *     @Target({FIELD})
 *     @Retention(RUNTIME)
 *     @Constraint(validatedBy = UniqueEmailValidator.class)
 *     public @interface UniqueEmail {
 *         String message() default "Email already exists";
 *         Class<?>[] groups() default {};
 *         Class<? extends Payload>[] payload() default {};
 *     }
 *
 *   Step 2: Implement validator
 *
 *     public class UniqueEmailValidator
 *             implements ConstraintValidator<UniqueEmail, String> {
 *
 *         @Autowired
 *         private UserRepository userRepository;
 *
 *         @Override
 *         public boolean isValid(String email, ConstraintValidatorContext ctx) {
 *             return email != null && !userRepository.existsByEmail(email);
 *         }
 *     }
 *
 *   Step 3: Use it
 *
 *     public class CreateUserRequest {
 *         @NotBlank
 *         @Email
 *         @UniqueEmail          // ← YOUR CUSTOM ANNOTATION
 *         private String email;
 *     }
 *
 * ───────────────────────────────────────────────────────────────────────
 * 10. KEY TAKEAWAYS FOR INTERVIEWS
 * ───────────────────────────────────────────────────────────────────────
 *
 *   ▸ DTO = Data Transfer Object — separates API contract from DB entity
 *   ▸ Never expose Entity directly to client (security + coupling)
 *   ▸ Use different DTOs for different use cases (CreateRequest, Response,
 *     Summary, Detail)
 *   ▸ Validation stack: Spring Boot → Spring MVC → Jakarta Validation API
 *     → Hibernate Validator
 *   ▸ @Valid triggers validation on @RequestBody/@RequestParam
 *   ▸ On failure: MethodArgumentNotValidException → @ControllerAdvice catpures
 *   ▸ Hibernate Validator is the actual implementation (NOT Spring)
 *   ▸ Spring Boot auto-configures it via spring-boot-starter-validation
 *   ▸ Custom validators: @Constraint + ConstraintValidator interface
 *   ▸ @Validated adds group support (@Valid doesn't have groups)
 */
public class DtosAndValidaters {
}
