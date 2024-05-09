package com.example.screw.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.screw.entity.ScrewMaterial;
import com.example.screw.vo.SettleOrderVo;

@Repository
@Transactional
public interface ScrewMaterialDao extends JpaRepository<ScrewMaterial, Integer> {
	
	// ���J�s�������q��
	@Modifying
	@Query(value = "INSERT IGNORE INTO screw.screw_material (order_number, name, aim, weight, raw, process) VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
	public int insertScrewMaterial(String orderNumber, String name, int aim, int weight, String raw, String process);

	// ��s�����q��
	@Modifying
	@Query(value = "UPDATE screw.screw_material SET name = ?2, aim = ?3, weight = ?4, raw = ?5, process = ?6 WHERE order_number = ?1", nativeQuery = true)
	public int updateScrewMaterial(String orderNumber, String name, int aim, int weight, String raw, String process);

	// �ھڳ渹�����A�N��� >= settleStart �B ��� <= settleEnd �� �q�׺�X�� �M pass�[�`�A�^��[{�渹,�q�y�`�M,���q�`�M}]
	// �`�q�� = ��S*�q�y���n�`�M/�B�@���/1000*�B�@�p��
//	@Query(value = "SELECT new com.example.screw.vo.SettleOrderVo(orderNumber, (SELECT dataRunAvg FROM screw.equipment where name = 'voltage')*sum(current)/3.5/count(current)/1000*count(current)*3.5/60/60 as , sum(pass))"
//			+ " FROM ReceiveData WHERE time >= ?1 AND time <= ?2 AND orderNumber LIKE ?3 GROUP BY orderNumber ORDER BY orderNumber")
	@Query(value = "SELECT new com.example.screw.vo.SettleOrderVo(orderNumber, (SELECT dataRunAvg FROM Equipment where name = 'voltage')*sum(current)/3.5/count(current)/1000*count(current)*3.5/60/60 AS kWhSum, sum(pass) AS produceTodaySum)"
			+ " FROM ReceiveData WHERE time >= ?1 AND time <= ?2 AND orderNumber LIKE ?3 GROUP BY orderNumber ORDER BY orderNumber")
	public List<SettleOrderVo> sumOrderKWhAndPass(String settleStart, String settleEnd, String orderNumber);
	
	// ������Ѧ��Ͳ��� material �����
	@Query(value = "SELECT * FROM screw.screw_material WHERE order_number IN (SELECT DISTINCT order_number FROM screw.receive_data WHERE time >= ?1 AND time <= ?2 ) ORDER BY order_no", nativeQuery = true)
	public List<ScrewMaterial> selectScrewMaterialToday(String settleStart, String settleEnd);

	
}
