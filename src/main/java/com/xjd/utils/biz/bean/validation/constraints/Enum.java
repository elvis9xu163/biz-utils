package com.xjd.utils.biz.bean.validation.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.xjd.utils.biz.bean.validation.ConstraintUtils;
import com.xjd.utils.biz.bean.validation.constraints.validators.EnumConstraintValidator;

import static java.lang.annotation.ElementType.*;

/**
 * The Object valud has to be a valid enum of enumClass. the default valid method name can be get/set from
 * {@link ConstraintUtils} or {@link EnumConstraintValidator}, for example:
 * <pre>
 *	ConstraintUtils.setDefaultEnumValidMethodName("valid");
 *	String methodForValid = ConstraintUtils.getDefaultEnumValidMethodName();
 * </pre>
 * or you can set mobile defaultPattern from annotation as below:
 * <pre>
 *	 '@Enum(enumClass=StatusEnum.class, methodForValid="valid")
 * </pre>
 * the valid method signature should like below:
 * <pre>
 *	public void/boolean/Boolean/{enumClass} {methodForValid}({valueClass})
 * </pre>
 * null is considered valid
 *
 * @author elvis.xu
 * @since 2017-08-25 09:30
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumConstraintValidator.class})
public @interface Enum {
	Class<?> enumClass();

	String methodForValid() default "";

	String message() default "com.xjd.utils.biz.bean.validation.constraints.Enum.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
