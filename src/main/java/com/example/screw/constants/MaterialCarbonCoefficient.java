package com.example.screw.constants;

public enum MaterialCarbonCoefficient {
	
	BAR_IN_COIL("�����L��", 2.63), //
	STEEL_WIRE_ROD("�u���L��", 2.71), //
	BALLING_BAR_IN_COIL("�y�Ʊ����L��", 2.83), //
	BALLING_STEEL_WIRE_ROD("�y�ƽu���L��", 2.91), //
	HCL("�Q�� (37%)", 1.69), //
	NATURAL_GAS("�ѵM��", 2.63), //
	NAOH("�B��ƶu/�G�P (45%)", 1.54), //
	ZINC_INGOT("�N��", 9.18), //
	REGENERATION_ZINC_INGOT("�A�;N��", 0.04), //
	REGENERATION_ZINC_ALLOY_INGOT("�A�;N�X����", 0.0403), //
	ELECTRIC("�q�O", 0.495);
	
	private String name;

	private double carbonCoefficient;

	private MaterialCarbonCoefficient(String name, double carbonCoefficient) {
		this.name = name;
		this.carbonCoefficient = carbonCoefficient;
	}

	public String getName() {
		return name;
	}

	public double getCarbonCoefficient() {
		return carbonCoefficient;
	}

	// �\��G�ھڭ�ơA���o�������ҫY�ƭ�
	public static MaterialCarbonCoefficient getCarbonCoefficientByName(String name){
		for(MaterialCarbonCoefficient material : MaterialCarbonCoefficient.values()) {
			if(material.name.equals(name)) {
				return material;
			}
		}
		return null;
    }
	
}
