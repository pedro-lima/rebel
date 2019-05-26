package br.com.phoebus.rebel.api.services;

import java.util.Optional;

import br.com.phoebus.rebel.api.models.GenericEntity;

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
public interface GenericService<T extends GenericEntity> {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 */
	T save(T obj);

	/**
	 * Edits a given entity. Use the returned instance for further operations as the
	 * edit operation might have changed the entity instance completely.
	 */
	T edit(T obj);

	/**
	 * Edits a given entity. Use the returned instance for further operations as the
	 * edit operation might have changed the entity instance completely.
	 */
	T edit(long id, T obj);

	/**
	 * Retrieves an entity by its id.
	 */
	Optional<T> find(long id);

	/**
	 * Deletes a given entity.
	 */
	void delete(long id);

	/**
	 * Deletes a given entity.
	 */
	void delete(T obj);

	/**
	 * Returns all instances of the type.
	 */
	Iterable<T> list();

}
