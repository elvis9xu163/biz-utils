package com.xjd.utils.biz.bean.validation.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import com.xjd.utils.biz.bean.validation.ConstraintUtils;
import com.xjd.utils.biz.bean.validation.constraints.validators.MobileConstraintValidator;

import static java.lang.annotation.ElementType.*;

/**
 * The string has to be a well-formed mobile or email address. the default mobile pattern can be get/set from
 * {@link ConstraintUtils} or {@link MobileConstraintValidator}, for example:
 * <pre>
 *	ConstraintUtils.setDefaultMobilePattern("^\\d{11}$");
 *	String defaultMobilePattern = ConstraintUtils.getDefaultMobilePattern();
 * </pre>
 * or you can set mobile defaultPattern from annotation as below:
 * <pre>
 *	 '@EmailOrMobile(defaultMobilePattern="^1\\d{10}$")
 * </pre>
 * null is considered valid
 *
 * @author elvis.xu
 * @since 2017-08-25 09:19
 */
@ConstraintComposition(CompositionType.OR)
@Email
@Mobile
@ReportAsSingleViolation
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface EmailOrMobile {

	@OverridesAttribute(constraint = Mobile.class, name = "mobilePattern") String mobilePattern() default "";

	String message() default "com.xjd.utils.biz.bean.validation.constraints.EmailOrMobile.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
