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
import com.example.screw.vo.EquipmentWeek;

@Repository
@Transactional
public interface EquipmentHourDao extends JpaRepository<EquipmentHour, Integer>{
	
	//�x�s�C�p�ɲ��q�P�ӹq�q
	@Modifying
	@Query(value = "INSERT INTO screw.equipment_hour (name, pass, power, time, type, run_avg, pass_avg) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
	public void insertEquipmentHourData(String name, int pass, double power, LocalDateTime time, String type, double runAvg, double passAvg);
	
	//���o�S�w�ɬq���P���������x�����q�P�ӹq�q���
	@Query(value = "SELECT new com.example.screw.vo.EquipmentHoursDay(sum(pass) as pass, sum(power) as power, time, type) FROM EquipmentHour where time >= ?1 and time <= ?2 group by time, type")
	public List<EquipmentHoursDay> getEquipmentHourData(LocalDateTime start, LocalDateTime end);
	
	//���o�S�w�ɬq�S�w���x�����q�P�ӹq�q���
	@Query(value = "SELECT * FROM screw.equipment_hour where time >= ?1 and time <= ?2 and name =?3", nativeQuery = true)
	public List<EquipmentHour> getEquipmentDayData(LocalDateTime start, LocalDateTime end, String name);
	
	//���o�S�w�ɬq�S�w���x�����q�P�ӹq�q���
	@Query(value = "SELECT new com.example.screw.vo.EquipmentWeek(name, avg(runAvg) as runAvg, avg(passAvg) as passAvg, avg(power) as power) FROM EquipmentHour where time >= ?1 and time <= ?2 and name =?3")
	public EquipmentWeek getEquipmentWeekData(LocalDateTime start, LocalDateTime end, String name);
	
	//�]�w���x����
	@Modifying
	@Query(value = "UPDATE screw.equipment_hour SET type = ?1 WHERE name = ?2", nativeQuery = true)
	public void setType(String type, String machineName);
	
	
	
	
}
