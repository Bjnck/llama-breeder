//package hrpg.server.common.security;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
//
//@Component
//public class SynchronizedOnSessionPostProcessor implements BeanPostProcessor {
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
//        if (bean instanceof RequestMappingHandlerAdapter) {
//            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
//            adapter.setSynchronizeOnSession(true);
//        }
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//}
