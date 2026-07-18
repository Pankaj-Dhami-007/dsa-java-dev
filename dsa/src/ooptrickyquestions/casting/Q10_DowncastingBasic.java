package ooptrickyquestions.casting;

/*
    ==========================================================
    QUESTION-2
    ==========================================================

    DOWNCASTING
    WHAT WILL BE OUTPUT?
 */

public class Q10_DowncastingBasic {

    public static void main(String[] args) {

        VehicleQ10 vehicle =
                new CarQ10();

        CarQ10 car =
                (CarQ10) vehicle;

        car.music();
    }

}



class VehicleQ10 {

    public void start() {

        System.out.println(
                "Vehicle Started"
        );
    }

}



class CarQ10 extends VehicleQ10 {

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

    Music Playing

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Downcasting:

        Parent reference
            ->
        Child reference

    SAFE because:
        actual object is CarQ10

 */