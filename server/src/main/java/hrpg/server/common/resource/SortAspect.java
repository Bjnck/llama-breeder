package hrpg.server.common.resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Aspect
public class SortAspect {
    @Pointcut("execution(@SortValues * *.*(..))")
    void annotatedMethod() {
    }

    @Around("annotatedMethod() && @annotation(sortValues)")
    public Object adviseAnnotatedMethods(ProceedingJoinPoint pjp, SortValues sortValues) throws Throwable {
        for (int i = 0; i < pjp.getArgs().length; i++) {
            if (pjp.getArgs()[i] instanceof PageRequest) {
                PageRequest page = ((PageRequest) pjp.getArgs()[i]);
                pjp.getArgs()[i] = PageRequest.of(page.getPageNumber(),
                        Math.min(page.getPageSize(), 20), getSort(page.getSort(), sortValues));
                break;
            }
        }

        return pjp.proceed(pjp.getArgs());
    }

    private Sort getSort(Sort sort, SortValues sortValues) {
        List<String> sortLower = Arrays.stream(sortValues.values()).map(String::toLowerCase).collect(Collectors.toList());

        List<Sort.Order> orders = sort.get()
                .filter(order -> sortLower.contains(order.getProperty().toLowerCase()))
                .map(order -> {
                    if (order.isAscending())
                        return Sort.Order.asc(getValue(order.getProperty(), sortValues));
                    else
                        return Sort.Order.desc(getValue(order.getProperty(), sortValues));
                }).collect(Collectors.toList());

        return Sort.by(orders);
    }

    private String getValue(String orderValue, SortValues sortValues) {
        return Arrays.stream(sortValues.values())
                .filter(sortValue -> sortValue.equalsIgnoreCase(orderValue))
                .findAny().orElse(null);
    }
}