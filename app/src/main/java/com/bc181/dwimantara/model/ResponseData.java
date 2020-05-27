package com.bc181.dwimantara.model;

import java.util.List;

public class ResponseData {

    private String value;
    private String message;
    private List<Content> result;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<Content> getResult() {
        return result;
    }
}
