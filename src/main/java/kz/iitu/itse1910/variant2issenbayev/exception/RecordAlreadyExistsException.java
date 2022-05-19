package kz.iitu.itse1910.variant2issenbayev.exception;

import java.util.List;

public class RecordAlreadyExistsException extends ApplicationException {
    public RecordAlreadyExistsException(String message) {
        super(message);
    }

    public RecordAlreadyExistsException(List<String> messages) {
        super(messages);
    }
}
