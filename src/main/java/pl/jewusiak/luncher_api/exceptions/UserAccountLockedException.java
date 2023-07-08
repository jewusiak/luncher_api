package pl.jewusiak.luncher_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAccountLockedException extends ResponseStatusException {
    public UserAccountLockedException(){
        super(HttpStatus.UNAUTHORIZED, "Your account has been locked by system administrator.");
    }
}
