package kz.iitu.itse1910.variant2issenbayev.aop;


import kz.iitu.itse1910.variant2issenbayev.dto.response.ErrorResp;
import kz.iitu.itse1910.variant2issenbayev.exception.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@AllArgsConstructor
@SuppressWarnings({"unchecked","rawtypes"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    // WHY USE DELEGATE AND NOT WRITE THE EXCEPTION HANDLING LOGIC RIGHT HERE?
    // Because CustomExceptionHandler needs to be advised to log error responses by RequestResponseLoggingAspect.
    // But Spring AOP does not support aspects advising other aspects,
    // so a usual class, the delegate, is advised instead.
    private final ExceptionHandlerDelegate delegate;

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<ErrorResp> handleApplicationExceptions(ApplicationException ex, WebRequest request) {
        return delegate.handleApplicationExceptions(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return delegate.handleMethodArgumentNotValid(ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorResp> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return delegate.handleAccessDenied(ex);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResp> handleAllExceptions(Exception ex, WebRequest request) {
        return delegate.handleAllExceptions(ex);
    }
}
