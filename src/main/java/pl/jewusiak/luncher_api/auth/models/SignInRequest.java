package pl.jewusiak.luncher_api.auth.models;

import lombok.Data;

@Data
public class SignInRequest {
    String email;
    String password;
    Boolean useCookies;
}
