package java_17.records;

public class LombokVsRecord {
}

/*

Lombok reduces boilerplate using annotations,
but Records are an official Java language feature
designed specifically for immutable data carrier classes.

Records provide language-level semantics,
immutability, JVM support, better framework integration,
and cleaner modeling of read-only data.


If Lombok already removes boilerplate code,
then why did Java introduce Records?

Lombok is a third-party compile-time library,
while Records are an official Java language feature
designed specifically for immutable data models.

Biggest Difference = DESIGN INTENT

Lombok

Lombok says:  "Generate code for me."

Record

Record says:     "This class is PURE immutable DATA."
This is a language-level semantic meaning.

Record Represents DATA, Not BEHAVIOR, data carriers

Records are Safer  => Because immutable.

Immutable objects:
thread-safe
predictable
safer in concurrency








 */
