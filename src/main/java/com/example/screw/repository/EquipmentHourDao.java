package com.example.screw.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.screw.entity.EquipmentHour;
import com.example.screw.vo.EquipmentHoursDay;

@Repository
@Transactional
public interface EquipmentHourDao extends JpaRepository<EquipmentHour, Integer>{
	
	//儲存每小時產量與耗電量
	@Modifying
	@Query(value = "INSERT INTO screw.equipment_hour (name, pass, power, time, type) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	public void insertEquipmentHourData(String name, int pass, double power, LocalDateTime time, String type);
	
	//取得特定時段不同類型的機台的產量與耗電量資料
	@Query(value = "SELECT new com.example.screw.vo.EquipmentHoursDay(sum(pass) as pass, sum(power) as power, time, type) FROM EquipmentHour where time >= ?1 and time <= ?2 group by time, type")
	public List<EquipmentHoursDay> getEquipmentHourData(LocalDateTime start, LocalDateTime end);
	
	//設定機台類型
	@Modifying
	@Query(value = "UPDATE screw.equipment_hour SET type = ?1 WHERE name = ?2", nativeQuery = true)
	public void setType(String type, String machineName);
	
	
	
	
}
