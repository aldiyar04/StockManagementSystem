package kz.iitu.itse1910.variant2issenbayev.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResp {
    private final String error;
    private final List<String> messages;

    public ErrorResp(String error, String message) {
        this.error = error;
        this.messages = List.of(message);
    }

    public ErrorResp(String error, List<String> messages) {
        this.error = error;
        this.messages = messages;
    }
}
