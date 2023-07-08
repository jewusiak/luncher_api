package pl.jewusiak.luncher_api.diningOffers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOfferElement;

import java.util.UUID;

public interface DiningOfferElementsRepository extends JpaRepository<DiningOfferElement, UUID> {
    boolean existsByIdAndDiningOffer_Restaurant_Moderator_Id(UUID diningOfferElementId, UUID userId);
}
