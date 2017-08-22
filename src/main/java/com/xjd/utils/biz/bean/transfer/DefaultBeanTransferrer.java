package com.xjd.utils.biz.bean.transfer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;

/**
 * @author elvis.xu
 * @since 2017-08-22 17:29
 */
@Slf4j
public class DefaultBeanTransferrer implements ConfigurableBeanTransferrer {

	protected Transferrer defaultTransferrer;
	protected GenericFactory defaultFactory;

	protected List<ExtendTransferrer> extendTransferrers = new ArrayList<>();
	protected List<ExtendFactory> extendFactories = new ArrayList<>();

	public Transferrer getDefaultTransferrer() {
		return defaultTransferrer;
	}

	public void setDefaultTransferrer(Transferrer defaultTransferrer) {
		this.defaultTransferrer = defaultTransferrer;
	}

	public GenericFactory getDefaultFactory() {
		return defaultFactory;
	}

	public void setDefaultFactory(GenericFactory defaultFactory) {
		this.defaultFactory = defaultFactory;
	}

	public List<ExtendTransferrer> getExtendTransferrers() {
		return extendTransferrers;
	}

	public void setExtendTransferrers(List<ExtendTransferrer> extendTransferrers) {
		Objects.requireNonNull(extendTransferrers, "extendTransferrers is null");
		this.extendTransferrers = extendTransferrers;
	}

	public List<ExtendFactory> getExtendFactories() {
		return extendFactories;
	}

	public void setExtendFactories(List<ExtendFactory> extendFactories) {
		Objects.requireNonNull(extendFactories, "extendFactories is null");
		this.extendFactories = extendFactories;
	}

	public <S, T> void addExtendTransferrer(Class<S> sourceClass, Class<T> targetClass, Transferrer<S, T> transferrer) {
		extendTransferrers.add(new ExtendTransferrer<>(sourceClass, targetClass, transferrer));
	}

	public <T> void addExtendFactory(Class<T> targetClass, Factory<T> factory) {
		extendFactories.add(new ExtendFactory<>(targetClass, factory));
	}

	public <S, T> void addExtend(Class<S> sourceClass, Class<T> targetClass, Factory<T> factory, Transferrer<S, T> transferrer) {
		if (transferrer != null) {
			addExtendTransferrer(sourceClass, targetClass, transferrer);
		}
		if (factory != null) {
			addExtendFactory(targetClass, factory);
		}
	}

	public <T> T transferOne(Object source, Class<T> targetClass) {
		if (source == null) return null; // null 不处理

		Objects.requireNonNull(targetClass, "targetClass is null");

		T t = null;
		boolean found = false;
		for (ExtendFactory extendFactory : getExtendFactories()) {
			if (extendFactory.canGet(targetClass)) {
				t = (T) extendFactory.get();
				found = true;
				break;
			}
		}
		if (!found) {
			GenericFactory defaultFactory = getDefaultFactory();
			if (defaultFactory != null) {
				t = defaultFactory.get(targetClass);
				found = true;
			}
		}

		if (!found) {
			log.warn("no factory found for class[" + targetClass + "]");
		}

		transfer(source, t);
		return t;
	}

	public void transfer(Object source, Object target) {
		if (source == null || target == null) return; // null 不处理

		boolean noTransferrer = true;
		Transferrer defaultTransferrer = getDefaultTransferrer();
		if (defaultTransferrer != null) {
			defaultTransferrer.transfer(source, target);
			noTransferrer = false;
		}
		for (ExtendTransferrer extendTransferrer : getExtendTransferrers()) {
			if (extendTransferrer.canTransfer(source, target)) {
				extendTransferrer.transfer(source, target);
				noTransferrer = false;
			}
		}

		if (noTransferrer) {
			log.warn("no transferrer found for source[" + source + "] - target[" + target + "]");
		}
	}


	public static Transferrer TRANSFERRER_COPY_PROPERTY = (s, t) -> {
		if (s == null || t == null) return;
		BeanUtils.copyProperties(s, t);
	};

	public static GenericFactory FACTORY_INVOKE_NO_ARG_CONSTRUCTOR = new GenericFactory() {
		@Override
		public <T> T get(Class<T> clazz) {
			try {
				Constructor<T> constructor = clazz.getConstructor(null);
				if (!constructor.isAccessible()) {
					constructor.setAccessible(true);
				}
				return constructor.newInstance(null);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return null;
		}
	};

	public static class ExtendTransferrer<S, T> implements Transferrer<S, T> {
		protected Class<S> sourceClass;
		protected Class<T> targetClass;
		protected Transferrer<S, T> transferrer;

		public ExtendTransferrer(Class<S> sourceClass, Class<T> targetClass, Transferrer<S, T> transferrer) {
			Objects.requireNonNull(sourceClass, "sourceClass is null");
			Objects.requireNonNull(targetClass, "targetClass is null");
			Objects.requireNonNull(transferrer, "transferrer is null");
			this.sourceClass = sourceClass;
			this.targetClass = targetClass;
			this.transferrer = transferrer;
		}

		public boolean canTransfer(Object source, Object target) {
			if (source == null && target == null) return true;
			if (source == null && target != null && targetClass.isAssignableFrom(target.getClass())) return true;
			if (target == null && source != null && sourceClass.isAssignableFrom(source.getClass())) return true;
			if (source != null && target != null && sourceClass.isAssignableFrom(source.getClass()) && targetClass.isAssignableFrom(target.getClass())) {
				return true;
			}
			return false;
		}

		@Override
		public void transfer(S s, T t) {
			transferrer.transfer(s, t);
		}
	}

	public static class ExtendFactory<T> implements Factory<T> {
		protected Class<T> targetClass;
		protected Factory<T> factory;

		public ExtendFactory(Class<T> targetClass, Factory<T> factory) {
			Objects.requireNonNull(targetClass, "targetClass is null");
			Objects.requireNonNull(factory, "factory is null");
			this.targetClass = targetClass;
			this.factory = factory;
		}

		public boolean canGet(Class<?> clazz) {
			return clazz.isAssignableFrom(targetClass);
		}

		@Override
		public T get() {
			return factory.get();
		}
	}
}
