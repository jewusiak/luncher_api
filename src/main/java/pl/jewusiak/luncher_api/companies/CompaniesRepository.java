package pl.jewusiak.luncher_api.companies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jewusiak.luncher_api.companies.models.Company;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, UUID> {
    boolean existsByTin(String tin);
    Optional<Company> findByTin(String tin);
}
