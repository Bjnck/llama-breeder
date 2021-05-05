package hrpg.server.user.service;

import java.util.Optional;

public interface UserService {

    UserDto create(String registrationKey);

    Optional<UserDto> findByRegistrationKey(String registrationKey);

    UserDto get();

    UserDto updateName(String name);

    void delete();
}
