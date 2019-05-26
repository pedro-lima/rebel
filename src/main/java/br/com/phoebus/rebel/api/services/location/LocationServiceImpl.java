package br.com.phoebus.rebel.api.services.location;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phoebus.rebel.api.models.Location;
import br.com.phoebus.rebel.api.repositories.location.LocationRepository;
import br.com.phoebus.rebel.api.services.GenericServiceImp;

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
@Service("locationService")
public class LocationServiceImpl extends GenericServiceImp<Location> implements LocationService {

	/**
	 * The repository of the location
	 */
	@Autowired
	private LocationRepository repository;

	/**
	 * Get the repository of the location
	 */
	protected LocationRepository getRepository() {
		return this.repository;
	}

	/**
	 * Retrieve the last location resource that matches a given rebel's id
	 */
	@Override
	public Optional<Location> findLastByRebel(long id) {
		return this.repository.findLastByRebel(id);
	}

}
