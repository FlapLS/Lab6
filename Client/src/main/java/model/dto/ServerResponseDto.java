package model.dto;

import java.io.Serializable;

/**
 *
 */
public class ServerResponseDto implements Serializable {
    private String message;
    private String errorMessage;


    public String getMessage() {
        return message;
    }

    public ServerResponseDto message(String message) {
        this.message = message;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ServerResponseDto errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
