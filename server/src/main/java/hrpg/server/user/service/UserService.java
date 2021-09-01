package hrpg.server.user.service;

import hrpg.server.user.service.exception.InsufficientCoinsException;

import java.util.Optional;

public interface UserService {

    UserDto create(String registrationKey);

    Optional<UserDto> findByRegistrationKey(String registrationKey);

    String getNameFromId(int id);

    UserDto get();

    UserDto updateName(String name);

    void addCoins(int coins);

    void removeCoins(int coins) throws InsufficientCoinsException;

    void delete();
}
