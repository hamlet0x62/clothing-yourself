package io.hamlet.projs.suit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Validator {
	
	public boolean notNull() default true;
	public int minLength() default 0;
	public int maxLength() default Integer.MAX_VALUE;
	public int fixedLength() default 0;
	public String equalTo() default "";
	

}
