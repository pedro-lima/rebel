package br.com.phoebus.rebel.api.services.rebel;

import java.util.Optional;

import br.com.phoebus.rebel.api.dtos.RebelExchangerDTO;
import br.com.phoebus.rebel.api.dtos.in.EquipmentExchangeDTO;
import br.com.phoebus.rebel.api.models.Rebel;
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
public interface RebelService extends GenericService<Rebel> {

	/**
	 * Make an exchange of items between two rebels
	 * 
	 * @param dto The data of the exchange
	 */
	void exchange(EquipmentExchangeDTO dto);

	/**
	 * Retrieve all the data necessary to make a exchange that matches a rebel's id
	 * 
	 * @param id The a id of the rebel
	 * @return The data necessary to make and validate a exchange
	 */
	Optional<RebelExchangerDTO> findExchanger(long id);

	/**
	 * Generate a report of rebels
	 * 
	 * @return The generated report in PDF format
	 */
	byte[] generateReport();

}
