package com.mdiabetesReport.config;

import com.mdiabetesReport.util.KeywordsFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeywordsFileReaderModule {

    @Bean
    public KeywordsFileReader getKeywordsFileReader(@Value("${keywords.filepath}")String filePath) {
        return new KeywordsFileReader(filePath);
    }
}
