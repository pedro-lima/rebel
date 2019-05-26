package br.com.phoebus.rebel.api.services.location;

import java.util.Optional;

import br.com.phoebus.rebel.api.models.Location;
import br.com.phoebus.rebel.api.services.GenericService;

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
public interface LocationService extends GenericService<Location> {

	/**
	 * Retrieve the last location resource that matches a given rebel's id
	 */
	Optional<Location> findLastByRebel(long id);

}
