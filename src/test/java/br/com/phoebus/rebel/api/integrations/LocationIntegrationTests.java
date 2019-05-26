package br.com.phoebus.rebel.api.integrations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phoebus.rebel.api.RebelApplication;
import br.com.phoebus.rebel.api.models.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SuppressWarnings("all")
@ActiveProfiles("test")
@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = BetrayalIntegrationTests.SCRIPTS_PATH) })
public class LocationIntegrationTests {

	public static final String SCRIPTS_PATH = "classpath:integrations/sql/locations.sql";

	public static final String API_URL = "/api/lacations/";

	@LocalServerPort
	private int port;

	private final TestRestTemplate restTemplate = new TestRestTemplate();

	// ========================================
	// SEARCH
	// ========================================

	// @Test
	public void findById() throws Exception {
		TestUtils.genericFindById(createURLWithPort(API_URL + "1"), "");
	}

	// @Test
	public void findByIdNotFound() throws Exception {
		TestUtils.genericFindByIdNotFound(createURLWithPort(API_URL + "100"));
	}

	// ========================================
	// SAVE
	// ========================================

	// @Test
	public void save() throws Exception {
		TestUtils.genericSave(createURLWithPort(API_URL), "", "");
	}

	// @Test
	public void saveWithRebelNotFound() throws Exception {
		Location location = (Location) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(location, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithGalaxyNotFound() throws Exception {
		Location location = (Location) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(location, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// ========================================
	// METHODS
	// ========================================

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
