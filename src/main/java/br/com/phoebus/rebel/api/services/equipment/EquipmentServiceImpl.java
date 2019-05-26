package br.com.phoebus.rebel.api.services.equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phoebus.rebel.api.models.Equipment;
import br.com.phoebus.rebel.api.repositories.equipment.EquipmentRepository;
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
@Service("equipmentService")
public class EquipmentServiceImpl extends GenericServiceImp<Equipment> implements EquipmentService {

	/**
	 * The repository of the equipment
	 */
	@Autowired
	private EquipmentRepository repository;

	/**
	 * Get the repository of the equipment
	 */
	protected EquipmentRepository getRepository() {
		return this.repository;
	}

}