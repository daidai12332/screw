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
@Table(name = "equipment")
public class Equipment {

	@Id
	@NotBlank(message = "�]�ƦW�٤��ର��")
	@Column(name = "name")
	private String name;              // �]�ƽs�� �� �q��
	
	@NotNull(message = "�q�����ର��")
	@Column(name = "voltage")
	private int voltage;       			// �]�ƹq��
	
	@Column(name = "type")
	private String type;       			// �]������
	
	@Column(name = "del")		//�]�ƬO�_�Q�R��
	private boolean del;
	
	@Column(name = "phone")		//���׹q��
	private String phone;
	
	@Column(name = "location")		//�ʶR�a�I
	private String location;
	
	@Column(name = "warranty_date")		//�O�T���
	private LocalDate warrantyDate;
	
	@Column(name = "purchase_date")		//�ʶR���
	private LocalDate purchaseDate;
	
	@Column(name = "record")		//�Ƶ�
	private String record;
	
	@Column(name = "email")		//�q�l
	private String email;
	
	@Column(name = "status")		//�ϥΪ��A
	private boolean status;
	
	@Column(name = "price")    //����
	private int price;     
	
	@Column(name = "lifespan")		//�ϥΦ~��
	private LocalDate lifespan;
	
	@Column(name = "maintenance_staff")		//���פH���m�W
	private String maintenanceStaff;
	
	@Column(name = "address")		//�a�}
	private String address;
	
	public Equipment() {
		super();
	}

	public Equipment(@NotBlank(message = "�]�ƦW�٤��ର��") String name, @NotNull(message = "�q�����ର��") int voltage, String type,
			boolean del, String phone, String location, LocalDate warrantyDate, LocalDate purchaseDate, String record,
			String email, boolean status, int price, LocalDate lifespan, String maintenanceStaff, String address) {
		super();
		this.name = name;
		this.voltage = voltage;
		this.type = type;
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