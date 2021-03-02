package io.hamlet.projs.suit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CheckSame {
	public String entityField() default "";

}
