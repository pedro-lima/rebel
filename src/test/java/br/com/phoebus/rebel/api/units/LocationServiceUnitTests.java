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

import br.com.phoebus.rebel.api.models.Location;
import br.com.phoebus.rebel.api.repositories.location.LocationRepository;
import br.com.phoebus.rebel.api.services.location.LocationService;
import br.com.phoebus.rebel.api.services.location.LocationServiceImpl;

@SuppressWarnings("all")
@RunWith(SpringRunner.class)
public class LocationServiceUnitTests {

	@TestConfiguration
	static class LocationServiceImplTestContextConfiguration {

		@Bean
		public LocationService locationService() {
			return new LocationServiceImpl();
		}
	}

	@MockBean
	private LocationRepository locationRepositoryMock;

	@Autowired
	private LocationService locationService;

	private final Location defaultLocation = new Location();

	@Before
	public void before() {
		when(locationRepositoryMock.findById(anyLong())).thenReturn(Optional.of(defaultLocation));
		when(locationRepositoryMock.save(isA(Location.class))).thenReturn(defaultLocation);
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
