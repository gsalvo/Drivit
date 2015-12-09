package cl.blackbirdhq.drivit.helpers;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONParser {
    public JSONArray makeHttpRequest(String urlGet){
        StringBuilder result = new StringBuilder();
        JSONArray jsonArray = null;
        try{
            URL url = new URL(urlGet);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            jsonArray = new JSONArray(result.toString());
            urlConnection.disconnect();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
