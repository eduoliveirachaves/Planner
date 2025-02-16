package com.edu.planner.annotations;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

@Target(ElementType.PARAMETER) // Only for method parameters
@Retention(RetentionPolicy.RUNTIME) // Available at runtime
@Documented
@AuthenticationPrincipal // Uses Spring Security's AuthenticationPrincipal
public @interface CurrentUser {
}