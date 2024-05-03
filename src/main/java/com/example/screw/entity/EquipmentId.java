package com.example.screw.entity;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class EquipmentId implements Serializable {

	private String name;              // �]�ƽs�� �� �q��

	private LocalDate dataDate;       // ��Ʋέp���

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
