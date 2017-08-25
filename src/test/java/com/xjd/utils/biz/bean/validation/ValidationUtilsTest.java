package com.xjd.utils.biz.bean.validation;

import java.util.List;

import org.junit.Test;

/**
 * @author elvis.xu
 * @since 2017-08-24 11:08
 */
public class ValidationUtilsTest {
	@Test
	public void defaultValidation() throws Exception {
		List<ValidationProperty> list = ValidationUtils.defaultValidation()
				.beanClass(ValidBean.class)
				.validAll(false)
//				.property("loginName", "+8618521095333")
				.property("loginName", "33@qq.com")
//				.property("loginName", "xxxxx")
//				.property("channel", "WECHAT")
				.property("channel", "QQ")
//				.property("channel", "WEBO")
//				.property("sex", (byte) 1)
				.property("sex", (byte) 0)
//				.property("sex", (byte) -6)
				.property("sex2", (byte) 0)
//				.property("sex2", (byte) -1)
				.property("status1", (byte) 1)
//				.property("status1", (byte) -1)
				.property("status2", "1")
//				.property("status2", "-1")
//				.property("status2", "-2")
				.valid();
		print(list);

	}


	static void print(List<ValidationProperty> list) {
		System.out.println("============");
		for (ValidationProperty validationProperty : list) {
			System.out.println(validationProperty);
		}
	}

}