package basicQuestions;

import java.time.LocalDate;
import java.time.Period;

public class CalAgeFromDOB {


    public static int calAge(int day, int month, int year){

        int currDay = 12, currMonth= 1, currYear=2026;// Current date: 1 Jan 2026
        int age = currYear-year;
        if (currMonth < month ||
                (currMonth == month && currDay < day)) {
            age--;
        }
        return age;
    }

    public static int calAge(LocalDate dob) {
        //LocalDate currentDate = LocalDate.of(2026, 1, 12); // 12 Jan 2026
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }
    public static void main(String[] args) {

        int age = calAge(
                01, 07, 1999  // DOB: 1 july 1999
        );

        System.out.println("Age: " + age);

        LocalDate dob = LocalDate.of(1999, 7, 1);
        int age2 = calAge(dob);
        System.out.println("Age: " + age2);
    }
}
