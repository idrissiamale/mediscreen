package com.mdiabetesReport.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class StringSplitter {
    public static LinkedHashSet<String> getStringListOfSplitedNotes(List<String> patientNotes) {
        String delimiters = "[, ?.@*:/!]+";
        LinkedHashSet<String> stringList = new LinkedHashSet<>();
        List<String> notes = patientNotes;
        for (String patientNote : notes) {
            List<String> splitNotes = Arrays.asList(patientNote.split(delimiters));
            stringList.addAll(splitNotes);
        }
        return stringList;
    }
}
