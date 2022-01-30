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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import static com.mdiabetesReport.helper.HelperClass.*;

@Service
public class DiabetesReportServiceImpl implements DiabetesReportService {
    private static final Logger logger = LogManager.getLogger("DiabetesReportServiceImpl");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient getPatientById(Integer id) throws ResourceNotFoundException {
        logger.info("Patient with the following name " + id + " " + "found successfully.");
        return microservicePatientProxy.getPatientById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient getPatientByFamilyName(String familyName) throws ResourceNotFoundException {
        logger.info("Patient with the following name " + familyName + " " + "found successfully.");
        return microservicePatientProxy.getPatientByFamilyName(familyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientInfo getPatientInfo(Patient patient) throws IOException {
        String gender = patient.getSex();
        int age = getAge(patient.getDob());
        int numberOfKeywords = searchAndCountKeywords(patient.getId());
        String diabetesLevel = getDiabetesLevel(gender, age, numberOfKeywords);
        logger.info("Successfully fetched the data - including his/her diabetes level - for the following patient : " + patient.getFamily() + " " + patient.getGiven());
        return new PatientInfo(patient.getId(), patient.getFamily(), patient.getGiven(), age, patient.getSex(), diabetesLevel);
    }

    /**
     * Retrieves the diabetes level for the Mediscreen's patient.
     *
     * @param gender           - must not be null. It refers to the gender of the patient.
     * @param age              - must not be null. It refers to the age of the patient.
     * @param numberOfKeywords - must not be null. It refers to the number of medical keywords on patient's notes.
     * @return the diabetes level.
     */
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

    /**
     * Checking if the patient's diabetes level is NONE or not.
     *
     * @param gender           - must not be null. It refers to the gender of the patient.
     * @param age              - must not be null. It refers to the age of the patient.
     * @param numberOfKeywords - must not be null. It refers to the number of medical keywords on patient's notes.
     * @return true if the Patient has NONE's risk level.
     */
    private boolean isNone(String gender, int age, int numberOfKeywords) {
        manUnderThirty = gender.equals("M") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 0, 3);
        womanUnderThirty = gender.equals("F") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 0, 4);
        patientOverThirty = age > 30 && betweenInclusiveAndExclusive(numberOfKeywords, 0, 2);
        return manUnderThirty || womanUnderThirty || patientOverThirty;
    }

    /**
     * Checking if the patient's diabetes level is BORDERLINE or not.
     *
     * @param age              - must not be null. It refers to the age of the patient.
     * @param numberOfKeywords - must not be null. It refers to the number of medical keywords on patient's notes.
     * @return true if the Patient has BORDERLINE's risk level.
     */
    private boolean isBorderline(int age, int numberOfKeywords) {
        return age > 30 && betweenInclusiveAndExclusive(numberOfKeywords, 2, 6);
    }

    /**
     * Checking if the patient's diabetes level is IN DANGER or not.
     *
     * @param gender           - must not be null. It refers to the gender of the patient.
     * @param age              - must not be null. It refers to the age of the patient.
     * @param numberOfKeywords - must not be null. It refers to the number of medical keywords on patient's notes.
     * @return true if the Patient has IN DANGER's risk level.
     */
    private boolean isInDanger(String gender, int age, int numberOfKeywords) {
        manUnderThirty = gender.equals("M") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 3, 5);
        womanUnderThirty = gender.equals("F") && age < 30 && betweenInclusiveAndExclusive(numberOfKeywords, 4, 7);
        patientOverThirty = age > 30 && betweenInclusiveAndExclusive(numberOfKeywords, 6, 8);
        return manUnderThirty || womanUnderThirty || patientOverThirty;
    }

    /**
     * Checking if the patient's diabetes level is EARLY ONSET or not.
     *
     * @param gender           - must not be null. It refers to the gender of the patient.
     * @param age              - must not be null. It refers to the age of the patient.
     * @param numberOfKeywords - must not be null. It refers to the number of medical keywords on patient's notes.
     * @return true if the Patient has EARLY ONSET's risk level.
     */
    private boolean isEarlyOnset(String gender, int age, int numberOfKeywords) {
        manUnderThirty = gender.equals("M") && age < 30 && betweenInclusiveAndInclusive(numberOfKeywords, 5, 11);
        womanUnderThirty = gender.equals("F") && age < 30 && betweenInclusiveAndInclusive(numberOfKeywords, 7, 11);
        patientOverThirty = age > 30 && betweenInclusiveAndInclusive(numberOfKeywords, 8, 11);
        return manUnderThirty || womanUnderThirty || patientOverThirty;
    }

    /**
     * Searching and counting the number of the medical keywords on the patient's notes.
     *
     * @param id - must not be null. It refers to the patient's id.
     * @return the number of the keywords.
     * @throws IOException if an I/O error occurs reading from the Keywords file.
     * @see com.mdiabetesReport.util.KeywordsFileReader
     */
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

    /**
     * Retrieves all notes of Mediscreen.
     *
     * @param id - must not be null. It refers to the patient's id.
     * @return all notes of our application.
     * @throws ResourceNotFoundException if id is not found.
     * @see com.mdiabetesReport.proxy.MicroservicePatientHistoryProxy
     */
    private List<NoteDTO> getNotes(Integer id) throws ResourceNotFoundException {
        return microservicePatientHistoryProxy.getPatientHistory(id);
    }

    /**
     * Retrieves all patients of Mediscreen.
     *
     * @return all Patient entities.
     * @see com.mdiabetesReport.proxy.MicroservicePatientProxy
     */
    private List<Patient> getAllPatients() {
        return microservicePatientProxy.getAllPatients();
    }
}
