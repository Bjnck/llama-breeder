package hrpg.server.user.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findOneByRegistrationKeys(String registrationKey);
}
