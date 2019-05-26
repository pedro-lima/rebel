package br.com.phoebus.rebel.api.events.listeners;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.phoebus.rebel.api.events.EntityCreatedEvent;

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
@Component
public class EntityCreatedListener implements ApplicationListener<EntityCreatedEvent> {

	/**
	 * Handle an application event.
	 */
	@Override
	public void onApplicationEvent(EntityCreatedEvent EntityCreatedEvent) {
		HttpServletResponse response = EntityCreatedEvent.getResponse();
		Long id = EntityCreatedEvent.getId();

		addHeaderLocation(response, id);
	}

	/**
	 * Insert the location information inside the response Http
	 * 
	 * @param response The response object used to produce the response of the api
	 * @param id       The id of the entity savaed
	 */
	private void addHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
