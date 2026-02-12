package edu.usf.cs.hogwart_artifact_online.system.exception;

import edu.usf.cs.hogwart_artifact_online.artifact.ArtifactNotFoundException;
import edu.usf.cs.hogwart_artifact_online.system.Result;
import edu.usf.cs.hogwart_artifact_online.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice //  watch control;er
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ArtifactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handlerNotFoundException(ArtifactNotFoundException exception) {
        return new Result(false, StatusCode.NOT_FOUND, exception.getMessage(), null);
    }

    /**
     * This handles invalid inputs (Validation errors)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) // throw this if meet this error
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());

        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage(); // this line grab the message inside not empty
            map.put(key, val);
        });

        return new Result(false, StatusCode.FAILURE, "Provided arguments are invalid", map);// this line display the mesaage inside not empty
    }
}
