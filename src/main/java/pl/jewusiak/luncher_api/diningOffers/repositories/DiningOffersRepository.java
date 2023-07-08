package pl.jewusiak.luncher_api.diningOffers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOffer;

import java.util.UUID;

public interface DiningOffersRepository extends JpaRepository<DiningOffer, UUID> {
    boolean existsByIdAndRestaurant_Moderator_Id(UUID diningOfferId, UUID userId);
}
