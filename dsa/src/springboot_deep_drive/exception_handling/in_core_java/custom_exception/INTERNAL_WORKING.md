# Internal Working — Custom Exception Handling

## ErrorCode (Enum)
JVM loads enum once at startup. Each constant is a singleton — constructor runs once per constant. Each stores a `code` + `message` string pair.

## ApplicationException
Extends `RuntimeException`. Constructor calls `super(errorCode.getMessage())` which chains up to `Throwable.detailMessage`. Also keeps an `ErrorCode` reference. When `throw` executes, JVM instantly stops the method and unwinds the call stack.

## StudentNotFoundException
Extends `ApplicationException`. Passes `ErrorCode` to super. Object allocated on Heap → constructor chains through RuntimeException → Exception → Throwable → Throwable stores `detailMessage` + captures stack trace.

## StudentManager
Calls `DataStore.STUDENTS.get(id)`. HashMap internally computes `hashCode()` → bucket index → retrieves entry or `null`. If `null`, `new StudentNotFoundException(...)` creates Heap object, `throw` keyword stops method abruptly, JVM unwinds stack.

## DataStore
Static `HashMap` initialized once in `<clinit>` (static block). JVM creates HashMap on Heap, `STUDENTS` reference points to it. All classes share this single instance (singleton-like).

## ErrorUtil
`build()` receives Exception reference. `instanceof ApplicationException` check — if true, extracts `ErrorCode` and builds `ErrorResponse`. If false, falls back to `UNKNOWN_ERROR`. Always returns a valid `ErrorResponse`.

## ErrorResponse
Constructor calls `LocalDateTime.now()` (reads system clock). Stores code, message, details as Strings. `toString()` formats all four fields into a fixed-layout string.

## ConsolePrinter
`print()` calls `System.out.println(response)` which internally invokes `response.toString()` and writes the result to stdout (console via native I/O).

## StudentDemo
Calls `manager.findStudentById(999)`. HashMap.get(999) returns `null` → StudentManager throws StudentNotFoundException → JVM unwinds stack to `catch` → ErrorUtil.build() creates ErrorResponse → ConsolePrinter prints it.
