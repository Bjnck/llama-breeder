package hrpg.server.common.resource.validation;

public interface ValidationContextService<T, C> {
    C getContext(T toValidate);
}