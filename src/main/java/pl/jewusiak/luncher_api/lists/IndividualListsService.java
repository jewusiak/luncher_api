package pl.jewusiak.luncher_api.lists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jewusiak.luncher_api.exceptions.NotFoundException;
import pl.jewusiak.luncher_api.exceptions.ResourceAccessException;
import pl.jewusiak.luncher_api.lists.models.IndividualList;
import pl.jewusiak.luncher_api.lists.models.IndividualListMapper;
import pl.jewusiak.luncher_api.lists.models.IndividualListRequest;
import pl.jewusiak.luncher_api.restaurants.RestaurantsService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndividualListsService {
    private final IndividualListsRepository individualListsRepository;
    private final IndividualListMapper individualListMapper;
    private final RestaurantsService restaurantsService;

    public IndividualList getListById(UUID id) {
        return individualListsRepository.findById(id).orElseThrow(() -> new NotFoundException("Individual list by ID"));
    }

    public IndividualList createList(IndividualList individualList) {
        return individualListsRepository.save(individualList);
    }

    public IndividualList addRestaurantToList(UUID listId, UUID restaurantId) {
        var list = getListById(listId);
        list.getRestaurants().add(restaurantsService.getRestaurantById(restaurantId));
        return individualListsRepository.save(list);
    }

    /**
     * If restaurant is not in the list - it is silently ignored.
     */
    public IndividualList removeRestaurantFromList(UUID listId, UUID restaurantId) {
        var list = getListById(listId);
        list.getRestaurants().removeIf(r -> r.getId().equals(restaurantId));
        return individualListsRepository.save(list);
    }

    public void deleteListById(UUID listId) {
        individualListsRepository.delete(getListById(listId));
    }

    public IndividualList modifyList(UUID listId, IndividualListRequest changes) {
        var oldList = getListById(listId);
        individualListMapper.updateEntity(oldList, changes);
        return individualListsRepository.save(oldList);
    }


    public void throwIfUserDoesNotOwnList(UUID listId, UUID userId) {
        if(!getListById(listId).getOwner().getId().equals(userId)) throw new ResourceAccessException();
    }
}
