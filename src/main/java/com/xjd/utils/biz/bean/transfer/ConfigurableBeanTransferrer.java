package com.xjd.utils.biz.bean.transfer;

/**
 * @author elvis.xu
 * @since 2017-08-22 23:08
 */
public interface ConfigurableBeanTransferrer extends BeanTransferrer {

	void setDefaultFactory(GenericFactory defaultFactory);

	void setDefaultTransferrer(Transferrer<Object, Object> defaultTransferrer);

	<S, T> void addExtendTransferrer(Class<S> sourceClass, Class<T> targetClass, Transferrer<S, T> transferrer);

	<T> void addExtendFactory(Class<T> targetClass, Factory<T> factory);

	<S, T> void addExtend(Class<S> sourceClass, Class<T> targetClass, Factory<T> factory, Transferrer<S, T> transferrer);

}
