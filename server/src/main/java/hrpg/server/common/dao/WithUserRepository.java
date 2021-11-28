package hrpg.server.common.dao;

import hrpg.server.common.security.OAuthUserUtil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface WithUserRepository<T extends WithUser, ID>
        extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {

    void deleteByIdAndUserId(ID id, int userId);

    @Override
    default void deleteById(ID id) {
        deleteByIdAndUserId(id, OAuthUserUtil.getUserId());
    }
}
