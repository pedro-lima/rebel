package br.com.phoebus.rebel.api.services.betrayal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phoebus.rebel.api.dtos.out.BetrayalResumeDTO;
import br.com.phoebus.rebel.api.models.Betrayal;
import br.com.phoebus.rebel.api.models.Rebel;
import br.com.phoebus.rebel.api.repositories.betrayal.BetrayalRepository;
import br.com.phoebus.rebel.api.services.GenericServiceImp;
import br.com.phoebus.rebel.api.services.rebel.RebelService;

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
@Service("betrayalService")
public class BetrayalServiceImpl extends GenericServiceImp<Betrayal> implements BetrayalService {

	/**
	 * The repository of the rebel
	 */
	@Autowired
	private BetrayalRepository repository;

	/**
	 * The service of the rebel
	 */
	@Autowired
	private RebelService rebelService;

	/**
	 * Get the repository of the rebel
	 */
	protected BetrayalRepository getRepository() {
		return this.repository;
	}

	/**
	 * Get the service of the rebel
	 */
	public RebelService getRebelService() {
		return rebelService;
	}

	/**
	 * Get a list of the rebel's betrayals
	 */
	@Override
	public Iterable<BetrayalResumeDTO> listByRebel(long id) {
		return getRepository().listByRebel(id);
	}

	/**
	 * Checks whether a rebel should be blocked due to treason charges
	 */
	@Override
	public void ckeckMarkTraitor(Betrayal betrayal) {
		final long rebelId = betrayal.getRebel().getId();

		if (getRepository().countBetrayal(rebelId) >= Betrayal.BETRAYAL_LIMIT) {
			Rebel rebelDB = this.getRebelService().find(rebelId).get();
			rebelDB.setBlock(true);

			rebelService.edit(rebelDB);
		}

	}

}
