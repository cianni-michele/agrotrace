package it.unicam.cs.agrotrace.aspect.security;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.shared.model.user.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class ContentSecurityAspect {


    /**
     * Filters the contents based on the user's role and access rights.
     * If the user is not authenticated, an exception is thrown.
     *
     * @param joinPoint the proceeding join point
     * @param status    the validation status to filter contents by
     * @return a list of contents accessible by the user
     * @throws Throwable if an error occurs during method execution
     */
    @SuppressWarnings("unchecked")
    @Around("execution(* it.unicam.cs.agrotrace.service.ContentService.findAll(..)) && args(status)")
    public Object filterContentsByUserRole(ProceedingJoinPoint joinPoint, ValidationStatus status) throws Throwable {
        List<Content> contents = (List<Content>) joinPoint.proceed();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth) || !auth.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        User user = (User) auth.getPrincipal();

        return user.filterAccessibleContents(contents);
    }
}
