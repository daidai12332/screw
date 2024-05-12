package com.example.screw.vo;

import java.util.List;

public class SearchOrderRes {

	private int code;

	private String message;
	
	private List<ScrewMaterialObj> screwMaterialList;

	public SearchOrderRes() {
		super();
	}

	public SearchOrderRes(int code, String message, List<ScrewMaterialObj> screwMaterialList) {
		super();
		this.code = code;
		this.message = message;
		this.screwMaterialList = screwMaterialList;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ScrewMaterialObj> getScrewMaterialList() {
		return screwMaterialList;
	}

	public void setScrewMaterialList(List<ScrewMaterialObj> screwMaterialList) {
		this.screwMaterialList = screwMaterialList;
	}
	
}
