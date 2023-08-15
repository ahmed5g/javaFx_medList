package tn.esprit.medlist.FindLocation.BingAPI.MapDisplay;

public class Map {

    public String htmlFilePath;

    public void loadMap(){
        String htmlFilePath = Map.class.getClass().getResource("/API/BingMap/BingMap.html").toExternalForm();

    }
}
