package pl.jewusiak.luncher_api.diningOffers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jewusiak.luncher_api.diningOffers.models.Plate;

import java.util.UUID;

public interface PlatesRepository extends JpaRepository<Plate, UUID> {
    boolean existsByIdAndDiningOfferElement_DiningOffer_Restaurant_Moderator_Id(UUID plateId, UUID userId);
}
