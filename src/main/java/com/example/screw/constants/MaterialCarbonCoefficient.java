package com.example.screw.constants;

public enum MaterialCarbonCoefficient {
	
	MaterialName("����W��", 0.1);
	
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
			if(material.name == name) {
				return material;
			}
		}
		return null;
    }
	
}
