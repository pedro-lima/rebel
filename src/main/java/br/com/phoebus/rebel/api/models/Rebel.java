package br.com.phoebus.rebel.api.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
@Table(name = "t_rebel")
@Data
@EqualsAndHashCode(callSuper = false, of = { "id" })
@NoArgsConstructor
@SQLDelete(sql = "update t_rebel set date_delete = NOW() where id = ?")
@Where(clause = "date_delete IS NULL")
@SequenceGenerator(name = "rebel_gen", sequenceName = "seq_rebel", allocationSize = 1)
public class Rebel extends GenericEntity {

	/**
	 * The database generated rebel ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rebel_gen")
	@Column(name = "id", insertable = false, updatable = false)
	@ApiModelProperty(notes = "The database generated rebel ID")
	private Long id;

	/**
	 * The name of the rebel
	 */
	@NotEmpty
	@Column(name = "name", nullable = false, length = MAX_SIZE_TEXT)
	@Size(min = MIN_SIZE_TEXT, max = MAX_SIZE_TEXT)
	@ApiModelProperty(notes = "The name of the rebel")
	private String name;

	/**
	 * The age of the rebel
	 */
	@Min(18)
	@Column(name = "age")
	@ApiModelProperty(notes = "The age of the rebel")
	private int age;

	/**
	 * The gender of the rebel
	 */
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type_gender")
	@ApiModelProperty(notes = "The gender of the rebel")
	private TypeGender genger;

	/**
	 * If the rebel is blocked to peform exchange
	 */
	@Column(name = "block")
	@ApiModelProperty(notes = "If the rebel is blocket to peform exchange")
	private boolean block;

	/**
	 * The inventory of the rebel
	 */
	@NotNull
	@Size(min = 1)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_rebel")
	@ApiModelProperty(notes = "The inventory of the rebel")
	private List<Equipment> inventory;

}
