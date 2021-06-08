package com.example.tracemoeapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tracemoeapplication.interfaces.RequestManagerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

    public void postImage(Bitmap bitmap){
        String url = "https://api.trace.moe/search";
        String key = "image";
        String type = "image/gif";

        Response.Listener<NetworkResponse> responseListener = response -> {
            try{
                JSONObject JSONObject = new JSONObject(new String(response.data));
                listener.onPostImageResponse(JSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        Response.ErrorListener responseErrorListener = error -> System.out.println(error.getMessage());

        MultipartRequest request = new MultipartRequest(Request.Method.POST, url, responseListener, responseErrorListener){
            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> dataMap = new HashMap<>();
                String imageName = System.currentTimeMillis() + ".png";
                dataMap.put(key, new DataPart(imageName, bitmapToFileData(bitmap), type));
                return dataMap;
            };
        };

        addToRequestQueue(request);
    }

    // Convert bitmap into file string
    private byte[] bitmapToFileData(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
