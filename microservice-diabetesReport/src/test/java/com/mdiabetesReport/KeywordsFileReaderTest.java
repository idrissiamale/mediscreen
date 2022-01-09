package com.mdiabetesReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class KeywordsFileReaderTest {
    private KeywordsFileReader keywordsFileReader;

    @BeforeEach
    public void setUp() throws IOException {
        String filePath = "/Users/idrissi/Workspace/MediscreenProject/microservice-diabetesReport/src/main/resources/keywords.txt";
        keywordsFileReader = new KeywordsFileReader(filePath);
    }

    @Test
    public void getKeywordsFile() throws IOException {
        System.out.println(keywordsFileReader.getKeywordsTextFIle());
        keywordsFileReader.getKeywordsTextFIle();
    }


}
