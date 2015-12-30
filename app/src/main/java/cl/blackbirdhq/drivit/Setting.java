package cl.blackbirdhq.drivit;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import cl.blackbirdhq.drivit.helpers.DrivitSingleton;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initializeComponents();
    }

    private void initializeComponents() {
        String url = "http://blackbirdhq.cl/selectQuestionClassBSpecial1.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("mensaje", "Respuesta Volley:" + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("mensaje", "Error Respuesta en JSON: " + error.getMessage());
                    }
                }
        );
        DrivitSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

}