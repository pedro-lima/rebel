package br.com.phoebus.rebel.api.dtos.in;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.phoebus.rebel.api.dtos.RebelExchangerDTO;
import br.com.phoebus.rebel.api.validators.EquipmentExchangeValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@Data
@EquipmentExchangeValidator
public class EquipmentExchangeDTO {

	/**
	 * One of the rebels who is participating in the exchange
	 */
	@Valid
	@NotNull
	@ApiModelProperty(notes = "One of the rebels who is participating in the exchange")
	private RebelExchangerDTO exchanger1;

	/**
	 * One of the rebels who is participating in the exchange
	 */
	@Valid
	@NotNull
	@ApiModelProperty(notes = "One of the rebels who is participating in the exchange")
	private RebelExchangerDTO exchanger2;

}
