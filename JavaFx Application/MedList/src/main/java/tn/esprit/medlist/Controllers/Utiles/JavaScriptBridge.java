package tn.esprit.medlist.Controllers.Utiles;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Base64;
import java.util.zip.Inflater;


public class JavaScriptBridge {
    public void receiveUpdatedData(String compressedBase64) {
        try {
            // Decode the base64 compressed data
            byte[] compressedData = Base64.getDecoder().decode(compressedBase64);

            // Decompress the data using Inflater
            Inflater inflater = new Inflater();
            inflater.setInput(compressedData);

            byte[] buffer = new byte[1024];
            StringBuilder output = new StringBuilder();

            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                output.append(new String(buffer, 0, count));
            }

            inflater.end();

            // Process the decompressed JSON data
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonData = objectMapper.readTree(output.toString());

            // Now you can work with the jsonData object
            System.out.println("Received data from JavaScript: " + jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}