package hrpg.server.user.service;

import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.security.OAuthUserUtil;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserRepository;
import hrpg.server.user.service.exception.TooManyCaptureException;
import org.springframework.stereotype.Service;

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
    public UserDto create(String registrationKey) {
        return userMapper.toDto(userRepository.save(User.builder()
                .registrationKey(registrationKey)
                .coins(parametersProperties.getUser().getStartCoins())
                .build()));
    }

    @Override
    public Optional<UserDto> findByRegistrationKey(String registrationKey) {
        return userRepository.findOneByRegistrationKeys(registrationKey).map(userMapper::toDto);
    }

    @Override
    public UserDto get() {
        return userMapper.toDto(getUser());
    }

    @Override
    public void flagCapture(boolean flag) throws TooManyCaptureException {
        User user = getUser();
        if (flag && user.isCapture())
            throw new TooManyCaptureException();

        userRepository.save(user.toBuilder().capture(flag).build());
    }

    private User getUser() {
        return userRepository.findById(OAuthUserUtil.getUserId()).orElseThrow();
    }
}
