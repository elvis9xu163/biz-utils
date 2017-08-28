package com.xjd.utils.biz.bean.validation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.xjd.utils.biz.bean.validation.constraints.EmailOrMobile;
import com.xjd.utils.biz.bean.validation.constraints.Enum;
import com.xjd.utils.biz.bean.validation.enums.ChannelEnum;
import com.xjd.utils.biz.bean.validation.enums.SexEnum;
import com.xjd.utils.biz.bean.validation.enums.StatusEnum;

/**
 * @author elvis.xu
 * @since 2017-04-07 10:18
 */
@Setter
@Getter
public class ValidBean {
	public static final String NOT_NULL = "参数不能为空!";
	public static final String NOT_ENUM = "参数必须为有效的枚举值!";

	@NotBlank(message = NOT_NULL)
	@EmailOrMobile(message = "登录名格式不对，请输入手机号或邮箱！")
	private String loginName;

	@NotNull(message = NOT_NULL)
	@Enum(enumClass = ChannelEnum.class, message = NOT_ENUM)
	private String channel;

	@ConstraintTitle("性别")
	@NotNull(message = NOT_NULL)
	@Enum(enumClass = SexEnum.class, methodForValid = "validCode", message = NOT_ENUM)
	private Byte sex;

	@NotNull(message = NOT_NULL)
	@Enum(enumClass = SexEnum.class, methodForValid = "valid", message = NOT_ENUM)
	private Byte sex2;

	@NotNull(message = NOT_NULL)
	@Enum(enumClass = StatusEnum.class, methodForValid = "valueOfCode", message = NOT_ENUM)
	private Byte status1;

	@NotNull(message = NOT_NULL)
	@Enum(enumClass = StatusEnum.class, methodForValid = "valueOfCode", message = NOT_ENUM)
	private String status2;


}
