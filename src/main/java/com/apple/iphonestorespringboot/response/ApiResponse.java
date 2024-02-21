package com.apple.iphonestorespringboot.response;

public class ApiResponse {
    private String message;
    private boolean status;

    public ApiResponse() {
    }

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean b) {
        this.status = b;
    }
    
    
}
