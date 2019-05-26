package br.com.phoebus.rebel.api.repositories.betrayal;

import org.springframework.data.jpa.repository.Query;

import br.com.phoebus.rebel.api.dtos.out.BetrayalResumeDTO;
import br.com.phoebus.rebel.api.models.Betrayal;
import br.com.phoebus.rebel.api.repositories.GenericRepositoriy;

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
public interface BetrayalRepository extends GenericRepositoriy<Betrayal>, BetrayalRepositoryQuery {

	/**
	 * Return list of the rebel's betrayals
	 * 
	 * @param id The id of the rebel
	 * @return A list of the rebel's betrayals
	 */
	@Query(name = "listByRebel", value = "SELECT NEW br.com.phoebus.rebel.api.dtos.out.BetrayalResumeDTO(u.resume) FROM Betrayal u INNER JOIN u.rebel g WHERE g.id = ?1")
	Iterable<BetrayalResumeDTO> listByRebel(long id);

	/**
	 * Return the total number of the rebel's betrayals
	 * 
	 * @param id The id of the rebel
	 * @return Total number of the rebel's betrayals
	 */
	@Query(name = "listByRebel", value = "SELECT COUNT(u) FROM Betrayal u INNER JOIN u.rebel g WHERE g.id = ?1")
	int countBetrayal(long id);

}
