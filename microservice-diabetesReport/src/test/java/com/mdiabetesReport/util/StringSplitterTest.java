package com.mdiabetesReport.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.mdiabetesReport.util.StringSplitter.getStringListOfSplitedNotes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSplitterTest {
    private List<String> patientNotes;

    @BeforeEach
    public void setUp() {
        patientNotes = new ArrayList<>();
        patientNotes.add("Le patient déclare avoir mal à la tête et des insomnies");
        patientNotes.add("Ses symptomes persistent depuis deux semaines déjà");
    }

    @Test
    @DisplayName("Checking that the notes were splited well and that there are now 18 words")
    public void shouldReturnSplitedNotes() {
        LinkedHashSet<String> stringListOfSplitedNotes = getStringListOfSplitedNotes(patientNotes);

        assertEquals(18, stringListOfSplitedNotes.size());
    }

    @Test
    @DisplayName("Checking that the notes were splited well by testing that the list contains 'symptomes' word")
    public void shouldContainTheWord() {
        LinkedHashSet<String> stringListOfSplitedNotes = getStringListOfSplitedNotes(patientNotes);

        assertTrue(stringListOfSplitedNotes.contains("symptomes"));
    }
}
