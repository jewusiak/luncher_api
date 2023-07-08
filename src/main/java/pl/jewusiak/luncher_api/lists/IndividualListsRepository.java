package pl.jewusiak.luncher_api.lists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.luncher_api.lists.models.IndividualList;

import java.util.UUID;

@Repository
public interface IndividualListsRepository extends JpaRepository<IndividualList, UUID> {
}
