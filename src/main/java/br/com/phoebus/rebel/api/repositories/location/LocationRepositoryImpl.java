package br.com.phoebus.rebel.api.repositories.location;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.phoebus.rebel.api.models.Location;
import br.com.phoebus.rebel.api.models.QLocation;

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
@SuppressWarnings("all")
public class LocationRepositoryImpl implements LocationRepositoryQuery {

	/***
	 * The entity manager of the repository
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Retrieve the last location resource that matches a given rebel's id
	 */
	@Override
	public Optional<Location> findLastByRebel(long id) {
		QLocation location = QLocation.location;

		final JPQLQuery<Location> jpaQuery = new JPAQuery(em).from(location);
		jpaQuery.where(location.id.eq(id)).orderBy(location.id.desc()).limit(1);

		return Optional.ofNullable(jpaQuery.fetchFirst());
	}

}
