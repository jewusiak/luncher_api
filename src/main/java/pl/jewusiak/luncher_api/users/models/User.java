package pl.jewusiak.luncher_api.users.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.jewusiak.luncher_api.companies.models.Company;
import pl.jewusiak.luncher_api.lists.models.IndividualList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Email
    private String email;
    private String fullName;
    private String phoneNumber;
    private String password;
    private boolean isEnabled; //not enabled means e.g. email not yet confirmed
    private boolean isLocked; //locked by admin
    @Enumerated(EnumType.STRING)
    private URole role;

    @ManyToOne
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<IndividualList> individualLists;


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    /*
     * Workaround for Spring Security 6.1.0 not supporting role hierarchy (yet)
     * Simple hierarchy SUPERADMIN > ADMIN > RESTAURATEUR > USER
     */
    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>(4);
        switch (getRole()) {
            case ROLE_SUPERADMIN:
                roles.add(URole.ROLE_SUPERADMIN.name());
            case ROLE_ADMIN:
                roles.add(URole.ROLE_ADMIN.name());
            case ROLE_RESTAURATEUR:
                roles.add(URole.ROLE_RESTAURATEUR.name());
        }
        roles.add(URole.ROLE_CLIENT.name());
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
        //return List.of(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user)
            return user.getUsername().equals(this.getUsername());
        return false;
    }

    public Company getCompany() {
        return role == URole.ROLE_RESTAURATEUR ? company : null;
    }

    public void setCompany(Company company) {
        if (role != URole.ROLE_RESTAURATEUR) throw new IllegalStateException("User is not a restaurateur.");
        this.company = company;
    }
}
