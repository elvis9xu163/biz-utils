package com.xjd.utils.biz.bean.validation.constraints.validators;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.xjd.utils.basic.ObjectUtils;
import com.xjd.utils.basic.StringUtils;
import com.xjd.utils.biz.bean.validation.constraints.Mobile;

/**
 * @author elvis.xu
 * @since 2017-08-24 14:18
 */
public class MobileConstraintValidator implements ConstraintValidator<Mobile, String> {


	protected static String defaultMobilePattern = "^[0\\+]?\\d{10,14}$";
	protected static Pattern defaultPattern = Pattern.compile(defaultMobilePattern, Pattern.CASE_INSENSITIVE);

	protected String customeMobilePattern = null;

	public static String getDefaultMobilePattern() {
		return defaultMobilePattern;
	}

	public static void setDefaultMobilePattern(String defaultMobilePattern) {
		ObjectUtils.requireArgumentNonNull(StringUtils.trimToNull(defaultMobilePattern), "defaultMobilePattern is empty");
		MobileConstraintValidator.defaultMobilePattern = defaultMobilePattern;
		MobileConstraintValidator.defaultPattern = Pattern.compile(defaultMobilePattern, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public void initialize(Mobile constraintAnnotation) {
		customeMobilePattern = StringUtils.trimToNull(constraintAnnotation.mobilePattern());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return true;
		if (customeMobilePattern != null) {
			return Pattern.compile(customeMobilePattern, Pattern.CASE_INSENSITIVE).matcher(value).matches();
		}
		return defaultPattern.matcher(value).matches();
	}

}
