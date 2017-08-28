package com.xjd.utils.biz.bean.validation.enums;

/**
 * @author elvis.xu
 * @since 2017-04-07 11:40
 */
public enum SexEnum {
	FEMALE((byte) 0, "女"), MALE((byte) 1, "男"), UNKNOWN((byte) 2, "未知");

	private byte code;
	private String desc;

	SexEnum(byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public byte getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static SexEnum valueOfCode(Byte code) {
		if (code != null) {
			for (SexEnum tokenStatusEnum : values()) {
				if (tokenStatusEnum.getCode() == code) {
					return tokenStatusEnum;
				}
			}
		}
		return null;
	}

	public static boolean validCode(Byte code) {
		if (valueOfCode(code) == null) return false;
		return true;
	}

	public static void valid(Byte code) {
		if (!validCode(code)) {
			throw new RuntimeException("校验失败");
		}
	}
}
