package pl.jewusiak.luncher_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceAccessException extends ResponseStatusException {
    public ResourceAccessException( ){
        super(HttpStatus.FORBIDDEN, "You don't have permission to access this restaurant.");
    }
}
