package ooptrickyquestions.casting;

/*
    ==========================================================
    QUESTION-3
    ==========================================================

    INVALID DOWNCASTING
    WHAT HAPPENS?
 */

public class Q11_InvalidDowncasting {

    public static void main(String[] args) {

        VehicleQ11 vehicle =
                new VehicleQ11();



        /*
            RUNTIME ERROR
         */
        CarQ11 car =
                (CarQ11) vehicle;

        car.music();
    }

}



class VehicleQ11 {

}



class CarQ11 extends VehicleQ11 {

    public void music() {

        System.out.println(
                "Music Playing"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    ClassCastException

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Actual object:
        VehicleQ11

    JVM checks heap object.

    Vehicle object cannot become:
        Car object

    So:
        ClassCastException

 */