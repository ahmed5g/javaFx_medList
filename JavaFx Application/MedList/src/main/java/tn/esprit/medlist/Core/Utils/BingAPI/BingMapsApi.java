package tn.esprit.medlist.Core.Utils.BingAPI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;


public class BingMapsApi {

    public static final String BING_MAPS_API_KEY = "QWtqSt1MTNn09yxueZhC~WnvEIvYmw52FUOKsAw0W8w~Aizg11YzcbBgHWAT4MIdVjFCbxEYmjY6qLziT7-oy5IbOV2E_Bliv0Lt1c0YZkk2" ;

    //Works only in US and canada region
    public static String callApi(String apiUrl) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);

        HttpResponse response = httpClient.execute(request);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }


    public static String CallGeoCodingApi(String UserLocation) throws Exception{
        String encodedLocation = URLEncoder.encode(UserLocation, "UTF-8");
        String geocodingUrl = "http://dev.virtualearth.net/REST/v1/Locations?q=" + encodedLocation + "&key=" + BING_MAPS_API_KEY;

        String response;
        return response = callApi(geocodingUrl);
    }


    public static String CallLocalSearchApi(Double latitude,Double longitude) throws Exception{
        String localSearchUrl = "http://dev.virtualearth.net/REST/v1/LocalSearch?q=Doctor&userCircularMapView=" +
                latitude + "," + longitude + ",5000&key=" + BingMapsApi.BING_MAPS_API_KEY;
        String response;
        return response = callApi(localSearchUrl);
    }




}



