package com.example.screw.entity;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class EquipmentId implements Serializable {

	private String name;              // 設備編號 或 電壓

	private LocalDate dataDate;       // 資料統計日期

	public EquipmentId() {
		super();
	}

	public EquipmentId(String name, LocalDate dataDate) {
		super();
		this.name = name;
		this.dataDate = dataDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDate dataDate) {
		this.dataDate = dataDate;
	}

}
