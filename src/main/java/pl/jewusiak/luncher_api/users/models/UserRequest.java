package pl.jewusiak.luncher_api.users.models;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String fullName;
    private String phoneNumber;
    private String password;
}
