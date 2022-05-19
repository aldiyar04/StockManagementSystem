package kz.iitu.itse1910.variant2issenbayev.exception;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ApplicationException extends RuntimeException {
    private final List<String> details;

    public ApplicationException() {
        details = null;
    }

    public ApplicationException(String message) {
        this.details = List.of(message);
    }

    public ApplicationException(List<String> details) {
        this.details = details;
    }
}
