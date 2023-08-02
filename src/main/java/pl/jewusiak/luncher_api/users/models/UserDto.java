package pl.jewusiak.luncher_api.users.models;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private URole role;
}
