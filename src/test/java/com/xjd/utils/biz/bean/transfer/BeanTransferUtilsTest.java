package com.xjd.utils.biz.bean.transfer;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author elvis.xu
 * @since 2017-08-22 19:03
 */
public class BeanTransferUtilsTest {

	PersonModel personModel = null;
	PersonBean personBean = null;

	@Before
	public void setup() {
		personModel = PersonModel.builder()
				.name("xxx")
				.age(20)
				.password("xxxxxxxxx")
				.personExtend(PersonExtendModel.builder()
						.his("his")
						.config("config")
						.build())
				.build();
	}



	@Test
	public void transferArray() throws Exception {
		Object[] personBeans = BeanTransferUtils.transferArray(new PersonModel[]{personModel}, PersonBean.class, PersonBean::new, BeanUtils::copyProperties);
		System.out.println(Arrays.toString(personBeans));
	}

	@Test
	public void transferOne() throws Exception {
		personBean = BeanTransferUtils.<PersonModel, PersonBean>transferOne(personModel, PersonBean::new, DefaultBeanTransferrer.TRANSFERRER_COPY_PROPERTY.andThen(
				(s, t) -> {
					((PersonBean) t).setPersonExtend(BeanTransferUtils.<PersonExtendModel, PersonExtendBean>transferOne(((PersonModel) s).getPersonExtend(), PersonExtendBean::new, DefaultBeanTransferrer.TRANSFERRER_COPY_PROPERTY));
				}
		));
		System.out.println(personBean);
	}

	@Test
	public void transferCollection() throws Exception {
		List list = BeanTransferUtils.transferCollection(Arrays.asList(personModel), PersonBean::new, (s, t) -> {
			t.setPersonExtend(BeanTransferUtils.transferOne(s.getPersonExtend(), PersonExtendBean::new, BeanUtils::copyProperties));
		});
		System.out.println(list.get(0));
	}

	@Test
	public void builder() throws Exception {
		DefaultBeanTransferrer defaultBeanTransferrer = BeanTransferUtils.defaultTransferrer();

		defaultBeanTransferrer.addExtend(PersonModel.class, PersonBean.class, PersonBean::new, (s, t) -> {
			t.setPersonExtend(defaultBeanTransferrer.transferOne(s.getPersonExtend(), PersonExtendBean.class));
		});

		List<PersonBean> list = defaultBeanTransferrer.transferCollection(Arrays.asList(personModel), PersonBean.class);
		System.out.println(list.get(0));
	}

	@Test
	public void builder2() throws Exception {
		DefaultBeanTransferrer defaultBeanTransferrer = BeanTransferUtils.builder()
				.defaultFactory(DefaultBeanTransferrer.FACTORY_INVOKE_NO_ARG_CONSTRUCTOR)
				.defaultTransferrer(DefaultBeanTransferrer.TRANSFERRER_COPY_PROPERTY)
				.extendTransferrer(PersonModel.class, PersonBean.class, (s, t, bt) -> {
					t.setPersonExtend(bt.transferOne(s.getPersonExtend(), PersonExtendBean.class));
				})
				.build();

		List<PersonBean> list = defaultBeanTransferrer.transferCollection(Arrays.asList(personModel), PersonBean.class);
		System.out.println(list.get(0));
	}

}