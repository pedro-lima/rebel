package br.com.phoebus.rebel.api.integrations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hamcrest.CoreMatchers;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phoebus.rebel.api.models.Betrayal;
import br.com.phoebus.rebel.api.models.Rebel;

@SuppressWarnings("all")
public class TestUtils {

	private static final TestRestTemplate restTemplate = new TestRestTemplate();

	static Object createByFileName(String fileName) throws JAXBException {
		File file = new File(fileName);
		JAXBContext jaxbContext = JAXBContext.newInstance(Rebel.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(file);
	}

	static void genericBadRequestOperation(Object object, String errorMessage, String apiUrl, HttpMethod method) {
		Map body = new ObjectMapper().convertValue(object, Map.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Map<Object, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, method, entity, Map.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

		body = (Map<Object, Object>) responseEntity.getBody();

		assertEquals(body.get("status"), "BAD_REQUEST");
		assertThat(((List<String>) body.get("errors")).get(0), CoreMatchers.containsString(errorMessage));
	}

	static void genericFindById(String apiUrl, String fileResponse) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Map<Object, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

		Map<Object, Object> body = responseEntity.getBody();

		Map<Object, Object> test = new ObjectMapper().convertValue(TestUtils.createByFileName(fileResponse), Map.class);

		ReflectionAssert.assertReflectionEquals(body, test, ReflectionComparatorMode.LENIENT_ORDER);
	}

	static void genericFindByIdNotFound(String apiUrl) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Map<Object, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

		Map<Object, Object> body = responseEntity.getBody();

		assertEquals(body.get("status"), "NOT_FOUND");
	}

	static void genericList(String apiUrl, String fileResponse, int size) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Map<Object, Object>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<List> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, List.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

		List<Map<Object, Object>> body = (List<Map<Object, Object>>) responseEntity.getBody();

		assertEquals((int) body.size(), size);

		List<Map<Object, Object>> test = (List<Map<Object, Object>>) TestUtils.createByFileName(fileResponse);

		ReflectionAssert.assertReflectionEquals(body, test, ReflectionComparatorMode.LENIENT_ORDER);
	}

	static void genericSave(String apiUrl, String file, String fileResponse) throws Exception {
		Map body = new ObjectMapper().convertValue(TestUtils.createByFileName(file), Map.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<Object, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);

		body = (Map<Object, Object>) responseEntity.getBody();

		Map<Object, Object> test = new ObjectMapper().convertValue(TestUtils.createByFileName(fileResponse), Map.class);

		ReflectionAssert.assertReflectionEquals(body, test, ReflectionComparatorMode.LENIENT_ORDER);
	}

}
