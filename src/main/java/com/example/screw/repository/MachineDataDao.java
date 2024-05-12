package com.example.screw.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.screw.entity.Equipment;
import com.example.screw.entity.EquipmentId;
import com.example.screw.entity.ReceiveData;
import com.example.screw.vo.ElectricityPeriod;
import com.example.screw.vo.EquipmentName;
import com.example.screw.vo.ReceiveDataCurrent;
import com.example.screw.vo.ReceiveDataLong;
import com.example.screw.vo.ReceiveDataStatus;


@Repository
@Transactional
public interface MachineDataDao extends JpaRepository<Equipment, EquipmentId>{
	
	// 取所有機台一天的資料(pass總數、ng總數、平均電流)
	@Query(value = "select new com.example.screw.vo.ReceiveDataLong(name, avg(current) as current, sum(pass) as pass, sum(ng) as ng) from ReceiveData where time < ?1 GROUP BY name")
	public List<ReceiveDataLong> machineDataDay(LocalDateTime tomorrow);
	
	// 取所有機台一天的idle次數
	@Query(value = "select new com.example.screw.vo.ReceiveDataStatus(name, COUNT(status) as status) from ReceiveData where time <= ?1 and status = 'idle' GROUP BY name")
	public List<ReceiveDataStatus> machineDataStatusIdle(LocalDateTime tomorrow);
	
	// 取所有機台一天的run次數
	@Query(value = "select new com.example.screw.vo.ReceiveDataStatus(name, COUNT(status) as status) from ReceiveData where time <= ?1 and status = 'run' GROUP BY name")
	public List<ReceiveDataStatus> machineDataStatusRun(LocalDateTime tomorrow);
	
	// 取所有機台一天的error次數
	@Query(value = "select new com.example.screw.vo.ReceiveDataStatus(name, COUNT(status) as status) from ReceiveData where time <= ?1 and status = 'error' GROUP BY name")
	public List<ReceiveDataStatus> machineDataStatusError(LocalDateTime tomorrow);
	
	// 取得所有機台run狀態平均電流
	@Query(value = "select new com.example.screw.vo.ReceiveDataCurrent(name, avg(current) as current) from ReceiveData where status = 'run' GROUP BY name")
	public List<ReceiveDataCurrent> machineRunCurrentAvg();
	
	// 取得所有機台idle狀態平均電流
	@Query(value = "select new com.example.screw.vo.ReceiveDataCurrent(name, avg(current) as current) from ReceiveData where status = 'idle' GROUP BY name")
	public List<ReceiveDataCurrent> machineIdleCurrentAvg();
	
	// 取得所有機台error狀態平均電流
	@Query(value = "select new com.example.screw.vo.ReceiveDataCurrent(name, avg(current) as current) from ReceiveData where status = 'error' GROUP BY name")
	public List<ReceiveDataCurrent> machineErrorCurrentAvg();
	
	// 取得特定機台一週的平均各項資料
	@Query(value = "select * from screw.equipment where name = ?1 and `del` = '0' and data_date <= CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 7  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusWeek(String machineName);
	
	// 取得特定機台一個月的平均各項資料
	@Query(value = "select * from screw.equipment where name = ?1 and `del` = '0' and data_date <= CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 30  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusMonth(String machineName);
	
	// 取得特定機台一年的平均各項資料
	@Query(value = "select * from screw.equipment where name = ?1 and `del` = '0' and data_date <= CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 365  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusYear(String machineName);
	
	// 取得目前電壓
	@Query(value = "select data_run_avg from screw.equipment where name = 'voltage'", nativeQuery = true)
	public double machineVoltage();
	
	//更新電壓
	@Modifying
	@Query(value = "UPDATE screw.equipment SET data_run_avg = ?1 WHERE name = 'voltage'", nativeQuery = true)
	public void updateVoltage(double voltage);
	
	// 所有機台一期的總電度
	@Query(value = "select new com.example.screw.vo.ElectricityPeriod(sum(runIT) as runIT, sum(idleIT) as idleIT, sum(errorIT) as errorIT) from Equipment where del = 'false' and  data_date >= ?1")
	public ElectricityPeriod machineITAll(LocalDate period);
	
	// 取得目前有的機台名稱(除了delete是true的)
	@Query(value = "SELECT new com.example.screw.vo.EquipmentName( name) from Equipment where del = 'false' GROUP BY name")
	public List<EquipmentName> machineName();
	
	// 刪除設備
	@Modifying
	@Query(value = "UPDATE screw.equipment SET `del` = '1' WHERE `name` = ?1", nativeQuery = true)
	public void machineDelete(String machineName);
	
}
