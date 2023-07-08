package pl.jewusiak.luncher_api.auth.models;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    String refreshToken;
}
