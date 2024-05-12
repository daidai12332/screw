package com.example.screw.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.screw.entity.Equipment;
import com.example.screw.entity.EquipmentId;
import com.example.screw.entity.ReceiveData;


@Repository
@Transactional
public interface MachineDataDao extends JpaRepository<Equipment, EquipmentId>{
	
	// 取所有機台一天的資料(pass總數、ng總數、平均電流)
	@Query(value = "select name, sum(pass) as pass, sum(ng) as ng, avg(current) as current from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataDay();
	
	// 取所有機台一天的idle次數
	@Query(value = "select name,COUNT(status) as status from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY and status = 'idle' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataStatusIdle();
	
	// 取所有機台一天的run次數
	@Query(value = "select name,COUNT(status) as status from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY and status = 'run' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataStatusRun();
	
	// 取所有機台一天的error次數
	@Query(value = "select name,COUNT(status) as status from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY and status = 'error' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataStatusError();
	
	// 取得所有機台run狀態平均電流
	@Query(value = "select name,avg(current) as current from screw.receive_data where status = 'run' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineRunCurrentAvg();
	
	// 取得所有機台idle狀態平均電流
	@Query(value = "select name,avg(current) as current from screw.receive_data where status = 'idle' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineIdleCurrentAvg();
	
	// 取得所有機台error狀態平均電流
	@Query(value = "select name,avg(current) as current from screw.receive_data where status = 'error' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineErrorCurrentAvg();
	
	// 取得特定機台一週的平均各項資料
	@Query(value = "select * from screw.equipment where name = ?1 and data_date < CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 7  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusWeek(String machineName);
	
	// 取得特定機台一個月的平均各項資料
	@Query(value = "select * from screw.equipment where name = ?1 and data_date < CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 30  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusMonth(String machineName);
	
	// 取得特定機台一年的平均各項資料
	@Query(value = "select * from screw.equipment where name = ?1 and data_date < CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 365  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusYear(String machineName);
	
	// 取所有機台最新的資料
	@Query(value = "SELECT * FROM screw.receive_data ORDER BY time DESC LIMIT 10", nativeQuery = true)
	public List<ReceiveData> machineDataNow();
	
	// 所有機台一期的總電度
	@Query(value = "select sum(run_it) as run_it,sum(idle_it) as idle_it,sum(error_it) as error_it from screw.equipment where data_date >= ?1", nativeQuery = true)
	public Equipment machineITAll(LocalDate period);
	
	// 取得目前有的機台名稱(除了delete是true的)
	@Query(value = "SELECT name FROM screw.equipment  where `del` = '0' GROUP BY name;", nativeQuery = true)
	public List<Equipment> machineName();
	
	// 刪除設備
	@Query(value = "SELECT name FROM screw.equipment  where `del` = '0' GROUP BY name;", nativeQuery = true)
	public List<Equipment> machineDelete();
	
}
