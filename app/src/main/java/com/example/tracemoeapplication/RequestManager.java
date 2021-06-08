package com.example.tracemoeapplication;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tracemoeapplication.interfaces.RequestManagerListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestManager {
    private static RequestManager instance;
    private RequestQueue requestQueue;
    private static Context context;

    private static RequestManagerListener listener;

    
    private RequestManager(Context _context){
        context = _context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        try {
            listener = (RequestManagerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement HowToGetImageDialogListener");
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void postImage(String file){
        String url = "https://api.trace.moe/search";
        String key = "image";

        Response.Listener<JSONObject> responseListener = response -> listener.onPostImageResponse(response);

        Response.ErrorListener responseErrorListener = error -> System.out.println(error.getMessage());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, responseListener, responseErrorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>() ;
                Map<String,String> params = super.getParams();
                if(params != null){
                    map.putAll(params);
                }
                map.put(key, file);
                return map;
            }
        };

        addToRequestQueue(request);
    }
}
