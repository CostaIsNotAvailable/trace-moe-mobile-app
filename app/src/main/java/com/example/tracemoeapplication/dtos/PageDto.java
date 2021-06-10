package com.example.tracemoeapplication.dtos;

import java.util.Collection;

public class PageDto {
    public Collection<AnimeDto> getMedia() {
        return media;
    }

    public void setMedia(Collection<AnimeDto> media) {
        this.media = media;
    }

    private Collection<AnimeDto> media;
}
