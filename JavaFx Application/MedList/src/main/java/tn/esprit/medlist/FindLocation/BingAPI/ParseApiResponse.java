package tn.esprit.medlist.FindLocation.BingAPI;

import com.google.gson.Gson;

public class ParseApiResponse {

    private static final Gson gson = new Gson();

    public static <T> T parseJsonToClass(String jsonString, Class<T> classType) {
        return gson.fromJson(jsonString, classType);
    }
}
