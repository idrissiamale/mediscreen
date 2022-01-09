package com.mdiabetesReport.service;

import com.mdiabetesReport.KeywordsFileReader;
import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.helper.HelperClass;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.proxy.MicroservicePatientHistoryProxy;
import com.mdiabetesReport.proxy.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DiabetesReportServiceImpl implements DiabetesReportService {
    private MicroservicePatientProxy microservicePatientProxy;
    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;
    private KeywordsFileReader keywordsFileReader;

    @Autowired
    public DiabetesReportServiceImpl(MicroservicePatientProxy microservicePatientProxy, MicroservicePatientHistoryProxy microservicePatientHistoryProxy, KeywordsFileReader keywordsFileReader) {
        this.microservicePatientProxy = microservicePatientProxy;
        this.microservicePatientHistoryProxy = microservicePatientHistoryProxy;
        this.keywordsFileReader = keywordsFileReader;
    }


    @Override
    public List<Patient> getAllPatients() {
        return microservicePatientProxy.getAllPatients();
    }

    @Override
    public List<NoteDTO> getPatientNotes(Integer patId) throws ResourceNotFoundException {
        return microservicePatientHistoryProxy.getPatientHistory(patId);
    }


    @Override
    public int searchAndCountKeywords(Integer patId) throws IOException {
        LinkedHashSet<String> splitNotes = getWordsList(patId);
        List<String> keywords = keywordsFileReader.getKeywordsTextFIle();
        int count = 0;
        for (String keyword : keywords) {
            for (String note : splitNotes) {
                if (note.equalsIgnoreCase(keyword)) {
                    count++;
                }
            }
        }
        System.out.println(count);
        return count;
    }

    private LinkedHashSet<String> getWordsList(Integer patId) {
        String delimiters = "[, ?.@*:/!]+";
        LinkedHashSet<String> stringList = new LinkedHashSet<>();
        List<NoteDTO> patientNotes = getPatientNotes(patId);
        for (NoteDTO patientNote : patientNotes) {
            List<String> splitNotes = Arrays.asList(patientNote.getNote().split(delimiters));
            stringList.addAll(splitNotes);
        }
        return stringList;
    }
}
