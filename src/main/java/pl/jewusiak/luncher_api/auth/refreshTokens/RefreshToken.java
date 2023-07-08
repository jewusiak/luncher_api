package pl.jewusiak.luncher_api.auth.refreshTokens;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.jewusiak.luncher_api.users.models.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
