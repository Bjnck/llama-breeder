package hrpg.server.user.dao;

import hrpg.server.common.security.OAuthUserUtil;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    default User get() {
        return findById(OAuthUserUtil.getUserId()).orElseThrow();
    }

    Optional<User> findOneByRegistrationKeys(String registrationKey);
}
