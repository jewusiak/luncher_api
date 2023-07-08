package pl.jewusiak.luncher_api.utils;

import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class UserRoleDescriptionCustomizer implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        var annotation = handlerMethod.getMethodAnnotation(UserRoleDescription.class);
        if (annotation != null) {
            var securedAnnotation = handlerMethod.getMethodAnnotation(Secured.class);
            String description = operation.getDescription() == null ? "" : (operation.getDescription() + "\n\n");
            if (securedAnnotation != null) {
                operation.setDescription(description + "Required role: **" + String.join("or", securedAnnotation.value()) + "**");
            } else {
                operation.setDescription(description + "Required role: **ROLE_USER**");
            }
        }
        return operation;
    }
}