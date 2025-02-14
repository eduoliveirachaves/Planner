package com.edu.planner.utils;

public class Response {

    private String message;
    private Object data;

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
