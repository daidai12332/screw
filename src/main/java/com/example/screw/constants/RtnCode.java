package com.example.screw.constants;

public enum RtnCode {
	
	ORDER_NUMBER_NOT_EXISTS(400, "Not exists order numner !!"), //
	ORDER_NUMBER_DUPLICATED(400, "Duplicated order number !!"), //
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
