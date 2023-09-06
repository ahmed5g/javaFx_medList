package tn.esprit.medlist.Core.Models;

public class Slot {
    private int slotId;
    private String startTime;
    private String endTime;
    private boolean available;





    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Slot(int slotId, String startTime, String endTime) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public void bookSlot() {
        available = false;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}