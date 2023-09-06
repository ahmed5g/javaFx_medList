package tn.esprit.medlist.Core.Models;

public class LocationInfo {
    private String displayName;
    private String addressLine;
    private String locality;
    private double latitude;
    private double longitude;

    public LocationInfo(String displayName, String addressLine, String locality, double latitude, double longitude) {
        this.displayName = displayName;
        this.addressLine = addressLine;
        this.locality = locality;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
