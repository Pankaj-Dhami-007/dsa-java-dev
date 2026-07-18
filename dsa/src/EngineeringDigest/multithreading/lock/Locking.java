package EngineeringDigest.multithreading.lock;

/**
 * ===================================================================================
 * LOCKING MECHANISMS - COMPLETE REFERENCE GUIDE
 * ===================================================================================
 *
 * PACKAGE: EngineeringDigest.multithreading.lock
 * CLASS: Locking
 * PURPOSE: Comprehensive reference for locking mechanisms in Java/Spring Boot/JPA
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 1. WHAT IS LOCKING?
 * ===================================================================================
 * Locking is a mechanism that prevents multiple threads or transactions from
 * modifying the same data simultaneously.
 *
 * PURPOSE:
 * - Maintain data consistency
 * - Prevent: Duplicate records, Lost updates, Dirty reads, Race conditions
 *
 * ===================================================================================
 * 2. RACE CONDITION - REAL WORLD EXAMPLE
 * ===================================================================================
 * A race condition occurs when two or more threads execute the same code simultaneously
 * and the final result depends on which thread finishes first.
 *
 * SCENARIO: Duplicate Attendance Records
 * =====================================
 * Database State: No record exists for userId=1815, date=03-Jul
 *
 * Request A                    Request B (simultaneous)
 * ─────────────────────────────────────────────────────────
 * findTop()                    findTop()
 * ↓                            ↓
 * No record found              No record found
 * ↓                            ↓
 * Create Entity                Create Entity
 * ↓                            ↓
 * Save (ID=211)                Save (ID=212)
 *
 * RESULT: Duplicate records created (ID=211, ID=212)
 * PROBLEM: Race Condition - both threads saw "no record" simultaneously
 *
 * SOLUTION: Locking (Pessimistic or Optimistic)
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 3. TYPES OF LOCKING STRATEGIES
 * ===================================================================================
 *
 * A. PESSIMISTIC LOCKING
 * ======================
 * PHILOSOPHY: "Assume conflicts WILL happen - lock immediately"
 *
 * MECHANISM:
 * - Acquire lock immediately when reading data
 * - Other transactions MUST wait for lock release
 * - Lock released on transaction commit/rollback
 *
 * ANALOGY: ATM Machine
 * ─────────────────────────────────────
 * Person A inserts card → Locks ATM
 * Person B must wait until A finishes
 * Only ONE user at a time
 *
 * SPRING BOOT / JPA IMPLEMENTATION:
 * ───────────────────────────────────
 * @Lock(LockModeType.PESSIMISTIC_WRITE)
 * Optional<AttendanceEntity> findByUserIdAndAttendanceDate(Long userId, LocalDate date);
 *
 * FLOW:
 * ─────
 * Request A → Acquire Lock → Update → Commit → Release Lock → Request B proceeds
 *
 * LOCK MODES:
 * ───────────
 * 1. PESSIMISTIC_READ
 *    - Allows: Multiple concurrent reads
 *    - Blocks: Writes
 *    - Use Case: Many readers, no writers allowed
 *
 * 2. PESSIMISTIC_WRITE (MOST COMMON)
 *    - Blocks: Reads (DB dependent), Writes, Updates, Deletes
 *    - Only ONE transaction at a time
 *    - Use Case: Critical updates (banking, inventory, attendance)
 *
 * 3. PESSIMISTIC_FORCE_INCREMENT
 *    - Used with @Version column
 *    - Forces version increment even on read
 *    - Rarely used
 *
 * ADVANTAGES:
 * ───────────
 * ✔ Very safe - prevents all conflicts
 * ✔ Prevents duplicate updates
 * ✔ Ideal for: Banking, Inventory, Attendance approval
 *
 * DISADVANTAGES:
 * ────────────────
 * ❌ Threads wait (blocking)
 * ❌ Slower performance
 * ❌ Deadlock possible
 * ❌ Not ideal for high-concurrency reads
 *
 * USE CASES:
 * ──────────
 * ✔ Banking transactions
 * ✔ Inventory management
 * ✔ Attendance approval
 * ✔ Ticket booking
 *
 *
 * B. OPTIMISTIC LOCKING
 * =====================
 * PHILOSOPHY: "Assume conflicts are RARE - check version before update"
 *
 * MECHANISM:
 * - No lock acquired on read
 * - Version column (@Version) checked on update
 * - If version changed → OptimisticLockException thrown
 * - Application must handle retry logic
 *
 * EXAMPLE:
 * ────────
 * Employee A reads record (version=5)
 * Employee B reads record (version=5)
 * Employee A updates → version becomes 6 ✓
 * Employee B tries update → version mismatch (expected 5, found 6) → EXCEPTION
 *
 * ENTITY SETUP:
 * ─────────────
 * @Entity
 * public class Employee {
 *     @Id Long id;
 *     String name;
 *
 *     @Version
 *     private Integer version;  // Hibernate auto-manages this
 * }
 *
 * LOCK MODES:
 * ───────────
 * 1. OPTIMISTIC
 *    - Checks version on update
 *    - Throws OptimisticLockException on conflict
 *
 * 2. OPTIMISTIC_FORCE_INCREMENT
 *    - Forces version increment even on read
 *    - Mostly handled internally by Hibernate
 *
 * ADVANTAGES:
 * ───────────
 * ✔ Fast (no locking overhead)
 * ✔ High performance
 * ✔ No waiting/blocking
 * ✔ Scales well
 *
 * DISADVANTAGES:
 * ────────────────
 * ❌ Exceptions under high concurrency
 * ❌ Requires retry logic in application
 * ❌ Not suitable for critical financial data
 *
 * USE CASES:
 * ──────────
 * ✔ Profile updates
 * ✔ Product catalog updates
 * ✔ Employee information
 * ✔ Configuration data
 * ✔ Low-conflict scenarios
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 4. JAVA-LEVEL LOCKING: synchronized KEYWORD
 * ===================================================================================
 * This is JAVA-LEVEL locking (JVM-level), NOT database locking.
 *
 * SCOPE: Single JVM only
 * SCOPE LIMITATION: Does NOT work across multiple servers/instances
 *
 * SYNTAX:
 * ───────
 * // Method-level lock (locks on 'this' instance)
 * public synchronized void saveAttendance() { }
 *
 * // Block-level lock (more granular)
 * public void saveAttendance() {
 *     synchronized(this) {
 *         // critical section
 *     }
 * }
 *
 * // Static method lock (locks on Class object)
 * public static synchronized void staticMethod() { }
 *
 * BEHAVIOR:
 * ─────────
 * 10 requests → Only 1 thread enters → 9 threads wait
 *
 * ANALOGY: Single-threaded bathroom in a house
 * ──────────────────────────────────────────
 * One bathroom, multiple people → Queue forms
 *
 * ADVANTAGES:
 * ───────────
 * ✔ Simple syntax
 * ✔ Good for in-memory objects (caches, counters, singletons)
 * ✔ No database overhead
 *
 * DISADVANTAGES:
 * ────────────────
 * ❌ DOES NOT WORK across multiple servers
 * ❌ Each JVM has its own lock
 *
 * SERVER 1          SERVER 2          SERVER 3
 * ────────          ────────          ────────
 * [Lock]            [Lock]            [Lock]  ← Separate locks!
 *
 * USE CASES:
 * ───────────
 * ✔ In-memory caches
 * ✔ Counters/Sequence generators
 * ✔ Singleton initialization
 * ✔ Local in-memory objects
 *
 * DO NOT USE FOR:
 * ─────────────────
 * ❌ Database concurrency
 * ❌ Distributed systems
 * ❌ Multi-server deployments
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 5. synchronized vs DATABASE LOCKING - COMPARISON
 * ===================================================================================
 *
 * | Aspect              | synchronized (Java)     | Pessimistic Lock (DB)    |
 * |---------------------|-------------------------|--------------------------|
 * | Scope               | Single JVM only         | Works across ALL servers |
 * | Locks               | Java object monitor     | Database rows/tables     |
 * | Performance         | Fast (no network)       | Slower (DB round-trip)   |
 * | Distributed Systems | ❌ Not suitable         | ✅ Designed for it       |
 * | Deadlock Risk       | Low (single JVM)        | Higher (distributed)     |
 * | Use Case            | In-memory coordination  | Data consistency         |
 *
 * DECISION MATRIX:
 * ──────────────────────────────────────────────────────────────
 * | Scenario                    | Use                     |
 * |-----------------------------|-------------------------|
 * | Single server, in-memory    | synchronized            |
 * | Multi-server, DB critical   | Pessimistic Lock        |
 * | Multi-server, low conflict  | Optimistic Lock         |
 * | High contention, critical   | Pessimistic Lock        |
 * | High read, rare write       | Optimistic Lock         |
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 6. PRACTICAL CODE EXAMPLES
 * ===================================================================================
 */

// ============================================
// ENTITY WITH OPTIMISTIC LOCKING
// ============================================
/*
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Version
    private Integer version;  // Auto-managed by Hibernate

    // Getters, Setters
}
*/

// ============================================
// REPOSITORY WITH PESSIMISTIC LOCKING
// ============================================
/*
@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    // PESSIMISTIC_WRITE - Exclusive lock, blocks reads & writes
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AttendanceEntity a WHERE a.userId = :userId AND a.attendanceDate = :date")
    Optional<AttendanceEntity> findByUserIdAndDateForUpdate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date);

    // PESSIMISTIC_READ - Shared lock, blocks writes only
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT a FROM AttendanceEntity a WHERE a.userId = :userId AND a.attendanceDate = :date")
    Optional<AttendanceEntity> findByUserIdAndDateForRead(
            @Param("userId") Long userId,
            @Param("date") LocalDate date);
}
*/

// ============================================
// SERVICE USAGE EXAMPLES
// ============================================
/*
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    // ========================================
    // PESSIMISTIC LOCKING - Prevent duplicates
    // ========================================
    @Transactional
    public AttendanceEntity markAttendance(Long userId, LocalDate date) {
        // Lock the row (or gap) - blocks other transactions
        Optional<AttendanceEntity> existing = attendanceRepository
                .findByUserIdAndDateForUpdate(userId, date);

        if (existing.isPresent()) {
            throw new DuplicateAttendanceException("Already marked");
        }

        AttendanceEntity entity = new AttendanceEntity();
        entity.setUserId(userId);
        entity.setAttendanceDate(date);
        entity.setStatus(AttendanceStatus.PRESENT);

        return attendanceRepository.save(entity); // Lock released on commit
    }

    // ========================================
    // OPTIMISTIC LOCKING - Version checking
    // ========================================
    @Transactional
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3)
    public EmployeeEntity updateProfile(Long id, EmployeeDTO dto) {
        EmployeeEntity entity = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        // Version checked automatically on save

        return employeeRepository.save(entity);
    }
}
*/

// ============================================
// JAVA SYNCHRONIZED EXAMPLE
// ============================================
/*
@Component
public class InMemoryCounter {

    private int count = 0;

    // Method-level synchronization
    public synchronized int incrementAndGet() {
        return ++count;
    }

    // Block-level synchronization (more granular)
    private final Object lock = new Object();

    public int decrementAndGet() {
        synchronized (lock) {
            return --count;
        }
    }

    // Static synchronization (class-level lock)
    private static int staticCount = 0;

    public static synchronized int incrementStatic() {
        return ++staticCount;
    }
}
*/

/**
 * ===================================================================================
 * 7. DEADLOCK PREVENTION BEST PRACTICES
 * ===================================================================================
 *
 * 1. CONSISTENT LOCK ORDERING
 *    Always acquire locks in the same order across all transactions
 *    Example: Always lock Table A before Table B
 *
 * 2. SET LOCK TIMEOUTS
 *    @QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "5000"))
 *    Or DB level: SET LOCK_TIMEOUT 5000;
 *
 * 3. USE TRY-LOCK WITH TIMEOUT (Java)
 *    ReentrantLock lock = new ReentrantLock();
 *    if (lock.tryLock(5, TimeUnit.SECONDS)) { try { ... } finally { lock.unlock(); } }
 *
 * 4. AVOID NESTED TRANSACTIONS
 *    Keep transactions short and flat
 *
 * 5. DATABASE-LEVEL DEADLOCK DETECTION
 *    Most DBs (MySQL, PostgreSQL) auto-detect and rollback victim
 * ===================================================================================
 */

/**
 * ===================================================================================
 * 8. QUICK DECISION GUIDE
 * ===================================================================================
 *
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ SCENARIO                        │ RECOMMENDED APPROACH                      │
 * ├─────────────────────────────────┼───────────────────────────────────────────┤
 * │ Bank transfer                   │ PESSIMISTIC_WRITE                         │
 * │ Inventory decrement             │ PESSIMISTIC_WRITE                         │
 * │ Ticket booking                  │ PESSIMISTIC_WRITE                         │
 * │ Attendance marking              │ PESSIMISTIC_WRITE                         │
 * │ Profile update                  │ OPTIMISTIC (@Version)                     │
 * │ Product catalog update          │ OPTIMISTIC (@Version)                     │
 * │ Configuration change            │ OPTIMISTIC (@Version)                     │
 * │ In-memory counter (single JVM)  │ synchronized / AtomicInteger              │
 * │ Distributed counter (Redis)     │ Redis Lua script / Redisson lock          │
 * │ Cache invalidation (single JVM) │ synchronized / ReadWriteLock              │
 * └─────────────────────────────────┴───────────────────────────────────────────┘
 *
 * ===================================================================================
 * KEY TAKEAWAYS
 * ===================================================================================
 * 1. DATABASE LOCKING = Data consistency across ALL application instances
 * 2. JAVA SYNCHRONIZED = In-memory coordination within ONE JVM only
 * 3. PESSIMISTIC = Safe but slow, use for critical sections
 * 4. OPTIMISTIC = Fast but needs retry logic, use for low-conflict updates
 * 5. DEADLOCKS = Real risk with pessimistic locking, design lock ordering carefully
 * 6. ALWAYS set lock timeouts to prevent indefinite blocking
 * 7. Test under concurrent load before production
 * ===================================================================================
 */

public class Locking {
    // This class serves as a documentation holder for locking concepts.
    // See class-level Javadoc for complete reference guide.
}