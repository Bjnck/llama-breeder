package hrpg.server.user.service;

import hrpg.server.capture.dao.CaptureRepository;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserDetails;
import hrpg.server.user.dao.UserRepository;
import hrpg.server.user.service.exception.InsufficientCoinsException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PenRepository penRepository;
    private final CreatureRepository creatureRepository;
    private final ItemRepository itemRepository;
    private final CaptureRepository captureRepository;
    private final UserMapper userMapper;
    private final ParametersProperties parametersProperties;

    public UserServiceImpl(UserRepository userRepository,
                           PenRepository penRepository,
                           CreatureRepository creatureRepository,
                           ItemRepository itemRepository,
                           CaptureRepository captureRepository,
                           UserMapper userMapper,
                           ParametersProperties parametersProperties) {
        this.userRepository = userRepository;
        this.penRepository = penRepository;
        this.creatureRepository = creatureRepository;
        this.itemRepository = itemRepository;
        this.captureRepository = captureRepository;
        this.userMapper = userMapper;
        this.parametersProperties = parametersProperties;
    }

    @Transactional
    @Override
    public UserDto create(@NotNull String uid, @NotNull String issuer, String email) {
        User user = User.builder().uid(uid).issuer(issuer).email(email).build();
        user.setDetails(UserDetails.builder()
                .user(user)
                .coins(parametersProperties.getUser().getStartCoins())
                .level(parametersProperties.getUser().getStartLevel())
                .build());
        UserDto userDto = userMapper.toDto(userRepository.save(user));

        //todo test pen is created in tests
        Pen pen = Pen.builder().build();
        pen.setUserId(user.getId());
        penRepository.save(pen);

        return userDto;
    }

    @Override
    public Optional<UserDto> findByUid(String uid) {
        return userRepository.findOneByUid(uid).map(userMapper::toDto);
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
    public void updateLevel(int level) {
        User user = userRepository.get();
        if (user.getDetails().getLevel() < level)
            user.getDetails().setLevel(level);
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

        penRepository.deleteAll();
        itemRepository.deleteAll();
        captureRepository.deleteAll();
        creatureRepository.deleteAll();

        //todo if user has no creatures + for every delete creature, check if originalUserId still has creature, otherwise delete it
        // userRepository.delete(user);
        // else remove extra info
        user.setDetails(null);
        user.setDeleted(true);
    }
}
