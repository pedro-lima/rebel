package br.com.phoebus.rebel.api.dtos;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.phoebus.rebel.api.models.TypeEquipment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false, of = { "id" })
public class RebelExchangerDTO {

	/**
	 * The id of the rebel
	 */
	@Min(1)
	@ApiModelProperty(notes = "The id of the rebel")
	private long id;

	/**
	 * If the rebel is blocked to peform exchange
	 */
	@JsonIgnore
	private boolean block;

	/**
	 * The equipments of the rebel that he will exchange
	 */
	@NotNull
	@Size(min = 1)
	@ApiModelProperty(notes = "The equipments of the rebel that he will exchange")
	private List<EquipmentDTO> exchanges;

	@Data
	@EqualsAndHashCode(callSuper = false, of = { "id" })
	public static class EquipmentDTO {

		/**
		 * The id of the equipment
		 */
		@Min(1)
		@ApiModelProperty(notes = "The id of the equipment")
		private long id;

		/**
		 * The type of the equipment
		 */
		@JsonIgnore
		private TypeEquipment type;

	}

}
