package com.xjd.utils.biz.bean.transfer;

import lombok.*;

/**
 * @author elvis.xu
 * @since 2017-08-22 19:04
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonExtendBean {
	private String his;
	private String config;

	@Override
	public String toString() {
		return "PersonExtendBean{" +
				"his='" + his + '\'' +
				", config='" + config + '\'' +
				'}';
	}
}
