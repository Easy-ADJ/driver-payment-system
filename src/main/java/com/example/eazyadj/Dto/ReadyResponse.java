package com.example.eazyadj.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadyResponse {

    private String tid;

    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getNextRedirectPcUrl() {
        return nextRedirectPcUrl;
    }

    public void setNextRedirectPcUrl(String nextRedirectPcUrl) {
        this.nextRedirectPcUrl = nextRedirectPcUrl;
    }
}