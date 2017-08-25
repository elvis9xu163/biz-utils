package com.xjd.utils.biz.bean.validation.constraints.validators;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.ClassUtils;

import com.xjd.utils.basic.ObjectUtils;
import com.xjd.utils.basic.StringUtils;
import com.xjd.utils.biz.bean.validation.ValidationException;
import com.xjd.utils.biz.bean.validation.constraints.Enum;

/**
 * @author elvis.xu
 * @since 2017-08-24 14:18
 */
public class EnumConstraintValidator implements ConstraintValidator<Enum, Object> {

	protected static String defaultValidMethodName = "valueOf";
	protected String customeValidMethodName = null;
	protected Class<?> enumClass;

	public static String getDefaultValidMethodName() {
		return defaultValidMethodName;
	}

	public static void setDefaultValidMethodName(String defaultValidMethodName) {
		ObjectUtils.requireArgumentNonNull(defaultValidMethodName, "defaultValidMethodName is null");
		EnumConstraintValidator.defaultValidMethodName = defaultValidMethodName;
	}

	@Override
	public void initialize(Enum constraintAnnotation) {
		enumClass = constraintAnnotation.enumClass();
		ObjectUtils.requireArgumentNonNull(enumClass, "enumClass is null");
		if (!enumClass.isEnum()) {
			throw new ValidationException("enumClass[" + enumClass + "] is not a enum class");
		}
		customeValidMethodName = StringUtils.trimToNull(constraintAnnotation.methodForValid());
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) return true;

		String methodName = defaultValidMethodName;
		if (customeValidMethodName != null) {
			methodName = customeValidMethodName;
		}

		Method method = null;
		for (Method m : enumClass.getDeclaredMethods()) {
			if (methodName.equals(m.getName()) && Modifier.isPublic(m.getModifiers())
					&& m.getParameterCount() == 1 && ClassUtils.isAssignable(value.getClass(), m.getParameterTypes()[0])
					&& (ClassUtils.isAssignable(m.getReturnType(), void.class) || ClassUtils.isAssignable(m.getReturnType(), boolean.class) || ClassUtils.isAssignable(m.getReturnType(), enumClass))) {
				method = m;
				break;
			}
		}

		if (method == null) {
			throw new ValidationException(String.format("no suitable method found for enum[%1$s] validation, the method signature should as: public void/boolean/Boolean/%1$s %2$s(%3$s)",
					enumClass.getSimpleName(), methodName, value.getClass().getSimpleName()));
		}

		Object response = null;
		try {
			response = method.invoke(enumClass, value);
		} catch (Exception e) {
			return false;
		}

		if (response != null && ((ClassUtils.isAssignable(response.getClass(), boolean.class) && Boolean.TRUE.equals(response)) || ClassUtils.isAssignable(response.getClass(), enumClass))) {
			return true;
		}
		if (response == null && ClassUtils.isAssignable(method.getReturnType(), void.class)) {
			return true;
		}
		return false;
	}


}
