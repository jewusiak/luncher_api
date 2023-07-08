package pl.jewusiak.luncher_api.diningOffers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOffer;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOfferElement;
import pl.jewusiak.luncher_api.diningOffers.models.Plate;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferElementRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.PlateRequest;
import pl.jewusiak.luncher_api.diningOffers.repositories.DiningOfferElementsRepository;
import pl.jewusiak.luncher_api.diningOffers.repositories.DiningOffersRepository;
import pl.jewusiak.luncher_api.diningOffers.repositories.PlatesRepository;
import pl.jewusiak.luncher_api.exceptions.NotFoundException;
import pl.jewusiak.luncher_api.exceptions.ResourceAccessException;
import pl.jewusiak.luncher_api.users.models.URole;
import pl.jewusiak.luncher_api.users.models.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiningOffersService {
    private final DiningOffersRepository diningOffersRepository;
    private final DiningOfferElementsRepository diningOfferElementsRepository;
    private final PlatesRepository platesRepository;
    private final DiningOffersMapper diningOffersMapper;

    public DiningOffer createNewDiningOffer(DiningOffer diningOffer) {
        return diningOffersRepository.save(diningOffer);
    }

    @Named("getDiningOfferById")
    public DiningOffer getDiningOfferById(UUID id) {
        return diningOffersRepository.findById(id).orElseThrow(() -> new NotFoundException("Dining Offer by ID"));
    }

    @Named("getDiningOfferElementById")
    public DiningOfferElement getDiningOfferElementById(UUID id) {
        return diningOfferElementsRepository.findById(id)
                                            .orElseThrow(() -> new NotFoundException("Dining Offer Element by ID"));
    }

    public DiningOfferElement createNewDiningOfferElement(DiningOfferElement diningOfferElement) {
        return diningOfferElementsRepository.save(diningOfferElement);
    }

    public Plate createNewPlate(Plate plate) {
        return platesRepository.save(plate);
    }

    public void deleteDiningOfferById(UUID id) {
        diningOffersRepository.delete(getDiningOfferById(id));
    }

    public void deleteDiningOfferElementById(UUID id) {
        diningOfferElementsRepository.delete(getDiningOfferElementById(id));
    }

    public void deletePlateById(UUID id) {
        platesRepository.delete(getPlateById(id));
    }

    private Plate getPlateById(UUID id) {
        return platesRepository.findById(id).orElseThrow(() -> new NotFoundException("Plate by ID"));
    }

    public DiningOffer modifyDiningOffer(UUID id, DiningOfferRequest diningOfferRequest) {
        DiningOffer old = getDiningOfferById(id);
        diningOffersMapper.updateEntity(diningOfferRequest, old);
        return diningOffersRepository.save(old);
    }

    public DiningOfferElement modifyDiningOfferElement(UUID id, DiningOfferElementRequest diningOfferElementRequest) {
        DiningOfferElement old = getDiningOfferElementById(id);
        diningOffersMapper.updateEntity(diningOfferElementRequest, old);
        return diningOfferElementsRepository.save(old);
    }

    public Plate modifyPlate(UUID id, PlateRequest plateRequest) {
        Plate old = getPlateById(id);
        diningOffersMapper.updateEntity(plateRequest, old);
        return platesRepository.save(old);
    }


    public void throwIfUserDoesNotOwnDiningOfferAndIsNotAdmin(User user, UUID diningOfferId) {
        if (!(diningOffersRepository.existsByIdAndRestaurant_Moderator_Id(diningOfferId, user.getId()) ||
                user.getAuthorities().contains(new SimpleGrantedAuthority(URole.ROLE_ADMIN.name()))))
            throw new ResourceAccessException();
    }

    public void throwIfUserDoesNotOwnDiningOfferElementAndIsNotAdmin(User user, UUID diningOfferElementId) {
        if (!(diningOfferElementsRepository.existsByIdAndDiningOffer_Restaurant_Moderator_Id(diningOfferElementId, user.getId()) ||
                user.getAuthorities().contains(new SimpleGrantedAuthority(URole.ROLE_ADMIN.name()))))
            throw new ResourceAccessException();
    }

    public void throwIfUserDoesNotOwnPlateAndIsNotAdmin(User user, UUID plateId) {
        if (!(platesRepository.existsByIdAndDiningOfferElement_DiningOffer_Restaurant_Moderator_Id(plateId, user.getId()) ||
                user.getAuthorities().contains(new SimpleGrantedAuthority(URole.ROLE_ADMIN.name()))))
            throw new ResourceAccessException();
    }
}
