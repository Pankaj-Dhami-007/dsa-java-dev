package java_17.text_blocks;

/*
 * ===============================================================
 * JAVA 17 TEXT BLOCKS
 * ===============================================================
 *
 * Text Blocks are used for:
 *
 * -> multi-line strings
 * -> better readability
 * -> cleaner formatting
 *
 *
 * BEFORE JAVA 17
 * ------------------------
 * Multi-line strings were difficult:
 *
 * -> \n
 * -> string concatenation
 * -> escaping quotes
 *
 *
 * JAVA 17 SOLUTION
 * ------------------------
 *
 * Triple double quotes:
 *
 * """
 * text
 * """
 */

public class TextBlocksDemo {

    public static void main(String[] args) {

        // =========================================================
        // BEFORE JAVA 17
        // =========================================================

        String oldJson =

                "{\n" +
                        "   \"name\" : \"Rahul\",\n" +
                        "   \"city\" : \"Delhi\"\n" +
                        "}";



        System.out.println(oldJson);




        /*
         * Problems:
         *
         * -> ugly code
         * -> hard to maintain
         * -> many escape characters
         * -> difficult formatting
         */




        // =========================================================
        // JAVA 17 TEXT BLOCK
        // =========================================================

        String newJson = """

                {
                    "name" : "Rahul",
                    "city" : "Delhi"
                }

                """;



        System.out.println(newJson);




        /*
         * Advantages:
         *
         * -> readable
         * -> clean
         * -> no escaping
         * -> maintainable
         */




        // =========================================================
        // SQL QUERY EXAMPLE
        // =========================================================

        /*
         * BEFORE JAVA 17
         */

        String oldSql =

                "SELECT * FROM users " +
                        "WHERE age > 18 " +
                        "ORDER BY name";



        System.out.println(oldSql);




        /*
         * JAVA 17 TEXT BLOCK
         */

        String newSql = """

                SELECT *
                FROM users
                WHERE age > 18
                ORDER BY name

                """;



        System.out.println(newSql);




        // =========================================================
        // HTML TEMPLATE EXAMPLE
        // =========================================================

        String html = """

                <html>
                    <body>
                        <h1>Hello Java 17</h1>
                    </body>
                </html>

                """;



        System.out.println(html);




        // =========================================================
        // JSON API REQUEST EXAMPLE
        // =========================================================

        String requestBody = """

                {
                    "username": "rahul",
                    "password": "12345"
                }

                """;



        System.out.println(requestBody);




        // =========================================================
        // ESCAPE CHARACTERS STILL POSSIBLE
        // =========================================================

        String text = """

                Hello \"Rahul\"
                Welcome to Java 17

                """;



        System.out.println(text);




        // =========================================================
        // FORMATTING SUPPORT
        // =========================================================

        String name = "Rahul";



        String formattedText = """

                Hello %s
                Welcome to Java 17

                """.formatted(name);



        System.out.println(formattedText);




        // =========================================================
        // INDENTATION HANDLING
        // =========================================================

        /*
         * Java automatically removes
         * common indentation.
         */

        String message = """

                Java 17
                Text Blocks
                Example

                """;



        System.out.println(message);




        // =========================================================
        // REAL PROJECT USE CASES
        // =========================================================

        /*
         * USE CASE 1
         * SQL Queries
         */




        /*
         * USE CASE 2
         * JSON Payloads
         */




        /*
         * USE CASE 3
         * HTML Templates
         */




        /*
         * USE CASE 4
         * XML Content
         */




        /*
         * USE CASE 5
         * GraphQL Queries
         */




        /*
         * USE CASE 6
         * Email Templates
         */




        // =========================================================
        // IMPORTANT INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Why Text Blocks introduced?
         *
         * To simplify multi-line strings.
         */




        /*
         * Q2 Main advantage?
         *
         * Better readability.
         */




        /*
         * Q3 Triple quotes syntax?
         *
         * """
         */




        /*
         * Q4 Real use cases?
         *
         * JSON
         * SQL
         * HTML
         * XML
         */




        /*
         * Q5 Can formatting be used?
         *
         * YES
         *
         * .formatted()
         */




        /*
         * Q6 Are escape sequences supported?
         *
         * YES
         */




        /*
         * Q7 Difference between old string
         * and Text Block?
         *
         * Text Blocks:
         * -> cleaner
         * -> readable
         * -> less escaping
         */
    }
}