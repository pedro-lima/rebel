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

import br.com.phoebus.rebel.api.models.Rebel;
import br.com.phoebus.rebel.api.repositories.rebel.RebelRepository;
import br.com.phoebus.rebel.api.services.rebel.RebelService;
import br.com.phoebus.rebel.api.services.rebel.RebelServiceImpl;

@SuppressWarnings("all")
@RunWith(SpringRunner.class)
public class RebelServiceUnitTests {

	@TestConfiguration
	static class RebelServiceImplTestContextConfiguration {

		@Bean
		public RebelService rebelService() {
			return new RebelServiceImpl();
		}
	}

	@MockBean
	private RebelRepository rebelRepositoryMock;

	@Autowired
	private RebelService rebelService;

	private final Rebel defaultRebel = new Rebel();

	@Before
	public void before() {
		when(rebelRepositoryMock.findById(anyLong())).thenReturn(Optional.of(defaultRebel));
		when(rebelRepositoryMock.save(isA(Rebel.class))).thenReturn(defaultRebel);
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
