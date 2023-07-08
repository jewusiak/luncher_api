package pl.jewusiak.luncher_api.lists.models;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;
import pl.jewusiak.luncher_api.users.UsersService;
import pl.jewusiak.luncher_api.users.models.User;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class IndividualListMapper {
    private UsersService usersService;

    public abstract IndividualList mapToEntity(IndividualListRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntity(@MappingTarget IndividualList oldList, IndividualListRequest changes);
    public abstract List<IndividualListDto> mapAllToDto(List<IndividualList> lists);

    public abstract IndividualListDto mapToDto(IndividualList individualList);

    protected UUID map(User user){
        return user.getId();
    }

    protected User map(UUID userId){
        return usersService.getUserById(userId);
    }

    protected UUID map(Restaurant restaurant){
        return restaurant.getId();
    }
}
