package edu.usf.cs.hogwart_artifact_online.system.exception;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import edu.usf.cs.hogwart_artifact_online.Wizard.WizardNotFoundException;
import edu.usf.cs.hogwart_artifact_online.artifact.ArtifactNotFoundException;
import edu.usf.cs.hogwart_artifact_online.system.Result;
import edu.usf.cs.hogwart_artifact_online.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.access.AccessDeniedException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice //  watch controler, but error at service will also be catch, because service is called by controler, and the error will propagate to controler, and be catch by this class
public class ExceptionHandlerAdvice {
    @ExceptionHandler({ArtifactNotFoundException.class, WizardNotFoundException.class, UserPrincipalNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handlerNotFoundException(Exception exception) {
        /* Exception is the parent of RuntimeException, RE is also parent of NotFoundException,
        exc here is actually instance of NotFoundException,(is-a relationship),
        so it can be casted to NotFoundException, and get the message from it
         */
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

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handlerAuthenticationException(Exception exception) {
        /* Exception is the parent of RuntimeException, RE is also parent of NotFoundException,
        exc here is actually instance of NotFoundException,(is-a relationship),
        so it can be casted to NotFoundException, and get the message from it
         */
        return new Result(false, StatusCode.UNAUTHORIZED, "user or password wrong", null);
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handlerAuthenticationException(AccountStatusException exception) {
        return new Result(false, StatusCode.UNAUTHORIZED, "account is abnormal", null);
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handlerAuthenticationException(InvalidBearerTokenException exception) {
        return new Result(false, StatusCode.UNAUTHORIZED, "Token have problems for some reasons", null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    Result handleAccessDeniedException(AccessDeniedException exception) {
        //Delivered by CustomAcessDenier, when user dont have authorization
        return new Result(false, StatusCode.FORBIDDEN, "NO permission" , null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Result handleAccessDeniedException(Exception exception) {
        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR, "A server intenal error occurs" , null);
    }


}
