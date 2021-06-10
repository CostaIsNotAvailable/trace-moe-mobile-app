package com.example.tracemoeapplication.dtos;

import java.util.ArrayList;
import java.util.Collection;

public class VariablesDto {
    private Collection<Integer> ids;

    public VariablesDto(){
        ids = new ArrayList<>();
    }

    public Collection<Integer> getIds() {
        return ids;
    }

    public void setIds(Collection<Integer> ids) {
        this.ids = ids;
    }
}
