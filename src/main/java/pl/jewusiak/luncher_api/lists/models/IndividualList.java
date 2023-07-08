package pl.jewusiak.luncher_api.lists.models;

import jakarta.persistence.*;
import lombok.Data;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;
import pl.jewusiak.luncher_api.users.models.User;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class IndividualList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String displayName;

    @ManyToOne
    private User owner;


    @ManyToMany
    @JoinTable(name = "restaurants_and_lists", joinColumns = @JoinColumn(name = "list_id"), inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Restaurant> restaurants;

}
