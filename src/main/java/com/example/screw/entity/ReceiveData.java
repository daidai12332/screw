package com.example.screw.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "receive_data")
public class ReceiveData {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name") 
	private String name;
	
	@Column(name = "status") 
	private String status;
	
	@Column(name = "order_number") 
	private String orderNumber;
	
	@Column(name = "current") 
	private double current;
	
	@Column(name = "pass") 
	private int pass;
	
	@Column(name = "ng") 
	private int ng;
	
	@Column(name = "time") 
	private LocalDateTime time;

	public ReceiveData() {
		super();
	}

	public ReceiveData(String name, String status, String orderNumber, double current, int pass, int ng,
			LocalDateTime time) {
		super();
		this.name = name;
		this.status = status;
		this.orderNumber = orderNumber;
		this.current = current;
		this.pass = pass;
		this.ng = ng;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public int getNg() {
		return ng;
	}

	public void setNg(int ng) {
		this.ng = ng;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
}
