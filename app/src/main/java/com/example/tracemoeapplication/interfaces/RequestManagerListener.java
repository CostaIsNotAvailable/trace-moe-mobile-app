package com.example.tracemoeapplication.interfaces;

import com.android.volley.VolleyError;
import com.example.tracemoeapplication.dtos.MatchListDto;

import org.json.JSONObject;

public interface RequestManagerListener {
    void onPostImageResponse(MatchListDto matchListDto);
    void onPostImageResponseError(Exception exception);
}
