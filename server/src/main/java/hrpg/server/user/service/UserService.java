package hrpg.server.user.service;

import hrpg.server.user.service.exception.InsufficientCoinsException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserService  {

    Optional<UserDto> findByUid(String uid);

    UserDto create(@NotNull String uid, @NotNull String issuer, String email);

    String getNameFromId(int id);

    UserDto get();

    UserDto updateName(String name);

    void updateLevel(int level);

    void addCoins(int coins);

    void removeCoins(int coins) throws InsufficientCoinsException;

    void addPoints(int points);

    void delete();
}
