package com.edu.planner.auth;

import com.edu.planner.annotations.CurrentUser;
import com.edu.planner.entity.UserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/*
    * Resolver for the custom @CurrentUser annotation
    * CurrentUserArgumentResolver class that implements HandlerMethodArgumentResolver interface
    * This class is used to resolve the current user from the request
    * Used in controllers to get the current user
 */

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    // Check if the parameter is of type UserEntity and has the @CurrentUser annotation
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserEntity.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    // Resolve the current user from the request
    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        // Get the current user from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated and the principal is an instance of CustomUserDetails
        // If true, return the UserEntity object from the CustomUserDetails object
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUser();
        }

        return null;  // Return null if the user is not authenticated
    }
}
