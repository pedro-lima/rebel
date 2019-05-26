package br.com.phoebus.rebel.api.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
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
public class RebelReportDataDTO {

	/**
	 * The rebel's data that will be inserted in the report
	 */
	private List<RebelDTO> rebels;

	/**
	 * The summary's data that will be inserted in the report
	 */
	private SummaryDTO summary;

	@Data
	@EqualsAndHashCode(callSuper = false, of = { "id" })
	@AllArgsConstructor
	public static class RebelDTO {

		/**
		 * The id of the rebel
		 */
		private long id;

		/**
		 * The name of the rebel
		 */
		private String name;
	}

	@Data
	public static class SummaryDTO {

		/**
		 * The percentage of traitors
		 */
		private double traitorsPercentage;

		/**
		 * The percentage of active rebels
		 */
		private double rebelsPercentage;

		/**
		 * The average of food per rebel
		 */
		private double avgRessourceTypeFood;

		/**
		 * The average of water per rebel
		 */
		private double avgRessourceTypeWater;

		/**
		 * The average of ammunition per rebel
		 */
		private double avgRessourceTypeAmmunition;

		/**
		 * The average of weapon per rebel
		 */
		private double avgRessourceTypeWeapon;

		/**
		 * The total points of resources
		 */
		private double totalPointsResource;

		/**
		 * The total points of resources lost
		 */
		private double totalPointsResourceLost;

	}

}
