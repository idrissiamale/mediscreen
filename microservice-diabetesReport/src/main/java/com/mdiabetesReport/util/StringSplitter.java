package com.mdiabetesReport.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class StringSplitter {
    public static LinkedHashSet<String> getStringListOfSplitedNotes(List<String> patientNotes) {
        String delimiters = "[, ?.@*:/!]+";
        LinkedHashSet<String> stringList = new LinkedHashSet<>();
        for (String patientNote : patientNotes) {
            List<String> splitNotes = Arrays.asList(patientNote.split(delimiters));
            stringList.addAll(splitNotes);
        }
        return stringList;
    }
}
