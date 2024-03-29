package hrpg.server.common.dao;

import hrpg.server.common.security.OAuthUserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
@Aspect
public class WithUserAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(* hrpg.server.common.dao.WithUserRepository+.save(..))")
    public void savePointCut() {
    }

    @Around("savePointCut()")
    public Object onSave(ProceedingJoinPoint point) throws Throwable {
        WithUser withUser = (WithUser) point.getArgs()[0];
        if (withUser.getUserId() == null && OAuthUserUtil.getUserId() != null)
            withUser.setUserId(OAuthUserUtil.getUserId());
        return point.proceed();
    }

    @Pointcut("execution(* hrpg.server.common.dao.WithUserRepository+.findAll*(..))")
    public void findAllPointCut() {
    }

    @Pointcut("execution(* hrpg.server.common.dao.WithUserRepository+.count*(..))")
    public void countPointCut() {
    }

    @Pointcut("execution(* hrpg.server.common.dao.WithUserRepository+.deleteAll*(..))")
    public void deleteAllPointCut() {
    }

    @Around("findAllPointCut() || countPointCut() || deleteAllPointCut()")
    public Object onSearch(ProceedingJoinPoint point) throws Throwable {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("filterUserId").setParameter("userId", OAuthUserUtil.getUserId());
        Object obj = point.proceed();
        session.disableFilter("filterUserId");
        return obj;
    }


    @Pointcut("execution(* hrpg.server.common.dao.WithUserRepository+.findBy*(..))")
    public void findByPointCut() {
    }

    @Around("findByPointCut()")
    public Object onGet(ProceedingJoinPoint point) throws Throwable {
        Optional<WithUser> withUser = (Optional<WithUser>) point.proceed();
        if (withUser.isPresent() && !withUser.get().getUserId().equals(OAuthUserUtil.getUserId()))
            return Optional.empty();
        return withUser;
    }
}
