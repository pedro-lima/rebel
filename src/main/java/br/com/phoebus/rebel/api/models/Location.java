package br.com.phoebus.rebel.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "t_location")
@Data
@EqualsAndHashCode(callSuper = false, of = { "id" })
@NoArgsConstructor
@SQLDelete(sql = "update t_location set date_delete = NOW() where id = ?")
@Where(clause = "date_delete IS NULL")
@SequenceGenerator(name = "location_gen", sequenceName = "seq_location", allocationSize = 1)
public class Location extends GenericEntity {

	/**
	 * The database generated location ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_gen")
	@Column(name = "id", insertable = false, updatable = false)
	@ApiModelProperty(notes = "The database generated location ID")
	private Long id;

	/**
	 * The longitude of the location
	 */
	@Column(name = "longitude")
	@ApiModelProperty(notes = "The longitude of the location")
	private double longitude;

	/**
	 * The latitude of the location
	 */
	@Column(name = "latitude")
	@ApiModelProperty(notes = "The latitude of the location")
	private double latitude;

	/**
	 * The radius of the location
	 */
	@Column(name = "radius")
	@ApiModelProperty(notes = "The radius of the location")
	private double radius;

	/**
	 * The galaxy of the location
	 */
	@ManyToOne
	@JoinColumn(name = "id_galaxy", updatable = false)
	@ApiModelProperty(notes = "The galaxy of the location")
	private Galaxy galaxy;

	/**
	 * The rebel of the location
	 */
	@ManyToOne
	@JoinColumn(name = "id_rebel", updatable = false)
	@ApiModelProperty(notes = "The rebel of the location")
	private Rebel rebel;

}
