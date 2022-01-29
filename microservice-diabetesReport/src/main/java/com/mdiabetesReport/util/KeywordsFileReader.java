package com.mdiabetesReport.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * KeywordsFileReader is a util class which permits to read the Keywords text file.
 */
public class KeywordsFileReader {
    private File file;

    @Autowired
    public KeywordsFileReader(@Value("${keywords.filepath}") String filePath) {
        file = new File(filePath);

    }

    /**
     * Reading all lines from the Keywords text file.
     *
     * @return the lines from the file as a List.
     * @throws IOException if an I/O error occurs reading from the file.
     */
    public List<String> getKeywordsTextFile() throws IOException {
        return Files.readAllLines(file.toPath());
    }
}
