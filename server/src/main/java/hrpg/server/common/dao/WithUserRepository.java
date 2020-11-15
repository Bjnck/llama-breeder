package hrpg.server.common.dao;

import hrpg.server.common.security.OAuthUserUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WithUserRepository<T extends WithUser, ID> extends MongoRepository<T, ID> {

    default Page<T> findAllWithUserId(Example<T> example, Pageable pageable) {
        example.getProbe().setUserId(OAuthUserUtil.getUserId());
        return this.findAll(example, pageable);
    }

    default long countWithUserId(Example<T> example) {
        example.getProbe().setUserId(OAuthUserUtil.getUserId());
        return this.count(example);
    }

    void deleteByUserId(String userId);

    @Override
    default void deleteAll() {
        this.deleteByUserId(OAuthUserUtil.getUserId());
    }
}
