package com.xjd.utils.biz.bean.transfer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author elvis.xu
 * @since 2017-08-22 22:29
 */
public interface BeanTransferrer {
	void transfer(Object source, Object target);

	<T> T transferOne(Object source, Class<T> targetClass);

	default <T> List<T> transferCollection(Collection<Object> sources, Class<T> targetClass) {
		if (sources == null) return null;
		if (sources.isEmpty()) return new ArrayList<>(0);

		return sources.stream().map(source -> transferOne(source, targetClass)).collect(Collectors.toList());
	}

	default <T> T[] transferArray(Object[] sources, Class<T> targetClass) {
		if (sources == null) return null;
		if (sources.length == 0) return (T[]) Array.newInstance(targetClass, 0);

		return (T[]) Arrays.stream(sources).map(source -> transferOne(source, targetClass)).toArray();
	}
}
