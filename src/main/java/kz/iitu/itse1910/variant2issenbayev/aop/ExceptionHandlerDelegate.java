package kz.iitu.itse1910.variant2issenbayev.aop;

import kz.iitu.itse1910.variant2issenbayev.dto.response.ErrorResp;
import kz.iitu.itse1910.variant2issenbayev.exception.ApplicationException;
import kz.iitu.itse1910.variant2issenbayev.exception.InvalidCredentialsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordUndeletableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings({"unchecked","rawtypes"})
public class ExceptionHandlerDelegate {
    ResponseEntity<ErrorResp> handleApplicationExceptions(ApplicationException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String error;
        if (ex instanceof RecordNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            error = "Record Not Found";
        } else if (ex instanceof RecordAlreadyExistsException) {
            error = "Record Already Exists";
        } else if (ex instanceof RecordUndeletableException) {
            error = "Record Not Deletable";
        } else if (ex instanceof InvalidCredentialsException) {
            error = "Invalid Credentials";
        } else {
            throw new IllegalStateException("Exception must not be generic, i.e., " +
                    "must not be of type ApplicationException, only one of its subtypes");
        }
        ErrorResp errorResp = new ErrorResp(error, ex.getDetails());
        return new ResponseEntity(errorResp, httpStatus);
    }

    ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .forEach(err ->
                        details.add(err.getDefaultMessage())
                );
        ErrorResp errorResp = new ErrorResp("Validation Failed", details);
        return new ResponseEntity(errorResp, HttpStatus.BAD_REQUEST);
    }

    ResponseEntity<ErrorResp> handleAccessDenied(AccessDeniedException ex) {
        ErrorResp errorResp = new ErrorResp("Access Denied",
                "You don't have permissions to access this resource");
        return new ResponseEntity<>(errorResp, HttpStatus.FORBIDDEN);
    }

    ResponseEntity<ErrorResp> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        ErrorResp errorResp = new ErrorResp("Server Error", ex.getMessage());
        return new ResponseEntity(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
