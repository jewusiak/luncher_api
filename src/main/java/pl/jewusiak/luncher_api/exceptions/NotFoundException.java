package pl.jewusiak.luncher_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String entityDescription) {
        super(HttpStatus.NOT_FOUND, entityDescription);
    }
}
