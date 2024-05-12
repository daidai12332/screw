package com.example.screw.constants;

public enum RtnCode {


	ORDER_NO_NOT_EXISTS(400, "Not exists order no !!"), //
	ORDER_NO_DUPLICATED(400, "Duplicated order no !!"), //
	VOLTAGE_CANNOT_BE_ZERO(400, "Voltage can not be zero"),//
	MACHINE_NAME_CANNOT_BE_NULL(400, "Machine name can not be null"),
	PASSWORD_ERROR(400, "Password error !!"), //
	ACCOUNT_NOT_EXISTS(400, "Account not exists !!"), //
	ACCOUNT_EXISTS(400, "Account already exists !!"), //
	VOLTAGE_CANNOT_ZERO(400, "Voltage_Cannot_Zero"),//
	ORDER_NUMBER_NOT_EXISTS(400, "Not exists order numner !!"), //
	ORDER_NUMBER_DUPLICATED(400, "Duplicated order number !!"), //
	JSON_ERROR(400, "JSON error!!"), //
	MACHINE_NAME_NOT_FOUND(404, "Machine name not found"),//
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
