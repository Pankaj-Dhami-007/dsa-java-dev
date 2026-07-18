package designpattern.creational.builder;

public class BuilderDesignPattern {

    /*
        ==========================================================
        BUILDER DESIGN PATTERN
        ==========================================================

        VERY IMPORTANT DESIGN PATTERN.

        EXTREMELY heavily used in:
        - Spring Boot
        - Lombok
        - Java APIs
        - Enterprise applications
        - Immutable object creation

     */



    /*
        ==========================================================
        MAIN PROBLEM
        ==========================================================

        Suppose we have User class.

        User contains:

        - id
        - name
        - email
        - phone
        - address
        - age
        - gender
        - country

        ----------------------------------------------------------

        Creating object becomes messy.

     */



    /*
        ==========================================================
        NORMAL OBJECT CREATION PROBLEM
        ==========================================================

        User user = new User(
                1,
                "Pankaj",
                "abc@gmail.com",
                "9999999999",
                "Delhi",
                25,
                "Male",
                "India"
        );

        Problems:

        1. Too many constructor parameters

        2. Hard to remember parameter order

        3. Readability poor

        4. Optional fields difficult

        5. Constructor explosion problem

     */



    /*
        ==========================================================
        CONSTRUCTOR EXPLOSION PROBLEM
        ==========================================================

        You start creating multiple constructors.

        Example:

        User(id,name)

        User(id,name,email)

        User(id,name,email,phone)

        User(id,name,email,phone,address)

        and so on...

        Huge mess.

     */



    /*
        ==========================================================
        BUILDER PATTERN SOLUTION
        ==========================================================

        Build object step-by-step.

        Example:

            User user = new User.Builder()
                            .setId(1)
                            .setName("Pankaj")
                            .setEmail("abc@gmail.com")
                            .build();

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        Separate object construction
        from final object representation.

     */



    /*
        ==========================================================
        VERY SIMPLE DEFINITION
        ==========================================================

        Builder Pattern constructs
        complex objects step-by-step.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think about ordering burger.

        Step-by-step customization:

        - cheese
        - mayo
        - onion
        - extra patty
        - spicy sauce

        Finally:
        burger prepared.

        Same idea in Builder Pattern.

     */



    public static void main(String[] args) {

        /*
            Step-by-step object creation.

            Cleaner and readable.
         */
        User user = new User.Builder()

                .setId(101)

                .setName("Pankaj")

                .setEmail("pankaj@gmail.com")

                .setPhone("9999999999")

                .setCity("Delhi")

                .setAge(25)

                .build();



        System.out.println(user);



        /*
            OUTPUT:

            User{
                id=101,
                name='Pankaj',
                email='pankaj@gmail.com',
                phone='9999999999',
                city='Delhi',
                age=25
            }

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Instead of massive constructor:

                new User(...huge parameters...)

            We build object step-by-step.

         */



        /*
            ======================================================
            METHOD CHAINING
            ======================================================

            Builder methods return:

                this

            So methods chain continuously.

            Example:

                .setId()
                .setName()
                .setEmail()

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Builder object created.

            STEP-2:
            Setter-like methods initialize fields.

            STEP-3:
            build() creates final object.

         */



        /*
            ======================================================
            WHY BUILDER BETTER?
            ======================================================

            - readable
            - maintainable
            - flexible
            - supports optional fields
            - avoids constructor confusion

         */



        /*
            ======================================================
            IMMUTABLE OBJECTS
            ======================================================

            Builder Pattern commonly used
            with immutable classes.

            Final object fields can become final.

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            VERY heavily used.

            Examples:

            StringBuilder

            Lombok:
                @Builder

            Spring Boot:
                ResponseEntity builder APIs

            Java APIs:
                HttpRequest.Builder

         */



        /*
            ======================================================
            LOMBOK EXAMPLE
            ======================================================

            @Builder
            public class User {

            }

            Lombok automatically generates
            Builder Pattern internally.

         */



        /*
            ======================================================
            FACTORY vs BUILDER
            ======================================================

            Factory:
            creates object directly.

            Builder:
            constructs object step-by-step.

         */



        /*
            ======================================================
            WHEN TO USE BUILDER?
            ======================================================

            Use when:

            - object has many fields
            - many optional parameters
            - object creation complex
            - readability important

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - readable code
            - avoids huge constructors
            - flexible object creation
            - cleaner APIs
            - supports immutability

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - more code
            - extra builder class
            - slightly more complexity

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main purpose of Builder Pattern?

            Answer:
            Construct complex objects
            step-by-step.



            Q2:
            Which problem does it solve?

            Answer:
            Constructor explosion problem.



            Q3:
            Main advantage?

            Answer:
            Readable and flexible object creation.



            Q4:
            Which concept heavily used?

            Answer:
            Method chaining.



            Q5:
            Difference from Factory Pattern?

            Answer:

            Factory:
            object created directly.

            Builder:
            object built step-by-step.



            Q6:
            Real-world Java example?

            Answer:
            StringBuilder.



            Q7:
            Which annotation generates Builder?

            Answer:
            Lombok @Builder.



            Q8:
            Why Builder useful for immutable objects?

            Answer:
            Fields initialized during construction only.

         */



        /*
            ======================================================
            IS BUILDER PATTERN COMPLETE?
            ======================================================

            BASIC TO INTERMEDIATE:
            YES COMPLETE.

            Still remaining advanced topics:

            - immutable builders
            - Lombok builder internals
            - Director class
            - Builder vs Factory
            - Fluent interfaces
            - Real Spring APIs
            - Thread safety considerations

         */

    }

}



/*
    ==========================================================
    PRODUCT CLASS
    ==========================================================
 */
class User {

    private int id;

    private String name;

    private String email;

    private String phone;

    private String city;

    private int age;



    /*
        private constructor

        Object only created via Builder.
     */
    private User(Builder builder) {

        this.id = builder.id;

        this.name = builder.name;

        this.email = builder.email;

        this.phone = builder.phone;

        this.city = builder.city;

        this.age = builder.age;
    }



    /*
        ==========================================================
        BUILDER CLASS
        ==========================================================

        Static inner class.

        Responsible for object construction.
     */
    static class Builder {

        private int id;

        private String name;

        private String email;

        private String phone;

        private String city;

        private int age;



        /*
            Builder methods return this.

            Enables method chaining.
         */
        public Builder setId(int id) {

            this.id = id;

            return this;
        }



        public Builder setName(String name) {

            this.name = name;

            return this;
        }



        public Builder setEmail(String email) {

            this.email = email;

            return this;
        }



        public Builder setPhone(String phone) {

            this.phone = phone;

            return this;
        }



        public Builder setCity(String city) {

            this.city = city;

            return this;
        }



        public Builder setAge(int age) {

            this.age = age;

            return this;
        }



        /*
            Final object creation.
         */
        public User build() {

            return new User(this);
        }

    }



    @Override
    public String toString() {

        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                '}';
    }

}