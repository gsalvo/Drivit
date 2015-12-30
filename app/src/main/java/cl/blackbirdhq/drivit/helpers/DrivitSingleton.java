package cl.blackbirdhq.drivit.helpers;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class DrivitSingleton {
    private static DrivitSingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private DrivitSingleton(Context context){
        DrivitSingleton.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized DrivitSingleton getInstance(Context context){
        if(singleton == null){
            singleton = new DrivitSingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void addToRequestQueue(Request req){
        getRequestQueue().add(req);
    }

}
