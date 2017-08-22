package com.xjd.utils.biz.bean.transfer;

import java.util.Objects;


/**
 * @author elvis.xu
 * @since 2017-08-22 16:09
 */
public interface Transferrer<S, T> {
	void transfer(S s, T t);

	default Transferrer<S, T> andThen(Transferrer<S, T> afterTransferrer) {
		Objects.requireNonNull(afterTransferrer, "afterTransferrer is null");
		return (s, t) -> {
			transfer(s, t);
			afterTransferrer.transfer(s, t);
		};
	}
}
