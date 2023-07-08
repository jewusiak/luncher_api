package pl.jewusiak.luncher_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jewusiak.luncher_api.companies.CompaniesService;
import pl.jewusiak.luncher_api.users.UsersService;
import pl.jewusiak.luncher_api.users.models.URole;
import pl.jewusiak.luncher_api.users.models.User;

@SpringBootTest
public class CreateData {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompaniesService companiesService;

    @Test
    public void createData(){
//        var user = User.builder().password(passwordEncoder.encode("2277")).userProfile(new UserProfile("Grzegorz user","1234")).isEnabled(true).isLocked(false).email("user@jewusiak.pl").role(URole.ROLE_CLIENT).build();
//        var rest = User.builder().password(passwordEncoder.encode("2277")).userProfile(new UserProfile("Grzegorz rest","5678")).isEnabled(true).isLocked(false).email("rest@jewusiak.pl").role(URole.ROLE_RESTAURATEUR).build();
//        var admin = User.builder().password(passwordEncoder.encode("2277")).userProfile(new UserProfile("Grzegorz admin","9010")).isEnabled(true).isLocked(false).email("admin@jewusiak.pl").role(URole.ROLE_ADMIN).build();
//        var root = User.builder().password(passwordEncoder.encode("2277")).userProfile(new UserProfile("Grzegorz root","1112")).isEnabled(true).isLocked(false).email("root@jewusiak.pl").role(URole.ROLE_SUPERADMIN).build();
//        usersService.createNewUser(user);
//        usersService.createNewUser(rest);
//        usersService.createNewUser(admin);
//        usersService.createNewUser(root);

    }


}
