package pl.jewusiak.luncher_api.users;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import pl.jewusiak.luncher_api.exceptions.NotFoundException;
import pl.jewusiak.luncher_api.exceptions.UserAlreadyExistsException;
import pl.jewusiak.luncher_api.users.models.User;
import pl.jewusiak.luncher_api.users.models.UserMapper;
import pl.jewusiak.luncher_api.users.models.UserRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public User getUserByEmail(String email){
        return usersRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Auth - user by email"));
    }

    @Named("getUserById")
    public User getUserById(UUID id){
        return usersRepository.findById(id).orElseThrow(()-> new NotFoundException("User - by id"));
    }

    public User createNewUser(User user) {
        if(usersRepository.existsUserByEmail(user.getEmail())) throw new UserAlreadyExistsException();
        return usersRepository.save(user);
    }

    public User modifyUserProfile(UUID userId, UserRequest userRequest){
        User user = getUserById(userId);
        userMapper.updateEntity(user, userRequest);
        return usersRepository.save(user);
    }
}
