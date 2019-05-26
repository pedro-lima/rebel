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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.phoebus.rebel.api.events.EntityCreatedEvent;
import br.com.phoebus.rebel.api.models.Location;
import br.com.phoebus.rebel.api.services.location.LocationService;
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
@RequestMapping("/api/locations")
@Api(tags = { "locations" })
public class LocationResource {

	/**
	 * The service of the location
	 */
	@Autowired
	private LocationService locationService;

	/**
	 * The event used to add the location of a created resource
	 */
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * Save a location resource
	 * 
	 * @param location The resource that will be saved
	 * @param response The response object used to produce the response of the API
	 * @return The status of the saving process with the data of the resource saved
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Save a location resource", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource saved successfully") })
	public ResponseEntity<Location> save(@RequestBody @Valid Location location, HttpServletResponse response) {
		Location locationDB = locationService.save(location);
		publisher.publishEvent(new EntityCreatedEvent(this, response, locationDB.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(locationDB);
	}

	/**
	 * Retrieve the last location resource that matches a given rebel's id
	 * 
	 * @param id The id of the rebel
	 * @return The location resource found or a not found status message
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve the last location resource that matches a given rebel's id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Resource retrieved successfully"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> findLastByRebel(@PathVariable long id) {
		Optional<Location> location = locationService.findLastByRebel(id);
		return location.isPresent() ? ResponseEntity.ok(location.get()) : ResponseEntity.notFound().build();
	}

}
