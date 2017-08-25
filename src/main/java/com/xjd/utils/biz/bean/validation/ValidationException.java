package com.xjd.utils.biz.bean.validation;

/**
 * @author elvis.xu
 * @since 2017-08-25 10:48
 */
public class ValidationException extends RuntimeException {
	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
