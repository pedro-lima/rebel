package br.com.phoebus.rebel.api.resources;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.phoebus.rebel.api.dtos.out.BetrayalResumeDTO;
import br.com.phoebus.rebel.api.events.EntityCreatedEvent;
import br.com.phoebus.rebel.api.models.Betrayal;
import br.com.phoebus.rebel.api.services.betrayal.BetrayalService;
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
@RequestMapping("/api/betrayals")
@Api(tags = { "betrayals" })
public class BetrayalResource {

	/**
	 * The service of the betrayal
	 */
	@Autowired
	private BetrayalService betrayalService;

	/**
	 * The event used to add the location of a created resource
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * Save a betrayal resource
	 * 
	 * @param betrayal The resource that will be saved
	 * @param response The response object used to produce the response of the API
	 * @return The status of the saving process with the data of the resource saved
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Save a betrayal resource", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource saved successfully") })
	public ResponseEntity<Betrayal> save(@RequestBody @Valid Betrayal betrayal, HttpServletResponse response) {
		Betrayal betrayalDB = betrayalService.save(betrayal);
		betrayalService.ckeckMarkTraitor(betrayalDB);

		publisher.publishEvent(new EntityCreatedEvent(this, response, betrayalDB.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(betrayalDB);
	}

	/**
	 * Retrieve a betrayal resource that matches a given id
	 * 
	 * @param id The id of the betrayal
	 * @return The betrayal resource found or a not found status message
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve a betrayal resource that matches a given id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Resource successfully retrieved"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> find(@PathVariable long id) {
		Optional<Betrayal> betrayal = betrayalService.find(id);
		return betrayal.isPresent() ? ResponseEntity.ok(betrayal.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * View a list of the rebel's betrayals
	 * 
	 * @param id The id of the rebel
	 * @return The list of rebels found or a not found status message
	 */
	@GetMapping("/rebel/{id}")
	@ApiOperation(value = "View a list of the rebel's betrayals", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Resource successfully retrieved"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> listByRebel(@PathVariable long id) {
		Iterable<BetrayalResumeDTO> result = betrayalService.listByRebel(id);
		return IteratorUtils.isEmpty(result.iterator()) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
	}

}
