package com.netcracker.models;

public class Status {
    private boolean status;
    private String message;

    public Status(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status() {
        status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
