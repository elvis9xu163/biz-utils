package com.xjd.utils.biz.bean.validation;

import javax.validation.Validator;

/**
 * @author elvis.xu
 * @since 2017-08-18 19:08
 */
public class ValidationUtils {
	protected static Validator defaultValidator;
	protected static Class defaultBeanClass;
	protected static boolean defaultValidAll = false;

	static {
		defaultValidator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
	}

	public static Validator getDefaultValidator() {
		return defaultValidator;
	}

	public static void setDefaultValidator(Validator defaultValidator) {
		ValidationUtils.defaultValidator = defaultValidator;
	}

	public static Class getDefaultBeanClass() {
		return defaultBeanClass;
	}

	public static void setDefaultBeanClass(Class defaultBeanClass) {
		ValidationUtils.defaultBeanClass = defaultBeanClass;
	}

	public static boolean isDefaultValidAll() {
		return defaultValidAll;
	}

	public static void setDefaultValidAll(boolean defaultValidAll) {
		ValidationUtils.defaultValidAll = defaultValidAll;
	}

	public static Validation defaultValidation() {
		return new Validation().validator(defaultValidator).beanClass(defaultBeanClass).validAll(defaultValidAll);
	}

}
