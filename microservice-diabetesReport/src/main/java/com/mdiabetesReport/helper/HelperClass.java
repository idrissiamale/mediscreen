package com.mdiabetesReport.helper;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A helper class used to convert the birth date into age and to check if an integer is in a given range.
 */
public class HelperClass {
    /**
     * Converting birth date into years to get age.
     *
     * @param birthDate, it refers to person's date of birth.
     * @return age which is the difference between the current date and the date of birth.
     * @throws DateTimeParseException if an error occurs during parsing.
     */
    public static int getAge(String birthDate) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(birthDate, formatter);
        Period difference = Period.between(birthday, today);
        return difference.getYears();
    }

    /**
     * Checking if the number is in the given range. The range is an exclusive one.
     *
     * @param number, it refers to the integer for which we want to check if it's within the range.
     * @param low,    the minimum value of the range.
     * @param high,   the maximum value of the range.
     * @return true if the number is within the range. False if not.
     */
    public static boolean betweenInclusiveAndExclusive(int number, int low, int high) {
        return number >= low && number < high;
    }

    /**
     * Checking if the number is in the given range. The range is an inclusive one.
     *
     * @param number, it refers to the integer for which we want to check if it's within the range.
     * @param low,    the minimum value of the range.
     * @param high,   the maximum value of the range.
     * @return true if the number is within the range. False if not.
     */
    public static boolean betweenInclusiveAndInclusive(int number, int low, int high) {
        return number >= low && number <= high;
    }
}
