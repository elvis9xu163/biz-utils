package com.xjd.utils.biz.bean.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author elvis.xu
 * @since 2017-08-28 17:20
 */
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstraintTitle {
	String value() default "";
}
