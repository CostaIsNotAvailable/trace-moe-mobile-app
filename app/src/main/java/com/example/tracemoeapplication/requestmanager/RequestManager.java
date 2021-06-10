package com.example.tracemoeapplication.requestmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tracemoeapplication.R;
import com.example.tracemoeapplication.dtos.AnilistRequestDto;
import com.example.tracemoeapplication.dtos.AnimeDto;
import com.example.tracemoeapplication.dtos.DataDto;
import com.example.tracemoeapplication.dtos.MatchListDto;
import com.example.tracemoeapplication.interfaces.RequestManagerListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
            MatchListDto matchList = (new Gson()).fromJson(new String(response.data), MatchListDto.class);
            listener.onPostImageResponse(matchList);
        };

        Response.ErrorListener responseErrorListener = error -> listener.onPostImageResponseError(error);

        MultipartRequest request = new MultipartRequest(context, Request.Method.POST, url, responseListener, responseErrorListener){
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> dataMap = new HashMap<>();
                String imageName = System.currentTimeMillis() + ".png";
                dataMap.put(key, new DataPart(imageName, bitmapToFileData(bitmap), type));
                return dataMap;
            }
        };

        addToRequestQueue(request);
    }

    public void getAnimes(Collection<Integer> ids) {
        String url = "https://trace.moe/anilist";

        Response.Listener<JSONObject> responseListener = response -> {
            DataDto data = (new Gson()).fromJson(response.toString(), DataDto.class);
            List<AnimeDto> animes = new ArrayList<>(data.getPage().getMedia());
            listener.onGetAnimeResponse(animes);
        };

        Response.ErrorListener responseErrorListener = error -> listener.onGetAnimeResponseError(error);

        // Request body
        AnilistRequestDto requestObject = new AnilistRequestDto();
        requestObject.getVariables().setIds(ids);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject((new Gson()).toJson(requestObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonObject == null){
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, responseListener, responseErrorListener);

        addToRequestQueue(request);
    }

    // Convert bitmap into file string
    private byte[] bitmapToFileData(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
