package com.example.screw.constants;

public enum RtnCode {
	
	ORDER_NO_NOT_EXISTS(400, "Not exists order no !!"), //
	ORDER_NO_DUPLICATED(400, "Duplicated order no !!"), //
	VOLTAGE_CANNOT_ZERO(400, "Voltage_Cannot_Zero"),//
	JSON_ERROR(400, "JSON error!!"), //
	SUCCESS_AND_SAVE(200, "Success and save!!"), //
	SUCCESS(200, "Success!!"); //

	private int code;

	private String message;

	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
