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
import br.com.phoebus.rebel.api.dtos.in.EquipmentExchangeDTO;
import br.com.phoebus.rebel.api.models.Rebel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SuppressWarnings("all")
@ActiveProfiles("test")
@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = RebelIntegrationTests.SCRIPTS_PATH) })
public class RebelIntegrationTests {

	public static final String SCRIPTS_PATH = "classpath:integrations/sql/rebels.sql";

	public static final String API_URL = "/api/rebels/";

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
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithNameToShort() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithNameToLong() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithoutGender() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithWrongGender() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveUnderage() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithoutInventory() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveEmptyInventory() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveInventoryItemWithoutName() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveInventoryItemWithNameToShort() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveInventoryItemWithNameToLong() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveInventoryItemWithoutType() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// @Test
	public void saveWithWrongType() throws Exception {
		Rebel rebel = (Rebel) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(rebel, errorMessage, createURLWithPort(API_URL), HttpMethod.POST);
	}

	// ========================================
	// EXCHANGE
	// ========================================

	// @Test
	public void exchange() throws Exception {
		EquipmentExchangeDTO exchangeDTO = (EquipmentExchangeDTO) TestUtils.createByFileName("");

		Map body = new ObjectMapper().convertValue(exchangeDTO, Map.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<Object, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(createURLWithPort(API_URL + "exchange"),
				HttpMethod.PUT, entity, Map.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	// @Test
	public void exchangeWithoutRebelExchanger() throws Exception {
		EquipmentExchangeDTO exchangeDTO = (EquipmentExchangeDTO) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(exchangeDTO, errorMessage, createURLWithPort(API_URL + "exchange"),
				HttpMethod.PUT);
	}

	// @Test
	public void exchangeWithRebelNotFound() throws Exception {
		EquipmentExchangeDTO exchangeDTO = (EquipmentExchangeDTO) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(exchangeDTO, errorMessage, createURLWithPort(API_URL + "exchange"),
				HttpMethod.PUT);
	}

	// @Test
	public void exchangeWithoutItens() throws Exception {
		EquipmentExchangeDTO exchangeDTO = (EquipmentExchangeDTO) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(exchangeDTO, errorMessage, createURLWithPort(API_URL + "exchange"),
				HttpMethod.PUT);
	}

	// @Test
	public void exchangeEmptyItens() throws Exception {
		EquipmentExchangeDTO exchangeDTO = (EquipmentExchangeDTO) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(exchangeDTO, errorMessage, createURLWithPort(API_URL + "exchange"),
				HttpMethod.PUT);
	}

	// @Test
	public void exchangeWithItenNotFound() throws Exception {
		EquipmentExchangeDTO exchangeDTO = (EquipmentExchangeDTO) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(exchangeDTO, errorMessage, createURLWithPort(API_URL + "exchange"),
				HttpMethod.PUT);
	}

	// @Test
	public void exchangeWithDifferentExchangePoints() throws Exception {
		EquipmentExchangeDTO exchangeDTO = (EquipmentExchangeDTO) TestUtils.createByFileName("");
		String errorMessage = "";

		TestUtils.genericBadRequestOperation(exchangeDTO, errorMessage, createURLWithPort(API_URL + "exchange"),
				HttpMethod.PUT);
	}

	// ========================================
	// METHODS
	// ========================================

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
