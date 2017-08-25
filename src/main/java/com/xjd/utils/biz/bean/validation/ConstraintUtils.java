package com.xjd.utils.biz.bean.validation;

import com.xjd.utils.biz.bean.validation.constraints.validators.EnumConstraintValidator;
import com.xjd.utils.biz.bean.validation.constraints.validators.MobileConstraintValidator;

/**
 * @author elvis.xu
 * @since 2017-08-24 14:37
 */
public abstract class ConstraintUtils {

	public static void setDefaultMobilePattern(String pattern) {
		MobileConstraintValidator.setDefaultMobilePattern(pattern);
	}

	public static String getDefaultMobilePattern() {
		return MobileConstraintValidator.getDefaultMobilePattern();
	}

	public static String getDefaultEnumValidMethodName() {
		return EnumConstraintValidator.getDefaultValidMethodName();
	}

	public static void setDefaultEnumValidMethodName(String defaultValidMethodName) {
		EnumConstraintValidator.setDefaultValidMethodName(defaultValidMethodName);
	}
}
