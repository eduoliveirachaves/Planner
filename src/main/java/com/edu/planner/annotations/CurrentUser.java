package com.edu.planner.annotations;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

/*
    * CurrentUser annotation
    * This annotation is used to get the current user from the request
    * Used in controllers to get the current user
 */

@Target(ElementType.PARAMETER) // Only for method parameters
@Retention(RetentionPolicy.RUNTIME) // Available at runtime
@Documented
@AuthenticationPrincipal // Uses Spring Security's AuthenticationPrincipal
public @interface CurrentUser {
}