package com.example.screw.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(value = EquipmentId.class)
@Table(name = "equipment")
public class Equipment {

	@Id
	@NotBlank(message = "設備名稱不能為空")
	@Column(name = "name")
	private String name;              // 設備編號 或 電壓
	
	@Id
	@Column(name = "data_date")
	private LocalDate dataDate;       // 資料統計日期
	
	@Column(name = "pass")
	private int pass;       			// 生產數量
	
	@NotNull(message = "電壓不能為空")
	@Column(name = "voltage")
	private int voltage;       			// 設備電壓
	
	@Column(name = "type")
	private String type;       			// 設備類型

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
	
	@Column(name = "del")		//設備是否被刪除
	private boolean del;
	
	@Column(name = "phone")		//維修電話
	private String phone;
	
	@Column(name = "location")		//購買地點
	private String location;
	
	@Column(name = "warranty_date")		//保固日期
	private LocalDate warrantyDate;
	
	@Column(name = "purchase_date")		//購買日期
	private LocalDate purchaseDate;
	
	@Column(name = "record")		//備註
	private String record;
	
	@Column(name = "email")		//電郵
	private String email;
	
	@Column(name = "status")		//使用狀態
	private boolean status;
	
	@Column(name = "price")    //價格
	private int price;     
	
	@Column(name = "lifespan")		//使用年限
	private LocalDate lifespan;
	
	@Column(name = "maintenance_staff")		//維修人員姓名
	private String maintenanceStaff;
	
	@Column(name = "address")		//地址
	private String address;
	
	public Equipment() {
		super();
	}

	public Equipment(@NotBlank(message = "設備名稱不能為空") String name, LocalDate dataDate, int pass,
			@NotNull(message = "電壓不能為空") int voltage, String type, double dataRunAvg, double dataIdleAvg,
			double dataErrorAvg, double dataPassAvg, double dataCurrentAvg, double runIT, double idleIT, double errorIT,
			boolean del, String phone, String location, LocalDate warrantyDate, LocalDate purchaseDate, String record,
			String email, boolean status, int price, LocalDate lifespan, String maintenanceStaff, String address) {
		super();
		this.name = name;
		this.dataDate = dataDate;
		this.pass = pass;
		this.voltage = voltage;
		this.type = type;
		this.dataRunAvg = dataRunAvg;
		this.dataIdleAvg = dataIdleAvg;
		this.dataErrorAvg = dataErrorAvg;
		this.dataPassAvg = dataPassAvg;
		this.dataCurrentAvg = dataCurrentAvg;
		this.runIT = runIT;
		this.idleIT = idleIT;
		this.errorIT = errorIT;
		this.del = del;
		this.phone = phone;
		this.location = location;
		this.warrantyDate = warrantyDate;
		this.purchaseDate = purchaseDate;
		this.record = record;
		this.email = email;
		this.status = status;
		this.price = price;
		this.lifespan = lifespan;
		this.maintenanceStaff = maintenanceStaff;
		this.address = address;
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

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
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

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDate getLifespan() {
		return lifespan;
	}

	public void setLifespan(LocalDate lifespan) {
		this.lifespan = lifespan;
	}

	public String getMaintenanceStaff() {
		return maintenanceStaff;
	}

	public void setMaintenanceStaff(String maintenanceStaff) {
		this.maintenanceStaff = maintenanceStaff;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}