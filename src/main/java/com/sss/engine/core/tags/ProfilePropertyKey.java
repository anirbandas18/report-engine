package com.sss.engine.core.tags;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface ProfilePropertyKey {
	
	public String name() default "";
	
	public String nameSperator() default "";

}
