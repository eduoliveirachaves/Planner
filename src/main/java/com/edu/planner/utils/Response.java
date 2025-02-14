package com.edu.planner.utils;

public class Response {

    private final String message;

    private final Object data;

    private final boolean sucessful;


    public Response(String message, Object data, boolean sucessful) {
        this.message = message;
        this.data = data;
        this.sucessful = sucessful;
    }


    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
        this.sucessful = true;
    }


    public Response(String message) {
        this.message = message;
        this.data = null;
        this.sucessful = false;
    }


    public String getMessage() {
        return message;
    }


    public Object getData() {
        return data;
    }


    public boolean isSucessful() {
        return sucessful;
    }
}
