package tn.esprit.medlist.Core.Services;


import tn.esprit.medlist.Core.Models.Patient;

import java.util.List;

public interface PatientService {
    void addPatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatient(int patientId);
    Patient getPatientById(int patientId);
    List<Patient> getAllPatients();
}
