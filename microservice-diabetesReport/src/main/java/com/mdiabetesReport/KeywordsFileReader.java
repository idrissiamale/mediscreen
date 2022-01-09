package com.mdiabetesReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class KeywordsFileReader {
    private File file;

    @Autowired
    public KeywordsFileReader(@Value("${keywords.filepath}") String filePath) {
        file = new File(filePath);

    }

    public List<String> getKeywordsTextFIle() throws IOException {
        return Files.readAllLines(file.toPath());
    }
}
