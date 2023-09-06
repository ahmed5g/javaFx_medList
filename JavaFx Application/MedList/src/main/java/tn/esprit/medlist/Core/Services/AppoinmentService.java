package tn.esprit.medlist.Core.Services;

import tn.esprit.medlist.Core.Models.Appointment;
import tn.esprit.medlist.Core.Models.Doctor;
import tn.esprit.medlist.Core.Models.Patient;
import tn.esprit.medlist.Core.Models.Slot;

import java.util.List;

public interface AppoinmentService {




    Appointment scheduleAppoinment(int appointmentId, Patient patient, Doctor doctor, Slot slot);

    void saveAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();



    List<Doctor> getDoctors();
    List<Patient> getPatients();
    void addDoctor();
    void addPatient();
}
