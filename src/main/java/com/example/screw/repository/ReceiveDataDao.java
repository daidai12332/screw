package com.example.screw.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.screw.entity.ReceiveData;

@Transactional
@Repository
public interface ReceiveDataDao extends JpaRepository<ReceiveData, Integer>{

	// 插入收到的資料
	@Modifying
	@Query(value = "INSERT INTO screw.receive_data (name, status, order_number, current, pass, ng, time) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
	public void insertReceiveData(String name, String status ,String orderNumber, double current, int pass, int ng, LocalDateTime time);
	
	// 取所有機台最新的資料
	@Query(value = "select * from screw.receive_data ORDER BY time DESC LIMIT 10", nativeQuery = true)
	public List<ReceiveData> machineDataNow();


}
