package com.example.screw.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(value = EquipmentId.class)
@Table(name = "equipment")
public class Equipment {

	@Id
	@Column(name = "name")
	private String name;              // 設備編號 或 電壓
	
	@Id
	@Column(name = "data_date")
	private LocalDate dataDate;       // 資料統計日期

	@Column(name = "data_run_avg")
	private double dataRunAvg;           // 狀態run比例平均
	
	@Column(name = "data_idle_avg")		// 狀態idle比例平均
	private double dataIdleAvg;  
	
	@Column(name = "data_error_avg")	// 狀態error比例平均
	private double dataErrorAvg;  
	
	@Column(name = "data_pass_avg")			// 平均良率
	private double dataPassAvg;  
	
	@Column(name = "data_current_avg")		//平均電流
	private double dataCurrentAvg; 
	
	@Column(name = "run_it")		//狀態run的一天總瓦數
	private double runIT;  
	
	@Column(name = "idle_it")		//狀態idle的一天總瓦數
	private double idleIT;  
	
	@Column(name = "error_it")		//狀態error的一天總瓦數
	private double errorIT;
	
	@Column(name = "delete")		//設備是否被刪除
	private boolean delete;
	
	public Equipment() {
		super();
	}

	public Equipment(String name, LocalDate dataDate, double dataRunAvg, double dataIdleAvg, double dataErrorAvg,
			double dataPassAvg, double dataCurrentAvg, double runIT, double idleIT, double errorIT, boolean delete) {
		super();
		this.name = name;
		this.dataDate = dataDate;
		this.dataRunAvg = dataRunAvg;
		this.dataIdleAvg = dataIdleAvg;
		this.dataErrorAvg = dataErrorAvg;
		this.dataPassAvg = dataPassAvg;
		this.dataCurrentAvg = dataCurrentAvg;
		this.runIT = runIT;
		this.idleIT = idleIT;
		this.errorIT = errorIT;
		this.delete = delete;
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

	public double getDataRunAvg() {
		return dataRunAvg;
	}

	public void setDataRunAvg(double dataRunAvg) {
		this.dataRunAvg = dataRunAvg;
	}

	public double getDataIdleAvg() {
		return dataIdleAvg;
	}

	public void setDataIdleAvg(double dataIdleAvg) {
		this.dataIdleAvg = dataIdleAvg;
	}

	public double getDataErrorAvg() {
		return dataErrorAvg;
	}

	public void setDataErrorAvg(double dataErrorAvg) {
		this.dataErrorAvg = dataErrorAvg;
	}

	public double getDataPassAvg() {
		return dataPassAvg;
	}

	public void setDataPassAvg(double dataPassAvg) {
		this.dataPassAvg = dataPassAvg;
	}

	public double getDataCurrentAvg() {
		return dataCurrentAvg;
	}

	public void setDataCurrentAvg(double dataCurrentAvg) {
		this.dataCurrentAvg = dataCurrentAvg;
	}

	public double getRunIT() {
		return runIT;
	}

	public void setRunIT(double runIT) {
		this.runIT = runIT;
	}

	public double getIdleIT() {
		return idleIT;
	}

	public void setIdleIT(double idleIT) {
		this.idleIT = idleIT;
	}

	public double getErrorIT() {
		return errorIT;
	}

	public void setErrorIT(double errorIT) {
		this.errorIT = errorIT;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
}