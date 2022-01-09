package com.mdiabetesReport.config;

import com.mdiabetesReport.KeywordsFileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeywordsFileReaderModule {

    @Bean
    public KeywordsFileReader getKeywordsFileReader(String filePath) {
        return new KeywordsFileReader(filePath);
    }
}
