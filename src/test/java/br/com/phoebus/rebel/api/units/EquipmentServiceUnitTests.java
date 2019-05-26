package br.com.phoebus.rebel.api.units;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.phoebus.rebel.api.models.Equipment;
import br.com.phoebus.rebel.api.repositories.equipment.EquipmentRepository;
import br.com.phoebus.rebel.api.services.equipment.EquipmentService;
import br.com.phoebus.rebel.api.services.equipment.EquipmentServiceImpl;

@SuppressWarnings("all")
@RunWith(SpringRunner.class)
public class EquipmentServiceUnitTests {

	@TestConfiguration
	static class EquipmentServiceImplTestContextConfiguration {

		@Bean
		public EquipmentService equipmentService() {
			return new EquipmentServiceImpl();
		}
	}

	@MockBean
	private EquipmentRepository equipmentRepositoryMock;

	@Autowired
	private EquipmentService equipmentService;

	private final Equipment defaultEquipment = new Equipment();

	@Before
	public void before() {
		when(equipmentRepositoryMock.findById(anyLong())).thenReturn(Optional.of(defaultEquipment));
		when(equipmentRepositoryMock.save(isA(Equipment.class))).thenReturn(defaultEquipment);
	}

	// @Test
	public void saveCheckDateOperation() {

	}

	// @Test
	public void updateCheckDateOperation() {

	}

	// @Test
	public void deleteCheckDateOperation() {

	}

}
