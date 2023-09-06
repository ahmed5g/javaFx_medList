package tn.esprit.medlist.Core.Services;

import tn.esprit.medlist.Core.Models.Slot;

import java.util.List;

public interface SlotService {
    Slot createSlot(int slotId, String startTime, String endTime);

    void saveSlot(Slot slot);

    List<Slot> getAllSlots();

    Slot findSlotById(int slotId);
}