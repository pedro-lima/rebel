package br.com.phoebus.rebel.api.resources;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.phoebus.rebel.api.events.EntityCreatedEvent;
import br.com.phoebus.rebel.api.models.Galaxy;
import br.com.phoebus.rebel.api.services.galaxy.GalaxyService;
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
@RequestMapping("/api/galaxies")
@Api(tags = { "galaxies" })
public class GalaxyResource {

	/**
	 * The service of the galaxy
	 */
	@Autowired
	private GalaxyService galaxyService;

	/**
	 * The event used to add the location of a created resource
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * View a list of available galaxies
	 * 
	 * @return The list of available galaxies
	 */
	@GetMapping
	@ApiOperation(value = "View a list of available galaxies", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Resource retrieved successfully") })
	public Iterable<Galaxy> list() {
		return galaxyService.list();
	}

	/**
	 * Save a galaxy resource
	 * 
	 * @param galaxy   The resource that will be saved
	 * @param response The response object used to produce the response of the API
	 * @return The status of the saving process with the data of the resource saved
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Save a galaxy resource", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource saved successfully") })
	public ResponseEntity<Galaxy> save(@RequestBody @Valid Galaxy galaxy, HttpServletResponse response) {
		Galaxy galaxyDB = galaxyService.save(galaxy);
		publisher.publishEvent(new EntityCreatedEvent(this, response, galaxyDB.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(galaxyDB);
	}

	/**
	 * Retrieve a galaxy resource that matches a given id
	 * 
	 * @param id The id of the galaxy
	 * @return The galaxy resource found or a not found status message
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve a galaxy resource that matches a given id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Resource retrieved successfully"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> find(@PathVariable long id) {
		Optional<Galaxy> galaxy = galaxyService.find(id);
		return galaxy.isPresent() ? ResponseEntity.ok(galaxy.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * Edit a galaxy resource
	 * 
	 * @param id     The id of the galaxy
	 * @param galaxy The resource that will be edited
	 * @return The status of the editing process with the data of the resource saved
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Update a galaxy resource", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Resource updated successfully") })
	public ResponseEntity<Galaxy> edit(@PathVariable long id, @RequestBody @Valid Galaxy galaxy) {
		try {
			Galaxy galaxyDB = galaxyService.edit(id, galaxy);
			Galaxy galaxyDTO = galaxyService.find(galaxyDB.getId()).get();

			return ResponseEntity.ok(galaxyDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
