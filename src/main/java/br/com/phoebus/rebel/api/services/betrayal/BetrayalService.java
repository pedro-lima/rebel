package br.com.phoebus.rebel.api.services.betrayal;

import br.com.phoebus.rebel.api.dtos.out.BetrayalResumeDTO;
import br.com.phoebus.rebel.api.models.Betrayal;
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
public interface BetrayalService extends GenericService<Betrayal> {

	/**
	 * Get a list of the rebel's betrayals
	 */
	Iterable<BetrayalResumeDTO> listByRebel(long id);

	/**
	 * Checks whether a rebel should be blocked due to treason charges
	 */
	void ckeckMarkTraitor(Betrayal betrayalDB);

}
