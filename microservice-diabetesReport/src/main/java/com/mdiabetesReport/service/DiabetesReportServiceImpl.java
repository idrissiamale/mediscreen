package com.mdiabetesReport.service;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.dto.PatientInfo;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.filter.NoteFilter;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.proxy.MicroservicePatientHistoryProxy;
import com.mdiabetesReport.proxy.MicroservicePatientProxy;
import com.mdiabetesReport.util.KeywordsFileReader;
import com.mdiabetesReport.util.StringSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import static com.mdiabetesReport.helper.HelperClass.*;

@Service
public class DiabetesReportServiceImpl implements DiabetesReportService {
    private boolean manUnderThirty;
    private boolean womanUnderThirty;
    private boolean patientOverThirty;
    private KeywordsFileReader keywordsFileReader;
    private MicroservicePatientProxy microservicePatientProxy;
    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    @Autowired
    public DiabetesReportServiceImpl(KeywordsFileReader keywordsFileReader, MicroservicePatientProxy microservicePatientProxy, MicroservicePatientHistoryProxy microservicePatientHistoryProxy) {
        this.keywordsFileReader = keywordsFileReader;
        this.microservicePatientProxy = microservicePatientProxy;
        this.microservicePatientHistoryProxy = microservicePatientHistoryProxy;
    }

    @Override
    public Patient getPatientById(Integer id) throws ResourceNotFoundException {
        return microservicePatientProxy.getPatientById(id);
    }

    @Override
    public Patient getPatientByFamilyName(String familyName) throws ResourceNotFoundException {
        return microservicePatientProxy.getPatientByFamilyName(familyName);
    }

    @Override
    public PatientInfo getPatientInfo(Patient patient) throws IOException {
        String gender = patient.getSex();
        int age = getAge(patient.getDob());
        int numberOfKeywords = searchAndCountKeywords(patient.getId());
        String diabetesLevel = getDiabetesLevel(gender, age, numberOfKeywords);
        return new PatientInfo(patient.getId(), patient.getFamily(), patient.getGiven(), age, patient.getSex(), diabetesLevel);
    }

    private String getDiabetesLevel(String gender, int age, int numberOfKeywords) {
        String diabetesLevel = "";
        if (isNone(gender, age, numberOfKeywords)) {
            return "None";
        } else if (isBorderline(age, numberOfKeywords)) {
            return "Borderline";
        } else if (isInDanger(gender, age, numberOfKeywords)) {
            return "In danger";
        } else if (isEarlyOnset(gender, age, numberOfKeywords)) {
           return "Early onset";
        }
        return diabetesLevel;
    }

    private boolean isNone(String gender, int age, int numberOfKeywords) {
        manUnderThirty = gender.equals("M") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 0, 3);
        womanUnderThirty = gender.equals("F") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 0, 4);
        patientOverThirty = age > 30 && betweenInclusiveAndExclusive(numberOfKeywords, 0, 2);
        return manUnderThirty || womanUnderThirty || patientOverThirty;
    }

    private boolean isBorderline(int age, int numberOfKeywords) {
        return age > 30 && betweenInclusiveAndExclusive(numberOfKeywords, 2, 6);
    }

    private boolean isInDanger(String gender, int age, int numberOfKeywords) {
        manUnderThirty = gender.equals("M") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 3, 5);
        womanUnderThirty = gender.equals("F") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 4, 7);
        patientOverThirty = age > 30 && betweenInclusiveAndExclusive(numberOfKeywords, 6, 8);
        return manUnderThirty || womanUnderThirty || patientOverThirty;
    }

    private boolean isEarlyOnset(String gender, int age, int numberOfKeywords) {
        manUnderThirty = gender.equals("M") && age < 30 && betweenInclusiveAndInclusive(numberOfKeywords, 5, 11);
        womanUnderThirty = gender.equals("F") && age < 30 && betweenInclusiveAndInclusive(numberOfKeywords, 7, 11);
        patientOverThirty = age > 30 && betweenInclusiveAndInclusive(numberOfKeywords, 8, 11);
        return manUnderThirty || womanUnderThirty || patientOverThirty;
    }

    private int searchAndCountKeywords(Integer id) throws IOException {
        List<String> patientNotes = NoteFilter.getNotesFilteredByPatient(getNotes(id), getAllPatients());
        LinkedHashSet<String> splitNotes = StringSplitter.getStringListOfSplitedNotes(patientNotes);
        List<String> keywords = keywordsFileReader.getKeywordsTextFile();
        int count = 0;
        for (String keyword : keywords) {
            for (String word : splitNotes) {
                if (word.equalsIgnoreCase(keyword)) {
                    count++;
                }
            }
        }
        return count;
    }

    private List<NoteDTO> getNotes(Integer id) throws ResourceNotFoundException {
        return microservicePatientHistoryProxy.getPatientHistory(id);
    }

    private List<Patient> getAllPatients() {
        return microservicePatientProxy.getAllPatients();
    }
}
