package pl.jewusiak.luncher_api.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.luncher_api.users.models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);
}
