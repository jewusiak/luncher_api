package pl.jewusiak.luncher_api.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jewusiak.luncher_api.configs.jwt.JwtResponse;
import pl.jewusiak.luncher_api.users.models.UserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private JwtResponse tokens;
    private UserDto user;
}
