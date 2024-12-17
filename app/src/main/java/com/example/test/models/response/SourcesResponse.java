package com.example.test.models.response;

import com.example.test.models.Source;

import java.util.List;

/**
 * Created by Kwabena Berko on 5/7/2018.
 */

public class SourcesResponse {
    private String status;
    private List<Source> sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
