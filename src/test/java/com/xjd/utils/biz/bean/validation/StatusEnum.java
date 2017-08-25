package com.xjd.utils.biz.bean.validation;

/**
 * @author elvis.xu
 * @since 2017-04-07 11:40
 */
public enum StatusEnum {
	NORMAL((byte) 0, "正常"),
	INVALID((byte) 1, "无效");

	private byte code;
	private String desc;

	StatusEnum(byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public byte getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static StatusEnum valueOfCode(Byte code) {
		if (code != null) {
			for (StatusEnum tokenStatusEnum : values()) {
				if (tokenStatusEnum.getCode() == code) {
					return tokenStatusEnum;
				}
			}
		}
		return null;
	}

	public static StatusEnum valueOfCode(String codeStr) {
		Byte code = Byte.valueOf(codeStr);
		if (code != null) {
			for (StatusEnum tokenStatusEnum : values()) {
				if (tokenStatusEnum.getCode() == code) {
					return tokenStatusEnum;
				}
			}
		}
		return null;
	}
}
