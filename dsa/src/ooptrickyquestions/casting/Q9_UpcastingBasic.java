package ooptrickyquestions.casting;

/*
    ==========================================================
    QUESTION-1
    ==========================================================

    UPCASTING
    WHAT WILL BE OUTPUT?
 */

public class Q9_UpcastingBasic {

    public static void main(String[] args) {

        VehicleQ9 vehicle =
                new CarQ9();

        vehicle.start();
    }

}



class VehicleQ9 {

    public void start() {

        System.out.println(
                "Vehicle Started"
        );
    }

}



class CarQ9 extends VehicleQ9 {

    @Override
    public void start() {

        System.out.println(
                "Car Started"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    Car Started

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Upcasting:

        Parent reference
            ->
        Child object

    Reference:
        VehicleQ9

    Actual Object:
        CarQ9

    Overridden methods resolved using:
        ACTUAL OBJECT

 */