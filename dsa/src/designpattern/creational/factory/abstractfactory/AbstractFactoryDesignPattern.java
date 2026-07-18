package designpattern.creational.factory.abstractfactory;

public class AbstractFactoryDesignPattern {

    /*
        ==========================================================
        ABSTRACT FACTORY DESIGN PATTERN
        ==========================================================

        MOST IMPORTANT CREATIONAL DESIGN PATTERN.

        Slightly advanced pattern.

        Used heavily in:
        - Spring
        - UI Frameworks
        - Enterprise Applications
        - Cross-platform systems

     */



    /*
        ==========================================================
        BEFORE UNDERSTANDING ABSTRACT FACTORY
        ==========================================================

        First understand progression:

        ----------------------------------------------------------

        1. SIMPLE FACTORY

            One factory creates all objects.

        ----------------------------------------------------------

        2. FACTORY METHOD

            Separate factory for each object.

        ----------------------------------------------------------

        3. ABSTRACT FACTORY

            Factory of related factories/products.

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Abstract Factory Pattern creates
        families of related objects.

     */



    /*
        ==========================================================
        VERY IMPORTANT KEYWORD
        ==========================================================

            FAMILY OF OBJECTS

        NOT single object.

     */



    /*
        ==========================================================
        REAL WORLD EXAMPLE
        ==========================================================

        Suppose:

        You are building cross-platform UI system.

        Platforms:
        - Windows
        - Mac

        UI Components:
        - Button
        - Checkbox

        ----------------------------------------------------------

        WINDOWS FAMILY:
        - WindowsButton
        - WindowsCheckbox

        MAC FAMILY:
        - MacButton
        - MacCheckbox

        ----------------------------------------------------------

        We want:

        WindowsFactory
            -> creates Windows family

        MacFactory
            -> creates Mac family

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        One factory creates multiple related objects.

     */



    public static void main(String[] args) {

        /*
            Suppose app running on Windows.

            So we use Windows factory.
         */
        GUIFactory factory =
                new WindowsFactory();



        /*
            Factory creates related objects.
         */
        Button button =
                factory.createButton();



        Checkbox checkbox =
                factory.createCheckbox();



        button.paint();

        checkbox.check();



        /*
            OUTPUT:

            Windows Button Created

            Windows Checkbox Checked

         */



        /*
            ======================================================
            NOW SWITCH PLATFORM
            ======================================================

            Just change factory.
         */
        GUIFactory macFactory =
                new MacFactory();



        Button macButton =
                macFactory.createButton();

        Checkbox macCheckbox =
                macFactory.createCheckbox();



        macButton.paint();

        macCheckbox.check();



        /*
            OUTPUT:

            Mac Button Created

            Mac Checkbox Checked

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Factory creates RELATED OBJECTS.

            WindowsFactory creates:
            - WindowsButton
            - WindowsCheckbox

            MacFactory creates:
            - MacButton
            - MacCheckbox

         */



        /*
            ======================================================
            WHY ABSTRACT FACTORY NEEDED?
            ======================================================

            Without Abstract Factory:

            Client manually mixes objects.

            Example:
            - WindowsButton
            - MacCheckbox

            UI becomes inconsistent.

         */



        /*
            ======================================================
            ABSTRACT FACTORY BENEFIT
            ======================================================

            Ensures compatible object families.

            Windows factory only creates:
            Windows-compatible components.

         */



        /*
            ======================================================
            REAL LIFE ANALOGY
            ======================================================

            Think:

            Furniture showroom.

            ModernFurnitureFactory:
            - ModernChair
            - ModernTable
            - ModernSofa

            VictorianFurnitureFactory:
            - VictorianChair
            - VictorianTable
            - VictorianSofa

            Entire family produced together.

         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client selects factory family.

            STEP-2:
            Factory creates related objects.

            STEP-3:
            Client uses abstract interfaces.

         */



        /*
            ======================================================
            MOST IMPORTANT DIFFERENCE
            ======================================================

            Factory Method:
            creates ONE product.

            Abstract Factory:
            creates RELATED PRODUCT FAMILIES.

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            VERY common in:

            - GUI frameworks
            - Database drivers
            - Cloud SDKs
            - Payment gateways
            - Theme systems
            - Enterprise frameworks

         */



        /*
            ======================================================
            SPRING FRAMEWORK USAGE
            ======================================================

            Spring internally creates families
            of related beans/configurations.

            Bean factories follow concepts
            similar to abstract factory.

         */



        /*
            ======================================================
            ADVANTAGES
            ======================================================

            - consistent object families
            - loose coupling
            - scalable architecture
            - clean code
            - easy product family switching

         */



        /*
            ======================================================
            DISADVANTAGES
            ======================================================

            - many classes/interfaces
            - complexity increases
            - harder learning curve

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Main idea of Abstract Factory?

            Answer:
            Create families of related objects.



            Q2:
            Difference from Factory Method?

            Answer:

            Factory Method:
            creates one product.

            Abstract Factory:
            creates product families.



            Q3:
            What is "family of objects"?

            Answer:
            Related compatible objects.



            Q4:
            Real-world example?

            Answer:
            Cross-platform UI systems.



            Q5:
            Biggest advantage?

            Answer:
            Ensures compatible products.



            Q6:
            Which principle supported?

            Answer:
            Open Closed Principle.



            Q7:
            Client depends on?

            Answer:
            Abstract interfaces.

         */



        /*
            ======================================================
            IS ABSTRACT FACTORY COMPLETE?
            ======================================================

            BASIC TO INTERMEDIATE:
            YES COMPLETE.

            Still remaining advanced topics:

            - Builder Pattern
            - Prototype Pattern
            - Factory vs Builder
            - Spring BeanFactory internals
            - Enterprise factory architecture
            - Dependency Injection relation
            - Real framework implementations

         */

    }

}



/*
    ==========================================================
    PRODUCT-1 PARENT
    ==========================================================
 */
interface Button {

    void paint();
}



/*
    ==========================================================
    PRODUCT-2 PARENT
    ==========================================================
 */
interface Checkbox {

    void check();
}



/*
    ==========================================================
    WINDOWS PRODUCT FAMILY
    ==========================================================
 */
class WindowsButton implements Button {

    @Override
    public void paint() {

        System.out.println("Windows Button Created");
    }
}



class WindowsCheckbox implements Checkbox {

    @Override
    public void check() {

        System.out.println("Windows Checkbox Checked");
    }
}



/*
    ==========================================================
    MAC PRODUCT FAMILY
    ==========================================================
 */
class MacButton implements Button {

    @Override
    public void paint() {

        System.out.println("Mac Button Created");
    }
}



class MacCheckbox implements Checkbox {

    @Override
    public void check() {

        System.out.println("Mac Checkbox Checked");
    }
}



/*
    ==========================================================
    ABSTRACT FACTORY
    ==========================================================

    Creates related product families.
 */
interface GUIFactory {

    Button createButton();

    Checkbox createCheckbox();
}



/*
    ==========================================================
    FACTORY FAMILY-1
    ==========================================================
 */
class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {

        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {

        return new WindowsCheckbox();
    }
}



/*
    ==========================================================
    FACTORY FAMILY-2
    ==========================================================
 */
class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {

        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {

        return new MacCheckbox();
    }
}