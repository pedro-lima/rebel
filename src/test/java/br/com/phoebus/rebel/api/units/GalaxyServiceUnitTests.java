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

import br.com.phoebus.rebel.api.models.Galaxy;
import br.com.phoebus.rebel.api.repositories.galaxy.GalaxyRepository;
import br.com.phoebus.rebel.api.services.galaxy.GalaxyService;
import br.com.phoebus.rebel.api.services.galaxy.GalaxyServiceImpl;

@SuppressWarnings("all")
@RunWith(SpringRunner.class)
public class GalaxyServiceUnitTests {

	@TestConfiguration
	static class GalaxyServiceImplTestContextConfiguration {

		@Bean
		public GalaxyService galaxyService() {
			return new GalaxyServiceImpl();
		}
	}

	@MockBean
	private GalaxyRepository galaxyRepositoryMock;

	@Autowired
	private GalaxyService galaxyService;

	private final Galaxy defaultGalaxy = new Galaxy();

	@Before
	public void before() {
		when(galaxyRepositoryMock.findById(anyLong())).thenReturn(Optional.of(defaultGalaxy));
		when(galaxyRepositoryMock.save(isA(Galaxy.class))).thenReturn(defaultGalaxy);
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
