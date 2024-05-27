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
	@NotBlank(message = "�]�ƦW�٤��ର��")
	@Column(name = "name")
	private String name;              // �]�ƽs�� �� �q��
	
	@Id
	@Column(name = "data_date")
	private LocalDate dataDate;       // ��Ʋέp���
	
	@Column(name = "pass")
	private int pass;       			// �Ͳ��ƶq
	
	@Column(name = "voltage")
	private int voltage;       			// �]�ƹq��
	
	@Column(name = "type")
	private String type;       			// �]������

	@NotNull(message = "�q�����ର��")
	@Column(name = "data_run_avg")
	private double dataRunAvg;           // ���Arun��ҥ���
	
	@Column(name = "data_idle_avg")		// ���Aidle��ҥ���
	private double dataIdleAvg;  
	
	@Column(name = "data_error_avg")	// ���Aerror��ҥ���
	private double dataErrorAvg;  
	
	@Column(name = "data_pass_avg")			// �����}�v
	private double dataPassAvg;  
	
	@Column(name = "data_current_avg")		//�����q�y
	private double dataCurrentAvg; 
	
	@Column(name = "run_it")		//���Arun���@���`�˼�
	private double runIT;  
	
	@Column(name = "idle_it")		//���Aidle���@���`�˼�
	private double idleIT;  
	
	@Column(name = "error_it")		//���Aerror���@���`�˼�
	private double errorIT;
	
	@Column(name = "del")		//�]�ƬO�_�Q�R��
	private boolean del;
	
	@Column(name = "phone")		//���׹q��
	private String phone;
	
	@Column(name = "location")		//�ʶR�a�I
	private String location;
	
	@Column(name = "warranty_date")		//�O�T���
	private LocalDate warrantyDate;
	
	@Column(name = "spec")		//�W��
	private String spec;
	
	@Column(name = "purchase_date")		//�ʶR���
	private LocalDate purchaseDate;
	
	@Column(name = "record")		//���׬���
	private String record;
	
	public Equipment() {
		super();
	}

	public Equipment(@NotBlank(message = "�]�ƦW�٤��ର��") String name, LocalDate dataDate, int pass, int voltage,
			String type, @NotNull(message = "�q�����ର��") double dataRunAvg, double dataIdleAvg, double dataErrorAvg,
			double dataPassAvg, double dataCurrentAvg, double runIT, double idleIT, double errorIT, boolean del,
			String phone, String location, LocalDate warrantyDate, String spec, LocalDate purchaseDate, String record) {
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
		this.spec = spec;
		this.purchaseDate = purchaseDate;
		this.record = record;
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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
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
	
}