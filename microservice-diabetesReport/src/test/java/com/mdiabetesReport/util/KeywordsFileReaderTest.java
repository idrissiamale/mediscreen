package com.mdiabetesReport.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeywordsFileReaderTest {
    private KeywordsFileReader keywordsFileReader;

    @BeforeEach
    public void setUp() {
        String filePath = "/Users/idrissi/Workspace/MediscreenProject/microservice-diabetesReport/src/main/resources/keywords.txt";
        keywordsFileReader = new KeywordsFileReader(filePath);
    }

    @Test
    @DisplayName("Checking that Keywords file is correctly fetched by testing that the list has the expected size")
    public void shouldReturnAllKeywordsOfTheFile() throws IOException {
        List<String> keywords = keywordsFileReader.getKeywordsTextFile();
        assertEquals(11, keywords.size());
    }

    @Test
    @DisplayName("Checking that Keywords file is correctly fetched by testing that the list contains one of the keywords of the file")
    public void shouldContainTheKeywordOfTheFile() throws IOException {
        List<String> keywords = keywordsFileReader.getKeywordsTextFile();
        assertTrue(keywords.contains("Cholestérol"));
    }

}
