package com.epam.esm.exception;

import java.time.LocalDateTime;

/**
 * Class for custom error response
 */
public class CustomErrorResponse {
    private String message;
    private String code;
    private LocalDateTime time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}