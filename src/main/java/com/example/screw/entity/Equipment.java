package com.example.screw.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "equipment")
public class Equipment {

	@Id
	@Column(name = "name")
	private String name;              // �]�ƽs��
	
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;   // �]�ƲK�ʤ��

	public Equipment() {
		super();
	}

	public Equipment(String name, LocalDate purchaseDate) {
		super();
		this.name = name;
		this.purchaseDate = purchaseDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}