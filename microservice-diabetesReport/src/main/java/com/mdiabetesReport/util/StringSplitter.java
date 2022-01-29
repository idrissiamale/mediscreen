package com.mdiabetesReport.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * An util class which permits to split and to convert a String into a List of Strings.
 */
public class StringSplitter {
    /**
     * Splitting a String into multiples Strings given the delimiter that separates them and adding it into a List.
     *
     * @param notes, a list of Strings.
     * @return a list of the split Strings.
     */
    public static LinkedHashSet<String> getStringListOfSplitedNotes(List<String> notes) {
        String delimiters = "[, ?.@*:/!]+";
        LinkedHashSet<String> stringList = new LinkedHashSet<>();
        for (String note : notes) {
            List<String> splitNotes = Arrays.asList(note.split(delimiters));
            stringList.addAll(splitNotes);
        }
        return stringList;
    }
}
