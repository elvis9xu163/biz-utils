package com.xjd.utils.biz.bean.validation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * @author elvis.xu
 * @since 2017-08-18 19:48
 */
public class Validation {
	protected Validator validator;
	protected Boolean validAll;
	protected Class beanClass;
	protected List<ValidationProperty> validationProperties = new LinkedList<>();
	protected List<ValidationProperty> invalidProperties = new ArrayList<>();
	protected boolean done = false;

	public Validation validator(Validator validator) {
		assertNotDone();
		this.validator = validator;
		return this;
	}

	public Validation beanClass(Class rootClass) {
		assertNotDone();
		this.beanClass = rootClass;
		return this;
	}

	public Validation validAll(boolean validAll) {
		assertNotDone();
		this.validAll = validAll;
		return this;
	}

	public Validation property(String propertyName, Object propertyValue) {
		return property(propertyName, propertyName, propertyValue, null);
	}

	public Validation property(String propertyName, String propertyTitle, Object propertyValue) {
		return property(propertyName, propertyTitle, propertyValue, null);
	}

	public Validation property(String propertyName, Object propertyValue, String message) {
		return property(propertyName, propertyName, propertyValue, message);
	}

	public Validation property(String propertyName, String propertyTitle, Object propertyValue, String message) {
		assertNotDone();
		ValidationProperty validationProperty = new ValidationProperty(propertyName, propertyTitle, propertyValue, message);
		this.validationProperties.add(validationProperty);
		return this;
	}

	public List<ValidationProperty> valid() {
		if (this.done) {
			return invalidProperties;
		}

		assertNotNull(validator, "validator must be set");
		assertNotNull(beanClass, "beanClass must be set");
		assertNotNull(validAll, "validAll must be set");

		// 作校验
		for (ValidationProperty validationProperty : validationProperties) {
			Set<ConstraintViolation<Object>> constraintViolations = validator.validateValue(beanClass, validationProperty.getPropertyName(), validationProperty.getPropertyValue());

			if (constraintViolations != null && !constraintViolations.isEmpty()) {
				validationProperty.setBeanClass(beanClass);
				validationProperty.setConstraintViolations(constraintViolations);
				if (validationProperty.getMessage() == null) {
					for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
						validationProperty.setMessage(constraintViolation.getMessage());
						break;
					}
				}
				invalidProperties.add(validationProperty);
				if (!validAll) {
					break;
				}
			}
		}

		this.done = true;
		return invalidProperties;
	}

	protected void assertNotDone() {
		if (done) {
			throw new IllegalStateException("validation is done!");
		}
	}

	protected void assertNotNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}
}
