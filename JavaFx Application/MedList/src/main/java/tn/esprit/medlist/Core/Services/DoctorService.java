package tn.esprit.medlist.Core.Services;

import tn.esprit.medlist.Core.Models.Doctor;
import tn.esprit.medlist.Core.Models.Slot;

import java.util.List;

public interface DoctorService {
    void addDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
    void deleteDoctor(int doctorId);
    Doctor getDoctorById(int doctorId);
    List<Doctor> getAllDoctors();

    List<Slot> getDoctorAvailableSlots(int doctorId);
}
