package hrpg.server.user.service;

import hrpg.server.user.service.exception.TooManyCaptureException;

import java.util.Optional;

public interface UserService {

    UserDto create(String registrationKey);

    Optional<UserDto> findByRegistrationKey(String registrationKey);

    UserDto get();

    void flagCapture(boolean flag) throws TooManyCaptureException;

    UserDto updateName(String name);

    void delete();
}
