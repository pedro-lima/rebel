package br.com.phoebus.rebel.api.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.phoebus.rebel.api.exceptions.DelectableEntityNotFoundException;
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
public abstract class GenericServiceImp<T extends GenericEntity> implements GenericService<T> {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public T save(T obj) {
		return getRepository().save(obj);
	}

	/**
	 * Edits a given entity. Use the returned instance for further operations as the
	 * edit operation might have changed the entity instance completely.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public T edit(T obj) {
		return getRepository().save(obj);
	}

	/**
	 * Edits a given entity. Use the returned instance for further operations as the
	 * edit operation might have changed the entity instance completely.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public T edit(long id, T obj) {
		T t = find(id).orElseThrow(DelectableEntityNotFoundException::new);
		BeanUtils.copyProperties(obj, t, obj.getFieldsNotUpdate());

		return getRepository().save(t);
	}

	/**
	 * Retrieves an entity by its id.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<T> find(long id) {
		return getRepository().findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {
		T t = find(id).orElseThrow(DelectableEntityNotFoundException::new);
		delete(t);
	}

	/**
	 * Deletes a given entity.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T obj) {
		getRepository().delete(obj);
	}

	/**
	 * Returns all instances of the type.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Iterable<T> list() {
		return getRepository().findAll();
	}

	/**
	 * Get the repository used by the child class
	 */
	protected abstract CrudRepository<T, Long> getRepository();

}
