package br.com.phoebus.rebel.api.events;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

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
@Getter
public class EntityCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	/**
	 * The response object used to produce the response of the api
	 */
	private HttpServletResponse response;

	/**
	 * The id of the entity
	 */
	private long id;

	public EntityCreatedEvent(Object source, HttpServletResponse response, long id) {
		super(source);
		this.response = response;
		this.id = id;
	}

}
