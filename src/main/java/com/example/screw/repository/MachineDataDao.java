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
	
	// ���Ҧ����x�@�Ѫ����(pass�`�ơBng�`�ơB�����q�y)
	@Query(value = "select name, sum(pass) as pass, sum(ng) as ng, avg(current) as current from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataDay();
	
	// ���Ҧ����x�@�Ѫ�idle����
	@Query(value = "select name,COUNT(status) as status from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY and status = 'idle' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataStatusIdle();
	
	// ���Ҧ����x�@�Ѫ�run����
	@Query(value = "select name,COUNT(status) as status from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY and status = 'run' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataStatusRun();
	
	// ���Ҧ����x�@�Ѫ�error����
	@Query(value = "select name,COUNT(status) as status from screw.receive_data where time < CURRENT_DATE + INTERVAL 1 DAY and status = 'error' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineDataStatusError();
	
	// ���o�Ҧ����xrun���A�����q�y
	@Query(value = "select name,avg(current) as current from screw.receive_data where status = 'run' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineRunCurrentAvg();
	
	// ���o�Ҧ����xidle���A�����q�y
	@Query(value = "select name,avg(current) as current from screw.receive_data where status = 'idle' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineIdleCurrentAvg();
	
	// ���o�Ҧ����xerror���A�����q�y
	@Query(value = "select name,avg(current) as current from screw.receive_data where status = 'error' GROUP BY name", nativeQuery = true)
	public List<ReceiveData> machineErrorCurrentAvg();
	
	// ���o�S�w���x�@�g�������U�����
	@Query(value = "select * from screw.equipment where name = ?1 and data_date < CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 7  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusWeek(String machineName);
	
	// ���o�S�w���x�@�Ӥ몺�����U�����
	@Query(value = "select * from screw.equipment where name = ?1 and data_date < CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 30  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusMonth(String machineName);
	
	// ���o�S�w���x�@�~�������U�����
	@Query(value = "select * from screw.equipment where name = ?1 and data_date < CURRENT_DATE and data_date >= CURRENT_DATE - INTERVAL 365  DAY", nativeQuery = true)
	public List<Equipment> machineDataStatusYear(String machineName);
	
	// ���Ҧ����x�̷s�����
	@Query(value = "SELECT * FROM screw.receive_data ORDER BY time DESC LIMIT 10", nativeQuery = true)
	public List<ReceiveData> machineDataNow();
	
	// �Ҧ����x�@�����`�q��
	@Query(value = "select sum(run_it) as run_it,sum(idle_it) as idle_it,sum(error_it) as error_it from screw.equipment where data_date >= ?1", nativeQuery = true)
	public Equipment machineITAll(LocalDate period);
	
	// ���o�ثe�������x�W��(���Fdelete�Otrue��)
	@Query(value = "SELECT name FROM screw.equipment  where `del` = '0' GROUP BY name;", nativeQuery = true)
	public List<Equipment> machineName();
	
	// �R���]��
	@Query(value = "SELECT name FROM screw.equipment  where `del` = '0' GROUP BY name;", nativeQuery = true)
	public List<Equipment> machineDelete();
	
}
