package hrpg.server.common.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@FilterDef(name = "filterUserId", parameters = {@ParamDef(name = "userId", type = "int")})
@Filter(name = "filterUserId", condition = "user_id = :userId")
public abstract class WithUser {
    @Column(nullable = false)
    private Integer userId;
}
