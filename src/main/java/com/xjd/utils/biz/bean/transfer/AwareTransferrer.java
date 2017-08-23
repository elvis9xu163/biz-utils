package com.xjd.utils.biz.bean.transfer;

import java.util.Objects;


/**
 * @author elvis.xu
 * @since 2017-08-22 16:09
 */
public interface AwareTransferrer<S, T> extends Transferrer<S, T> {
	void transfer(S s, T t, BeanTransferrer transferrer);

	default void transfer(S s, T t) {
		transfer(s, t, null);
	}

	default AwareTransferrer<S, T> andThen(Transferrer<S, T> afterTransferrer) {
		Objects.requireNonNull(afterTransferrer, "afterTransferrer is null");
		return (s, t, transferrer) -> {
			transfer(s, t, transferrer);
			if (afterTransferrer instanceof AwareTransferrer) {
				((AwareTransferrer) afterTransferrer).transfer(s, t, transferrer);
			} else {
				afterTransferrer.transfer(s, t);
			}
		};
	}
}
