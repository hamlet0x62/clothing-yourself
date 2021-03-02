package io.hamlet.projs.suit.annotation;


import io.hamlet.projs.suit.entity.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RoleRequire {
	
	public Role value() default Role.USER;
 
}
