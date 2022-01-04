package com.mdiabetesReport.service;

import com.mdiabetesReport.dto.NoteDTO;
import com.mdiabetesReport.exception.ResourceNotFoundException;
import com.mdiabetesReport.helper.HelperClass;
import com.mdiabetesReport.model.Patient;
import com.mdiabetesReport.proxy.MicroservicePatientHistoryProxy;
import com.mdiabetesReport.proxy.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiabetesReportServiceImpl implements DiabetesReportService {
    private MicroservicePatientProxy microservicePatientProxy;
    private MicroservicePatientHistoryProxy microservicePatientHistoryProxy;

    @Autowired
    public DiabetesReportServiceImpl(MicroservicePatientProxy microservicePatientProxy, MicroservicePatientHistoryProxy microservicePatientHistoryProxy) {
        this.microservicePatientProxy = microservicePatientProxy;
        this.microservicePatientHistoryProxy = microservicePatientHistoryProxy;
    }


    @Override
    public List<Patient> getAllPatients() {
        return microservicePatientProxy.getAllPatients();
    }

    @Override
    public List<NoteDTO> getPatientNotes(Integer patId) throws ResourceNotFoundException {
        return microservicePatientHistoryProxy.getPatientHistory(patId);
    }
}
