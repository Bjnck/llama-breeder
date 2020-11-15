package hrpg.server.common.dao;

import hrpg.server.common.security.OAuthUserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class MongoAspect {
    @Pointcut("execution(* hrpg.server.common.dao.WithUserRepository.save(..))")
    public void savePointCut() {
    }

    @Around("savePointCut()")
    public Object onSave(ProceedingJoinPoint point) throws Throwable {
        ((WithUser) point.getArgs()[0]).setUserId(OAuthUserUtil.getUserId());
        return point.proceed();
    }

    @Pointcut("execution(* hrpg.server.common.dao.WithUserRepository.findBy*(..))")
    public void findPointCut() {
    }

    @Around("findPointCut()")
    public Object onFind(ProceedingJoinPoint point) throws Throwable {
        Optional<WithUser> proceed = (Optional<WithUser>) point.proceed();
        return proceed.filter(withUser -> withUser.getUserId().equals(OAuthUserUtil.getUserId()));
    }
}