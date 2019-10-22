package com.example.messageboard.response;

public class ReceiveMessageResponse {
    String message,color,file;
    int id;
    public String getMessage() {
        return message;
    }

    public String getColor() {
        return color;
    }

    public String getFile() {
        return file;
    }

    public int getMsg_id() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setMsg_id(int msg_id) {
        this.id = msg_id;
    }
}
