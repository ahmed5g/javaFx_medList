package tn.esprit.medlist.Core.Services.Implimentation;

import tn.esprit.medlist.Core.Infrastructure.DataBaseConnection;
import tn.esprit.medlist.Core.Models.Appointment;
import tn.esprit.medlist.Core.Models.Doctor;
import tn.esprit.medlist.Core.Models.Patient;
import tn.esprit.medlist.Core.Models.Slot;
import tn.esprit.medlist.Core.Services.AppoinmentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class AppoinmentServiceImpl implements AppoinmentService {



    //DATABASE INJECTION DEPENDENCY
    DataBaseConnection dbConnection = DataBaseConnection.getInstance();
    Connection connection = dbConnection.getConnection();



    /**
     * @param appointmentId
     * @param patient
     * @param doctor
     * @param slot
     * @return
     */
    @Override
    public Appointment scheduleAppoinment(int appointmentId, Patient patient, Doctor doctor, Slot slot) {
        return new Appointment(appointmentId, patient, doctor, slot);
    }

    /**
     * @param appointment
     */
    @Override
    public void saveAppointment(Appointment appointment) {
        try {
            String sql = "INSERT INTO appointment (appointmentId, patientId, doctorId, slotId) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setInt(1, appointment.getAppointmentId());
            preparedStatement.setInt(2, appointment.getPatient().getId());
            preparedStatement.setInt(3, appointment.getDoctor().getId());
            preparedStatement.setInt(4, appointment.getSlot().getSlotId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM appointment";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointmentId");
                int patientId = resultSet.getInt("patientId");
                int doctorId = resultSet.getInt("doctorId");
                int slotId = resultSet.getInt("slotId");
                // You would need to fetch the corresponding Patient, Doctor, and Slot instances
                // based on their IDs here using your PatientService, DoctorService, and SlotService
                //Patient patient = ...;
                //Doctor doctor = ...;
                //Slot slot = ...;
                //Appointment appointment = new Appointment(appointmentId, patient, doctor, slot);
                //appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }







    @Override
    public List<Doctor> getDoctors() {
        return null;
    }

    @Override
    public List<Patient> getPatients() {
        return null;
    }

    @Override
    public void addDoctor() {

    }

    @Override
    public void addPatient() {

    }
}
