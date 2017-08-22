package com.xjd.utils.biz.bean.transfer;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author elvis.xu
 * @since 2017-08-22 17:33
 */
public abstract class BeanTransferUtils {

	public static <S, T> T[] transferArray(S[] cs, Class<T> targetClass, Factory<T> factory, Transferrer<S, T> transferrer) {
		if (cs == null) return null;
		if (cs.length == 0) return (T[]) Array.newInstance(targetClass, 0);

		return Arrays.stream(cs).map(source -> transferOne(source, factory, transferrer)).toArray(i -> (T[]) Array.newInstance(targetClass, i));
	}

	public static <S, T> List<T> transferCollection(Collection<S> cs, Factory<T> factory, Transferrer<S, T> transferrer) {
		if (cs == null) return null;
		if (cs.isEmpty()) return new ArrayList<>(0);

		return cs.stream().map(s -> transferOne(s, factory, transferrer)).collect(Collectors.toList());
	}

	public static <S, T> T transferOne(S s, Factory<T> factory, Transferrer<S, T> transferrer) {
		if (s == null) return null;
		Objects.requireNonNull(factory, "supplier is null");
		T t = factory.get();
		transfer(s, t, transferrer);
		return t;
	}

	public static <S, T> void transfer(S s, T t, Transferrer<S, T> transferrer) {
		if (s == null || t == null) return;
		Objects.requireNonNull(transferrer, "transferrer is null");
		transferrer.transfer(s, t);
	}


	public static BeanTransferrerBuilder builder() {
		return new BeanTransferrerBuilder();
	}

	public static DefaultBeanTransferrer defaultTransferrer() {
		return builder()
				.defaultTransferrer(DefaultBeanTransferrer.TRANSFERRER_COPY_PROPERTY)
				.defaultFactory(DefaultBeanTransferrer.FACTORY_INVOKE_NO_ARG_CONSTRUCTOR)
				.build();
	}
}
