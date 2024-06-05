package com.example.screw.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.screw.entity.Maintenance;
import com.example.screw.vo.RecordStatistics;

@Repository
@Transactional
public interface MaintenanceDao extends JpaRepository<Maintenance, Integer>{
	
	//���o�S�w���x�����׸��
	@Query(value = "SELECT * FROM screw.maintenance where name =?1", nativeQuery = true)
	public List<Maintenance> getEquipmentRecord(String name);
	
	//���o�S�w���x�����׸�Ʋέp
	@Query(value = "SELECT new com.example.screw.vo.RecordStatistics( reason,count(reason)as count) FROM Maintenance where name = ?1 group by reason")
	public List<RecordStatistics> getRecordStatistics(String name);
	
	//�s�W���׬���
	@Modifying
	@Query(value = "INSERT INTO screw.maintenance (name, time, reason, result, note) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	public void insertEquipmentRecord(String name, LocalDateTime time, String reason, String result, String note);

	//��s���׬���
	@Modifying
	@Query(value = "UPDATE screw.maintenance SET reason = ?3, result = ?4, note = ?5 WHERE name = ?1 and time = ?2", nativeQuery = true)
	public void updateMachine(String name, LocalDateTime time, String reason, String result, String note);
	
	//�R�����׬���
	@Modifying
	@Query(value = "DELETE FROM screw.maintenance  WHERE name = ?1 and time = ?2", nativeQuery = true)
	public void deleteMachine(String name, LocalDateTime time);
	
	
}
