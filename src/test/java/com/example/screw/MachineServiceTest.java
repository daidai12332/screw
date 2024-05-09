package com.example.screw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.screw.repository.MachineDataDao;
import com.example.screw.service.ifs.MachineService;

@SpringBootTest
public class MachineServiceTest {
	
	@Autowired
	private MachineService machineService;
	
	@Autowired
	private MachineDataDao machineDataDao;

	@Test
	public void machineDataDay() {
		
	}
}
