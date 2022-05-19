package kz.iitu.itse1910.variant2issenbayev.dto.response;

import lombok.Data;

@Data
public class ErrorResp {
    private final String error;
    private final String message;
}
