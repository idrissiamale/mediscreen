package com.mdiabetesReport.service;

import com.mdiabetesReport.KeywordsFileReader;
import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.proxy.MicroservicePatientHistoryProxy;
import com.mdiabetesReport.proxy.MicroservicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiabetesReportServiceImplTest {
    private DiabetesReportServiceImpl diabetesReportServiceImpl;
    private NoteDTO note1;
    private NoteDTO note2;
    private List<NoteDTO> notes = new ArrayList<>();
    @Mock
    private MicroservicePatientProxy microservicePatientProxy;
    @Mock
    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;
    @Mock
    private KeywordsFileReader keywordsFileReader;

    @BeforeEach
    void setUp() {
        String filePath = "/Users/idrissi/Workspace/MediscreenProject/microservice-diabetesReport/src/main/resources/keywords.txt";
        keywordsFileReader = new KeywordsFileReader(filePath);
        diabetesReportServiceImpl = new DiabetesReportServiceImpl(microservicePatientProxy, microservicePatientHistoryProxy, keywordsFileReader);
        note1 = new NoteDTO(1, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois");
        note2 = new NoteDTO(1, "Tests de laboratoire indiquant une microalbumine élevée");
        notes.add(note1);
        notes.add(note2);
    }

    //@Test
    //void testConvertPatientNotesIntoString() {
        //when(microservicePatientHistoryProxy.getPatientHistory(note1.getPatId())).thenReturn(notes);
        //diabetesReportServiceImpl.getWordsList(note1.getPatId());
    //}

    @Test
    void testSearchAndCountKeywords() throws IOException {
        when(microservicePatientHistoryProxy.getPatientHistory(note1.getPatId())).thenReturn(notes);
        diabetesReportServiceImpl.searchForAndCountKeywords(note1.getPatId());
    }
}
