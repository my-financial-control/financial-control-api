package pedro.almeida.financialcontrol.web.handlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pedro.almeida.financialcontrol.domain.errors.*;
import pedro.almeida.financialcontrol.web.handlers.dtos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ErrorHandler {

    private MessageSource messageSource;

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handler400(RuntimeException exception) {
        return new ErrorMessageDTO(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public List<ValidationErrorDTO> handler(MethodArgumentNotValidException exception) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ValidationErrorDTO error = new ValidationErrorDTO(e.getField(), message);
            errors.add(error);
        });
        return errors;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public List<ValidationErrorDTO> handleConstraintViolation(ConstraintViolationException exception) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        constraintViolations.forEach(cv -> {
            String field = extractFieldName(cv.getPropertyPath().toString());
            String message = cv.getMessage();
            ValidationErrorDTO error = new ValidationErrorDTO(field, message);
            errors.add(error);
        });
        return errors;
    }

    private String extractFieldName(String propertyPath) {
        int lastIndex = propertyPath.lastIndexOf(".");
        return propertyPath.substring(lastIndex + 1);
    }
}
