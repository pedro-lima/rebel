package br.com.phoebus.rebel.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@Entity
@Table(name = "t_equipment")
@Data
@EqualsAndHashCode(callSuper = false, of = { "id" })
@NoArgsConstructor
@SQLDelete(sql = "update t_equipment set date_delete = NOW() where id = ?")
@Where(clause = "date_delete IS NULL")
@SequenceGenerator(name = "equipment_gen", sequenceName = "seq_equipment", allocationSize = 1)
public class Equipment extends GenericEntity {

	/**
	 * The database generated equipment ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_gen")
	@Column(name = "id", insertable = false, updatable = false)
	@ApiModelProperty(notes = "The database generated equipment ID")
	private Long id;

	/**
	 * The name of the equipment
	 */
	@NotEmpty
	@Column(name = "name", nullable = false, length = MAX_SIZE_TEXT)
	@Size(min = MIN_SIZE_TEXT, max = MAX_SIZE_TEXT)
	@ApiModelProperty(notes = "The name of the equipment")
	private String name;

	/**
	 * The type of the equipment
	 */
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type_equipment", nullable = false)
	@ApiModelProperty(notes = "The type of the equipment")
	private TypeEquipment type;

}
