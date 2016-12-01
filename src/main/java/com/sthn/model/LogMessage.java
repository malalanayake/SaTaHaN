package com.sthn.model;


public class LogMessage {
    private long id;
    private String message;

    public LogMessage() {
    }

    public LogMessage(Long id, String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
