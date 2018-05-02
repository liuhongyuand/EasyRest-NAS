package tech.dbgsoftware.easyrest.model;

import java.util.Map;

public class ErrorResponse {

    private String errorType;

    private String errorMessage;

    public ErrorResponse(String errorMessage, String errorType) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public ErrorResponse(Map<String, String> errorMap) {
        errorMap.forEach((k, v) -> {
            this.errorMessage = k;
            this.errorType = v;
        });
    }
}
