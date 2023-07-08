package pl.jewusiak.luncher_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {
    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "This email already exists!");
    }
}
