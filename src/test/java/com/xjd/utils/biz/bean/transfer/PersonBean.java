package com.xjd.utils.biz.bean.transfer;

import lombok.*;

/**
 * @author elvis.xu
 * @since 2017-08-22 19:03
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonBean {
	private String name;
	private Integer age;

	private PersonExtendBean personExtend;

	@Override
	public String toString() {
		return "PersonBean{" +
				"name='" + name + '\'' +
				", age=" + age +
				", personExtend=" + personExtend +
				'}';
	}
}
