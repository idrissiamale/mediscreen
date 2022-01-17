package com.mdiabetesReport.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static com.mdiabetesReport.helper.HelperClass.*;
import static org.junit.jupiter.api.Assertions.*;

public class HelperClassTest {
    @Test
    @DisplayName("Checking that age is correctly calculated")
    public void shouldReturnAge() {
        String birthDate = "2000-01-01";

        int actualAge = getAge((birthDate));

        assertEquals(22, actualAge);
    }

    @Test
    @DisplayName("Checking that DateTimeParseException is thrown when error on birth date")
    public void shouldThrowExceptionWhenErrorOnBirthDate() {
        String birthDate = "18/12/2000";

        assertThrows(DateTimeParseException.class, () -> getAge(birthDate));
    }

    @Test
    @DisplayName("Checking that the given number is in range")
    public void shouldReturnTrueWhenNumberIsInRange() {
        int number = 2;
        int low = 1;
        int high = 3;

        boolean inRange = betweenInclusiveAndInclusive(number, low, high);

        assertTrue(inRange);
    }

    @Test
    @DisplayName("Checking that the given number is in range even if it's equal to the highest value (because it's inclusive)")
    public void shouldReturnTrueWhenNumberIsInRangeEvenIfItsEqualToTheHighestValue() {
        int number = 2;
        int low = 0;
        int high = 2;

        boolean inRange = betweenInclusiveAndInclusive(number, low, high);

        assertTrue(inRange);
    }

    @Test
    @DisplayName("Checking that the given number is not in the range even if it's equal to the higher value (because it's exclusive)")
    public void shouldReturnFalseWhenNumberIsNotInRange() {
        int number = 2;
        int low = 0;
        int high = 2;

        boolean inRange = betweenInclusiveAndExclusive(number, low, high);

        assertFalse(inRange);
    }
}
