package com.xjd.utils.biz.bean.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * @author elvis.xu
 * @since 2017-08-18 19:50
 */
public class ValidationProperty {
	protected String propertyName;
	protected String propertyTitle;
	protected Object propertyValue;
	protected String message;
	protected Class<Object> beanClass;
	protected Set<ConstraintViolation<Object>> constraintViolations;

	public ValidationProperty(String propertyName, String propertyTitle, Object propertyValue, String message) {
		this.propertyName = propertyName;
		this.propertyTitle = propertyTitle;
		this.propertyValue = propertyValue;
		this.message = message;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getPropertyTitle() {
		return propertyTitle;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public String getMessage() {
		return message;
	}

	public Class<Object> getBeanClass() {
		return beanClass;
	}

	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return constraintViolations;
	}

	protected void setMessage(String message) {
		this.message = message;
	}

	protected void setBeanClass(Class<Object> beanClass) {
		this.beanClass = beanClass;
	}

	protected void setConstraintViolations(Set<ConstraintViolation<Object>> constraintViolations) {
		this.constraintViolations = constraintViolations;
	}

	@Override
	public String toString() {
		return "ValidationProperty{" +
				"propertyName='" + propertyName + '\'' +
				", propertyTitle='" + propertyTitle + '\'' +
				", propertyValue=" + propertyValue +
				", message='" + message + '\'' +
				", beanClass=" + beanClass +
				", constraintViolations=" + constraintViolations +
				'}';
	}
}
