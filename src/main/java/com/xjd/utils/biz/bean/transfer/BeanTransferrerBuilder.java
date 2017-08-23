package com.xjd.utils.biz.bean.transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author elvis.xu
 * @since 2017-08-22 18:06
 */
public class BeanTransferrerBuilder {

	protected Transferrer defaultTransferrer;
	protected GenericFactory defaultFactory;
	protected List<DefaultBeanTransferrer.ExtendTransferrer> extendTransferrers = new ArrayList<>();
	protected List<DefaultBeanTransferrer.ExtendFactory> extendFactories = new ArrayList<>();

	
	public BeanTransferrerBuilder defaultTransferrer(Transferrer defaultTransferrer) {
		this.defaultTransferrer = defaultTransferrer;
		return this;
	}

	public BeanTransferrerBuilder defaultFactory(GenericFactory defaultFactory) {
		this.defaultFactory = defaultFactory;
		return this;
	}

	public BeanTransferrerBuilder extendTransferrers(List<DefaultBeanTransferrer.ExtendTransferrer> extendTransferrers) {
		this.extendTransferrers.clear();
		if (extendTransferrers != null) {
			this.extendTransferrers.addAll(extendTransferrers);
		}
		return this;
	}

	public <S, T> BeanTransferrerBuilder extendTransferrer(Class<S> sourceClass, Class<T> targetClass, Transferrer<S, T> transferrer) {
		this.extendTransferrers.add(new DefaultBeanTransferrer.ExtendTransferrer<>(sourceClass, targetClass, transferrer));
		return this;
	}

	public <S, T> BeanTransferrerBuilder extendTransferrer(Class<S> sourceClass, Class<T> targetClass, AwareTransferrer<S, T> transferrer) {
		this.extendTransferrers.add(new DefaultBeanTransferrer.ExtendTransferrer<>(sourceClass, targetClass, transferrer));
		return this;
	}

	public BeanTransferrerBuilder extendFactories(List<DefaultBeanTransferrer.ExtendFactory> extendFactories) {
		this.extendFactories.clear();
		if (extendFactories != null) {
			this.extendFactories.addAll(extendFactories);
		}
		return this;
	}

	public <T> BeanTransferrerBuilder extendFactory(Class<T> targetClass, Factory<T> factory) {
		this.extendFactories.add(new DefaultBeanTransferrer.ExtendFactory<>(targetClass, factory));
		return this;
	}

	public <S, T> BeanTransferrerBuilder extend(Class<S> sourceClass, Class<T> targetClass, Factory<T> factory, Transferrer<S, T> transferrer) {
		extendTransferrer(sourceClass, targetClass, transferrer);
		extendFactory(targetClass, factory);
		return this;
	}

	public <S, T> BeanTransferrerBuilder extend(Class<S> sourceClass, Class<T> targetClass, Factory<T> factory, AwareTransferrer<S, T> transferrer) {
		extendTransferrer(sourceClass, targetClass, transferrer);
		extendFactory(targetClass, factory);
		return this;
	}

	public DefaultBeanTransferrer build() {
		DefaultBeanTransferrer defaultBeanTransferrer = new DefaultBeanTransferrer();
		defaultBeanTransferrer.setDefaultTransferrer(defaultTransferrer);
		defaultBeanTransferrer.setDefaultFactory(defaultFactory);
		defaultBeanTransferrer.setExtendTransferrers(extendTransferrers);
		defaultBeanTransferrer.setExtendFactories(extendFactories);
		return defaultBeanTransferrer;
	}
}
