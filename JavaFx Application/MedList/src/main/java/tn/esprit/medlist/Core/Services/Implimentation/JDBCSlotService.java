package tn.esprit.medlist.Core.Services.Implimentation;

import tn.esprit.medlist.Core.Infrastructure.DataBaseConnection;
import tn.esprit.medlist.Core.Models.Slot;
import tn.esprit.medlist.Core.Services.SlotService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JDBCSlotService implements SlotService {




    //DATABASE INJECTION DEPENDENCY
    DataBaseConnection dbConnection = DataBaseConnection.getInstance();
    Connection connection = dbConnection.getConnection();




    @Override
    public Slot createSlot(int slotId, String startTime, String endTime) {
        return new Slot(slotId, startTime, endTime);
    }

    @Override
    public void saveSlot(Slot slot) {
        try {
            String sql = "INSERT INTO slot (slot_id, start_time, end_time, available) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setInt(1, slot.getSlotId());
            preparedStatement.setString(2, slot.getStartTime());
            preparedStatement.setString(3, slot.getEndTime());
            preparedStatement.setBoolean(4, slot.isAvailable());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Slot> getAllSlots() {
        List<Slot> slots = new ArrayList<>();
        try {
            String sql = "SELECT * FROM slot";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int slotId = resultSet.getInt("slotId");
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");
                boolean available = resultSet.getBoolean("available");
                Slot slot = new Slot(slotId, startTime, endTime);
                if (!available) {
                    slot.bookSlot();
                }
                slots.add(slot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }



    @Override
    public Slot findSlotById(int slotId) {
        try {
            String sql = "SELECT * FROM slot WHERE slotId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, slotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");
                boolean available = resultSet.getBoolean("available");
                Slot slot = new Slot(slotId, startTime, endTime);
                if (!available) {
                    slot.bookSlot();
                }
                return slot;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
