package hrpg.server.common.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface WithUserRepository<T extends WithUser, ID>
        extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {
}
