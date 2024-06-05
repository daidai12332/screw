package com.example.screw.vo;

import java.util.List;

public class EquipmentWeekRes extends BaseRes{

	private EquipmentWeek equipmentWeek;

	public EquipmentWeekRes() {
		super();
		
	}

	public EquipmentWeekRes(int code, String message) {
		super(code, message);
		
	}

	public EquipmentWeekRes(int code, String message, EquipmentWeek equipmentWeek) {
		super(code, message);
		this.equipmentWeek = equipmentWeek;
	}

	public EquipmentWeek getEquipmentWeek() {
		return equipmentWeek;
	}

	public void setEquipmentWeek(EquipmentWeek equipmentWeek) {
		this.equipmentWeek = equipmentWeek;
	}
	
}
