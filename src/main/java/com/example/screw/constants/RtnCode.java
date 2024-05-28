package com.example.screw.constants;

public enum RtnCode {


	VOLTAGE_CANNOT_BE_ZERO(400, "Voltage can not be zero"),//
	MACHINE_NAME_CANNOT_BE_NULL(400, "Machine name can not be null"),
	PASSWORD_ERROR(400, "�K�X���~"), //
	ACCOUNT_NOT_EXISTS(400, "�b�����s�b"), //
	ACCOUNT_EXISTS(400, "Account already exists !!"), //
	VOLTAGE_CANNOT_ZERO(400, "Voltage_Cannot_Zero"),//
	ORDER_NUMBER_NOT_EXISTS(400, "Not exists order numner !!"), //
	ORDER_NUMBER_DUPLICATED(400, "�渹�w�s�b"), //
	JSON_ERROR(400, "JSON error!!"), //
	MACHINE_NAME_NOT_FOUND(404, "Machine name not found"),//
	TYPE_NOT_FOUND(404, "Type not found"),//
	RECIVEDATA_NOT_FOUND(404, "ReciveData not found"),//
	PLEASE_LOGIN_FIRST(404, "Please login first"), //
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
