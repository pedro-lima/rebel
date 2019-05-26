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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phoebus.rebel.api.RebelApplication;
import br.com.phoebus.rebel.api.models.Galaxy;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SuppressWarnings("all")
@ActiveProfiles("test")
@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = GalaxyIntegrationTests.SCRIPTS_PATH) })
public class GalaxyIntegrationTests {

	public static final String SCRIPTS_PATH = "classpath:integrations/sql/galaxies.sql";

	public static final String API_URL = "/api/galaxies/";

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
	// LIST
	// ========================================

	// @Test
	public void list() throws Exception {
		TestUtils.genericList(createURLWithPort(API_URL), "", 3);
	}

	// ========================================
	// SAVE
	// ========================================

	// @Test
	public void save() throws Exception {
		TestUtils.genericSave(createURLWithPort(API_URL), "", "");
	}

	// @Test
	public void saveWithoutName() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(galaxy, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithNameToShort() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(galaxy, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithNameToLong() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(galaxy, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// ========================================
	// UPDATE
	// ========================================

	// @Test
	public void update() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");

		Map body = new ObjectMapper().convertValue(galaxy, Map.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<Object, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(createURLWithPort(API_URL + "1"), HttpMethod.POST,
				entity, Map.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	// @Test
	public void updateWithoutName() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(galaxy, errorMessage, createURLWithPort(API_URL + "1"), HttpMethod.PUT);
	}

	// @Test
	public void updateWithNameToShort() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(galaxy, errorMessage, createURLWithPort(API_URL + "1"), HttpMethod.PUT);
	}

	// @Test
	public void updateWithNameToLong() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(galaxy, errorMessage, createURLWithPort(API_URL + "1"), HttpMethod.PUT);
	}

	// @Test
	public void updateWithGalaxyIdNotFound() throws Exception {
		Galaxy galaxy = (Galaxy) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(galaxy, errorMessage, createURLWithPort(API_URL + "100"), HttpMethod.PUT);
	}

	// ========================================
	// METHODS
	// ========================================

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
