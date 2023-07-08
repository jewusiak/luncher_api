package pl.jewusiak.luncher_api.restaurants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;

import java.util.UUID;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurant, UUID> {
    boolean existsByIdAndModerator_Id(UUID restaurantId, UUID moderatorId);
}
