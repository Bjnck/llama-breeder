package hrpg.server.user.service;

import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserDetails;
import hrpg.server.user.dao.UserRepository;
import hrpg.server.user.service.exception.TooManyCaptureException;
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
                .build());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public Optional<UserDto> findByRegistrationKey(String registrationKey) {
        return userRepository.findOneByRegistrationKeys(registrationKey).map(userMapper::toDto);
    }

    @Override
    public UserDto get() {
        return userMapper.toDto(userRepository.get());
    }

    @Override
    public void flagCapture(boolean flag) throws TooManyCaptureException {
//        User user = getUser();
//        if (flag && user.isCapture())
//            throw new TooManyCaptureException();
//
//        userRepository.save(user.toBuilder().capture(flag).build());
    }

    @Transactional
    @Override
    public UserDto updateName(String name) {
        User user = userRepository.get();
        user.setName(name);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public void delete() {
        User user = userRepository.get();
        //todo if user has no creatures
        // userRepository.delete(user);
        // else remove extra info
        user.setRegistrationKeys(null);
        user.setDetails(null);
    }
}
