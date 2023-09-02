package tn.esprit.medlist.Core.Services.Implimentation;

import tn.esprit.medlist.Core.Infrastructure.DataBaseConnection;
import tn.esprit.medlist.Core.Models.Doctor;
import tn.esprit.medlist.Core.Models.Slot;
import tn.esprit.medlist.Core.Services.DoctorService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDrService implements DoctorService {
    //DATABASE INJECTION DEPENDENCY
    DataBaseConnection dbConnection = DataBaseConnection.getInstance();
    Connection connection = dbConnection.getConnection();



    @Override
    public void addDoctor(Doctor doctor) {
        try {
            String sql = "INSERT INTO Doctor (name, specialty) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setString(2, doctor.getSpecialty());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        try {
            String sql = "UPDATE Doctor SET name=?, specialty=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setString(2, doctor.getSpecialty());
            preparedStatement.setInt(3, doctor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDoctor(int doctorId) {
        try {
            String sql = "DELETE FROM Doctor WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Doctor getDoctorById(int doctorId) {
        try {
            String sql = "SELECT * FROM Doctor WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Doctor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("specialty")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Doctor";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Doctor doctor = new Doctor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("specialty")
                );
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    /**
     * @param doctorId
     * @return
     */
    @Override
    public List<Slot> getDoctorAvailableSlots(int doctorId) {
        List<Slot> availableSlots = new ArrayList<>();
        try {
            String sql = "SELECT s.slotId, s.startTime, s.endTime, s.available " +
                    "FROM Slot s " +
                    "INNER JOIN Appointment a ON s.slotId = a.slotId " +
                    "WHERE a.doctorId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int slotId = resultSet.getInt("slotId");
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");
                boolean isAvailable = resultSet.getBoolean("available");
                if (isAvailable) {
                    Slot slot = new Slot(slotId, startTime, endTime);
                    availableSlots.add(slot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableSlots;
    }
}
