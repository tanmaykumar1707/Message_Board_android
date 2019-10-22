package com.example.messageboard.response;

public class Id_checkResponse {
    private String message;
    private boolean status;

    public Id_checkResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
