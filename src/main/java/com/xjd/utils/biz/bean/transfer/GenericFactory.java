package com.xjd.utils.biz.bean.transfer;

import java.util.Objects;

/**
 * @author elvis.xu
 * @since 2017-08-22 18:39
 */
public interface GenericFactory {
	<T> T get(Class<T> clazz);

	default GenericFactory orElse(GenericFactory orElse) {
		Objects.requireNonNull(orElse);

		return new GenericFactory() {
			@Override
			public <T> T get(Class<T> clazz) {
				T t = GenericFactory.this.get(clazz);
				if (t == null) {
					t = orElse.get(clazz);
				}
				return t;
			}
		};
	}
}
