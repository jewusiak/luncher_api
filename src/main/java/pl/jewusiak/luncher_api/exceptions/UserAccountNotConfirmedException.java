package pl.jewusiak.luncher_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAccountNotConfirmedException extends ResponseStatusException {
    public UserAccountNotConfirmedException(){
        super(HttpStatus.UNAUTHORIZED, "Account hasn't been confirmed yet.");
    }
}
