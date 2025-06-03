package it.unicam.cs.agrotrace.security.context.support;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCuratorUserSecurityContextFactory.class)
public @interface WithCuratorUser {}
