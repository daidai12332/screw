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

	@Modifying
	@Query(value = "INSERT IGNORE INTO screw.screw_material (order_no, name, aim, weight, raw, process) VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
	public int insertScrewMaterial(int orderNo, String name, int aim, int weight, String raw, String process);

	@Modifying
	@Query(value = "UPDATE screw.screw_material SET name = ?2, aim = ?3, weight = ?4, raw = ?5, process = ?6 WHERE order_no = ?1", nativeQuery = true)
	public int updateScrewMaterial(int orderNo, String name, int aim, int weight, String raw, String process);

	// �ھڳ渹�����A�N��� >= settleStart �B ��� <= settleEnd �� �q�y�q�[�` �M pass�[�`�A�^��[{�渹,�q�y�`�M,���q�`�M}]
	@Query(value = "SELECT new com.example.screw.vo.SettleOrderVo(orderNumber, sum(current), sum(pass)) FROM ReceiveData WHERE time >= ?1 AND time <= ?2 GROUP BY orderNumber ORDER BY orderNumber")
	public List<SettleOrderVo> sumOrderCurrentAndPass(String settleStart, String settleEnd);

	// �ھڳ渹�����A�N��� >= settleStart �B ��� <= settleEnd �� �q�y�q�[�` �M pass�[�`�A�^��[{�渹,�q�y�`�M,���q�`�M}]
	@Query(value = "SELECT new com.example.screw.vo.SettleOrderVo(orderNumber, sum(current), sum(pass)) FROM ReceiveData WHERE orderNumber = ?3 AND time >= ?1 AND time <= ?2 GROUP BY orderNumber ORDER BY orderNumber")
	public SettleOrderVo sumParticularOrderCurrentAndPass(String settleStart, String settleEnd, int orderNumber);

	
	// ������Ѧ��Ͳ��� material �����
	@Query(value = "SELECT * FROM screw.screw_material WHERE order_no IN (SELECT DISTINCT order_number FROM screw.receive_data WHERE time >= ?1 AND time <= ?2 ) ORDER BY order_no", nativeQuery = true)
	public List<ScrewMaterial> selectScrewMaterialToday(String settleStart, String settleEnd);

	
}
