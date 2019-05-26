package br.com.phoebus.rebel.api.resources;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.phoebus.rebel.api.dtos.in.EquipmentExchangeDTO;
import br.com.phoebus.rebel.api.events.EntityCreatedEvent;
import br.com.phoebus.rebel.api.models.Rebel;
import br.com.phoebus.rebel.api.services.rebel.RebelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/*
 * Copyright 2019 Phoebus Tecnologia Ltda.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
@RestController
@RequestMapping("/api/rebels")
@Api(tags = { "rebels" })
public class RebelResource {

	/**
	 * The service of the rebel
	 */
	@Autowired
	private RebelService rebelService;

	/**
	 * The event used to add the location of a created resource
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * View a list of available rebels
	 * 
	 * @return The list of available rebels
	 */
	@GetMapping
	@ApiOperation(value = "List all the rebels", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A list of all the rebels") })
	public Iterable<Rebel> list() {
		return rebelService.list();
	}

	/**
	 * Save a rebel resource
	 * 
	 * @param rebel    The resource that will be saved
	 * @param response The response object used to produce the response of the API
	 * @return The status of the saving process with the data of the resource saved
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Save a rebel resource", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource saved successfully") })
	public ResponseEntity<Rebel> save(@RequestBody @Valid Rebel rebel, HttpServletResponse response) {
		Rebel rebelDB = rebelService.save(rebel);
		publisher.publishEvent(new EntityCreatedEvent(this, response, rebelDB.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(rebelDB);
	}

	/**
	 * Retrieve a rebel resource that matches a given id
	 * 
	 * @param id The id of the rebel
	 * @return The rebel resource found or a not found status message
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve a rebel resource that matches a given id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Resource retrieved successfully"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> find(@PathVariable long id) {
		Optional<Rebel> rebel = rebelService.find(id);
		return rebel.isPresent() ? ResponseEntity.ok(rebel.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * Make an exchange of items between two rebels
	 * 
	 * @param dto      The data of the exchange
	 * @param response The response object used to produce the response of the API
	 */
	@PutMapping("/exchange")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Make an exchange of items between two rebels", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Resources updated successfully") })
	public void exchange(@RequestBody @Valid EquipmentExchangeDTO dto, HttpServletResponse response) {
		rebelService.exchange(dto);
	}

	/**
	 * Generate a report of rebels
	 * 
	 * @param response The response object used to produce the response of the API
	 * @return The generated report in PDF format
	 */
	@PostMapping("/report")
	@ApiOperation(value = "Generate a report of rebels", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Report generated successfully") })
	public HttpEntity<byte[]> gerarArquivoFuncionarios(final HttpServletResponse response) {
		String uuid = UUID.randomUUID().toString();
		byte[] arquivo = rebelService.generateReport();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		response.setContentLength(arquivo.length);
		response.setHeader("Content-Disposition", "attachment; filename=rebel_" + uuid + ".pdf");
		return new HttpEntity<byte[]>(arquivo, headers);
	}

}
