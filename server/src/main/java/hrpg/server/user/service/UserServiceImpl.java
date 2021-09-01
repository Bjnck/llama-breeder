package hrpg.server.user.service;

import hrpg.server.user.service.exception.InsufficientCoinsException;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserDetails;
import hrpg.server.user.dao.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ParametersProperties parametersProperties;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           ParametersProperties parametersProperties) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.parametersProperties = parametersProperties;
    }

    @Override
    public UserDto create(@NotNull String registrationKey) {
        User user = User.builder().registrationKey(registrationKey).build();
        user.setDetails(UserDetails.builder()
                .user(user)
                .coins(parametersProperties.getUser().getStartCoins())
                .level(parametersProperties.getUser().getStartLevel())
                .build());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public Optional<UserDto> findByRegistrationKey(String registrationKey) {
        return userRepository.findOneByRegistrationKeys(registrationKey).map(userMapper::toDto);
    }

    @Override
    public String getNameFromId(int id) {
        return userRepository.findById(id).map(User::getName).orElse(null);
    }

    @Override
    public UserDto get() {
        return userMapper.toDto(userRepository.get());
    }

    @Transactional
    @Override
    public UserDto updateName(String name) {
        if (Strings.isBlank(name))
            name = "Breeder";
        User user = userRepository.get();
        user.setName(name);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public void addCoins(int coins) {
        User user = userRepository.get();
        user.getDetails().setCoins(user.getDetails().getCoins() + coins);
    }

    @Transactional
    @Override
    public void removeCoins(int coins) throws InsufficientCoinsException {
        if (coins > 0) {
            User user = userRepository.get();
            if (user.getDetails().getCoins() < coins)
                throw new InsufficientCoinsException();
            user.getDetails().setCoins(user.getDetails().getCoins() - coins);
        }
    }

    @Transactional
    @Override
    public void delete() {
        User user = userRepository.get();

        //todo delete all items
        //todo delete all captures

        //todo if user has no creatures
        // userRepository.delete(user);
        // else remove extra info
        user.setRegistrationKeys(null);
        user.setDetails(null);
    }
}
