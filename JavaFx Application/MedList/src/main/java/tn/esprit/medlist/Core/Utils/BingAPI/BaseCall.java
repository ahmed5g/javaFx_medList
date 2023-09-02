package tn.esprit.medlist.Core.Utils.BingAPI;

import javafx.scene.control.TextField;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public abstract class BaseCall  {
    private static final String BASE_URL = "https://api.example.com"; // Your base URL

    private final HttpClient httpClient = HttpClients.createDefault();

    protected String sendGetRequest(String endpoint, String queryParams) throws IOException {
        String fullUrl = BASE_URL + "/" + endpoint + queryParams;

        HttpGet httpGet = new HttpGet(fullUrl);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } finally {
            httpGet.releaseConnection();
        }

        return null; // Handle error cases
    }


    //GET request type
    protected abstract String getBaseEndpoint();

    // You can add more methods for different types of API calls (POST, PUT, etc.)
}
