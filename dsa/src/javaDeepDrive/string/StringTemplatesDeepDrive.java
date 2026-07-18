package javaDeepDrive.string;
/*
===============================================================================
String Templates Deep Drive
===============================================================================

String Templates introduced modern string interpolation in Java.

Goal:
- cleaner string creation
- readable formatting
- remove messy concatenation

===============================================================================
What Problem Existed Before
===============================================================================

Before String Templates:

We mostly used:

1. + concatenation
2. String.format()
3. StringBuilder

Problems:
- unreadable code
- too many + operators
- formatting complexity
- difficult maintenance

===============================================================================
Java Version History
===============================================================================

Java 8:
- normal string concatenation
- StringBuilder internally used

Java 13:
- Text Blocks introduced
- multiline strings supported

Java 21:
- String Templates introduced (Preview Feature)

Important:

String Templates are preview feature.

Meaning:
- not final/stable yet
- future changes possible

===============================================================================
Before Java 13
===============================================================================

Multiline strings were difficult.

Example:

String json = "{\n" +
              "  \"name\": \"Pankaj\"\n" +
              "}";

Very ugly.

===============================================================================
Java 13 Text Blocks
===============================================================================

Java introduced:

"""
multiline text
"""

Example:

String json = """
{
   "name": "Pankaj"
}
""";

Much cleaner.

===============================================================================
What Text Blocks Solve
===============================================================================

Text Blocks solve:
- multiline formatting
- readability

But still missing:
- variable interpolation

===============================================================================
Before String Templates (Old Style)
===============================================================================
*/

public class StringTemplatesDeepDrive {

    public static void main(String[] args) {

        String name = "Pankaj";
        int age = 25;

        // old concatenation style
        String result =
                "My name is " + name +
                        " and age is " + age;

        System.out.println(result);
    }
}

/*
===============================================================================
Output
===============================================================================

My name is Pankaj and age is 25

===============================================================================
Problems With Old Style
===============================================================================

Problems:
- too many +
- difficult readability
- formatting issues
- hard to maintain

===============================================================================
String.format() Alternative
===============================================================================
*/

class StringFormatExample {

    public static void main(String[] args) {

        String name = "Pankaj";
        int age = 25;

        String result =
                String.format(
                        "My name is %s and age is %d",
                        name,
                        age
                );

        System.out.println(result);
    }
}

/*
===============================================================================
Output
===============================================================================

My name is Pankaj and age is 25

===============================================================================
Problems With String.format()
===============================================================================

Problems:
- verbose
- difficult format specifiers
- runtime formatting mistakes possible

===============================================================================
Java 21 String Templates
===============================================================================

Syntax:

STR."Hello \{name}"

Meaning:
- insert variable/expression inside string

This process called:

String Interpolation

===============================================================================
Simple String Template Example
===============================================================================
*/

class StringTemplateExample {

    public static void main(String[] args) {

        String name = "Pankaj";
        int age = 25;

        String result =
                STR."My name is \{name} and age is \{age}";

        System.out.println(result);
    }
}

/*
===============================================================================
Output
===============================================================================

My name is Pankaj and age is 25

===============================================================================
Meaning of \{} Syntax
===============================================================================

\{name}

Means:
- evaluate expression
- convert to string
- insert into template

===============================================================================
Expressions Allowed
===============================================================================
*/

class ExpressionExample {

    public static void main(String[] args) {

        int a = 10;
        int b = 20;

        String result =
                STR."Sum is \{a + b}";

        System.out.println(result);
    }
}

/*
===============================================================================
Output
===============================================================================

Sum is 30

===============================================================================
Method Calls Allowed
===============================================================================
*/

class MethodCallExample {

    public static void main(String[] args) {

        String name = "pankaj";

        String result =
                STR."Uppercase: \{name.toUpperCase()}";

        System.out.println(result);
    }
}

/*
===============================================================================
Output
===============================================================================

Uppercase: PANKAJ

===============================================================================
Text Blocks + String Templates
===============================================================================

Very powerful combination.

Useful for:
- JSON
- SQL
- HTML
- API payloads

===============================================================================
Example
===============================================================================
*/

class JsonTemplateExample {

    public static void main(String[] args) {

        String name = "Pankaj";

        String json = STR."""
                {
                    "name": "\{name}"
                }
                """;

        System.out.println(json);
    }
}

/*
===============================================================================
Output
===============================================================================

{
   "name": "Pankaj"
}

===============================================================================
Internal Working
===============================================================================

Step 1:
- Java reads template

Step 2:
- expressions inside \{} evaluated

Step 3:
- converted to String

Step 4:
- final string generated

===============================================================================
What is STR?
===============================================================================

STR is:

Template Processor.

Responsible for:
- processing template
- evaluating expressions
- producing final string

===============================================================================
Syntax Breakdown
===============================================================================

STR."Hello \{name}"

STR
    -> template processor

.
    -> processor invocation

" "
    -> template literal

\{}
    -> embedded expression

===============================================================================
Important Difference
===============================================================================

Text Block:
- multiline string feature

String Template:
- variable interpolation feature

Can work together.

===============================================================================
Real Industry Use Cases
===============================================================================

Useful for:
- SQL queries
- JSON creation
- logging
- HTML templates
- API requests
- dynamic messages

===============================================================================
SQL Example
===============================================================================
*/

class SqlExample {

    public static void main(String[] args) {

        int id = 101;

        String query =
                STR."SELECT * FROM users WHERE id = \{id}";

        System.out.println(query);
    }
}

/*
===============================================================================
Output
===============================================================================

SELECT * FROM users WHERE id = 101

===============================================================================
Compare Old vs New
===============================================================================

Old:

"Hello " + name

New:

STR."Hello \{name}"

===============================================================================
Difference Between String Template and StringBuilder
===============================================================================

String Template:
- readability feature
- syntax improvement
- interpolation support

StringBuilder:
- mutable object
- performance optimization
- efficient concatenation

Different purposes.

===============================================================================
Important Interview Questions
===============================================================================

Q) In which Java version String Templates introduced?

Java 21 (Preview Feature)

-------------------------------------------------------------------------------

Q) What is String Interpolation?

Embedding variables directly inside strings.

-------------------------------------------------------------------------------

Q) What is STR?

Template processor used to process String Templates.

-------------------------------------------------------------------------------

Q) Difference between Text Blocks and String Templates?

Text Blocks:
- multiline strings

String Templates:
- variable interpolation

===============================================================================
Most Important Interview Line
===============================================================================

String Templates provide a concise and readable way to create strings by embedding expressions directly inside string literals.

===============================================================================
Quick Revision
===============================================================================

Before:
    "Hello " + name

Java 13:
    Text Blocks introduced

Java 21:
    STR."Hello \{name}"

===============================================================================
*/
