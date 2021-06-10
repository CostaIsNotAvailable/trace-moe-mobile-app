package com.example.tracemoeapplication.interfaces;

import com.android.volley.VolleyError;
import com.example.tracemoeapplication.dtos.AnimeDto;
import com.example.tracemoeapplication.dtos.MatchListDto;

import org.json.JSONObject;

import java.util.Collection;

public interface RequestManagerListener {
    void onPostImageResponse(MatchListDto matchListDto);
    void onPostImageResponseError(Exception exception);
    void onGetAnimeResponse(Collection<AnimeDto> anime);
    void onGetAnimeResponseError(Exception exception);
}
