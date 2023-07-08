package pl.jewusiak.luncher_api.users.models;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jewusiak.luncher_api.auth.models.RegistrationRequest;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "isEnabled", expression = "java(false)")
    @Mapping(target = "isLocked", expression = "java(false)")
    @Mapping(target = "role", expression = "java(URole.ROLE_CLIENT)")
    @Mapping(target = "password",expression = "java(passwordEncoder.encode(request.getPassword()))")
    public abstract User mapRegistrationRequestToUser(RegistrationRequest request);

    public abstract UserDto mapUserToDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntity(@MappingTarget User oldUser, UserRequest changes);
}
