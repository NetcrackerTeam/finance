package com.netcracker.models;

public class Status {
    private boolean status;
    private String message;

    public Status(boolean satus, String message) {
        this.status = satus;
        this.message = message;
    }

    public Status() {
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
