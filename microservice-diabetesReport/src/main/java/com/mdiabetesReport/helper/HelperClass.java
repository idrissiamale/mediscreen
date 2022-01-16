package com.mdiabetesReport.helper;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HelperClass {
    public static int getAge(String birthdate) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(birthdate, formatter);
        Period difference = Period.between(birthday, today);
        return difference.getYears();
    }

    public static boolean betweenInclusiveAndExclusive(int number, int low, int high) {
        return number >= low && number < high;
    }

    public static boolean betweenInclusiveAndInclusive(int number, int low, int high) {
        return number >= low && number <= high;
    }
}
