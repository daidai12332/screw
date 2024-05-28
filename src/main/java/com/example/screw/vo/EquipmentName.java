package com.example.screw.vo;

import java.time.LocalDate;

public class EquipmentName {

	private String name;              // 設備編號 或 電壓
	
	private int voltage;       			// 設備電壓
	
	private String type;       			// 設備類型

	private String phone;
	
	private String location;
	
	private LocalDate warrantyDate;
	
	private String spec;
	
	private LocalDate purchaseDate;
	
	private String record;

	public EquipmentName() {
		super();
	}

	public EquipmentName(String name, int voltage, String type, String phone, String location, LocalDate warrantyDate,
			String spec, LocalDate purchaseDate, String record) {
		super();
		this.name = name;
		this.voltage = voltage;
		this.type = type;
		this.phone = phone;
		this.location = location;
		this.warrantyDate = warrantyDate;
		this.spec = spec;
		this.purchaseDate = purchaseDate;
		this.record = record;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(LocalDate warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}
	
}
