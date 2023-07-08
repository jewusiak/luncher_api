package pl.jewusiak.luncher_api.auth.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequest {

    private String fullName;
    private String phoneNumber;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
