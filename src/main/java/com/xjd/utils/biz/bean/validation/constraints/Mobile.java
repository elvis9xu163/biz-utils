package com.xjd.utils.biz.bean.validation.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.xjd.utils.biz.bean.validation.ConstraintUtils;
import com.xjd.utils.biz.bean.validation.constraints.validators.MobileConstraintValidator;

import static java.lang.annotation.ElementType.*;

/**
 * The string has to be a well-formed mobile. the default mobile pattern can be get/set from
 * {@link ConstraintUtils} or {@link MobileConstraintValidator}, for example:
 * <pre>
 *	ConstraintUtils.setDefaultMobilePattern("^\\d{11}$");
 *	String defaultMobilePattern = ConstraintUtils.getDefaultMobilePattern();
 * </pre>
 * or you can set mobile defaultPattern from annotation as below:
 * <pre>
 *	 '@Mobile(defaultMobilePattern="^1\\d{10}$")
 * </pre>
 * null is considered valid
 *
 * @author elvis.xu
 * @since 2017-08-24 14:17
 */

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MobileConstraintValidator.class})
public @interface Mobile {
	String mobilePattern() default "";

	String message() default "com.xjd.utils.biz.bean.validation.constraints.Mobile.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
