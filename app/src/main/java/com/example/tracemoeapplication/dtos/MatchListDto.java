package com.example.tracemoeapplication.dtos;

import java.util.Collection;

public class MatchListDto {
    private long frameCount;
    private String error;
    private Collection<MatchDto> result;

    public long getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(long frameCount) {
        this.frameCount = frameCount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Collection<MatchDto> getResult() {
        return result;
    }

    public void setResult(Collection<MatchDto> result) {
        this.result = result;
    }
}
