package com.example.tracemoeapplication.interfaces;

import com.example.tracemoeapplication.dtos.MatchListDto;

import org.json.JSONObject;

public interface RequestManagerListener {
    public void onPostImageResponse(MatchListDto matchListDto);
}
