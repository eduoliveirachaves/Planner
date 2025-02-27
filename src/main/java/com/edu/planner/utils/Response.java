package com.edu.planner.utils;

import lombok.Getter;

/**
 * Response class.
 * This class is used to create a response.
 * It contains a message, data and a boolean to check if the response was successful.
 * Used by the controllers to return a response to the client.
 */

@Getter
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

}
