package tn.esprit.medlist.FindLocation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


public class JavaScriptBridge {
    public void receiveUpdatedData(Object data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonData = objectMapper.readTree(data.toString());

            // Now you can work with the jsonData object
            // For example, you can access properties using jsonData.get("propertyName")
            System.out.println("Received data from JavaScript: " + jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
