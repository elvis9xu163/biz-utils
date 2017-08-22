package com.xjd.utils.biz.bean.transfer;

import java.util.Objects;

/**
 * @author elvis.xu
 * @since 2017-08-22 16:17
 */
public interface Factory<T> {
	T get();

	default Factory<T> orElse(Factory<T> orElse) {
		Objects.requireNonNull(orElse);

		return () -> {
			T t = get();
			if (t == null) {
				t = orElse.get();
			}
			return t;
		};
	}
}
