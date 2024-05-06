package com.example.screw.constants;

public enum MaterialCarbonCoefficient {
	
	MaterialName("中文名稱", 0.1);
	
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

	// 功能：根據原料，取得對應的碳係數值
	public static MaterialCarbonCoefficient getCarbonCoefficientByName(String name){
		for(MaterialCarbonCoefficient material : MaterialCarbonCoefficient.values()) {
			if(material.name == name) {
				return material;
			}
		}
		return null;
    }
	
}
