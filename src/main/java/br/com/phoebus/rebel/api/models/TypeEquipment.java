package br.com.phoebus.rebel.api.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
@AllArgsConstructor
public enum TypeEquipment {

	WEAPON(4, "WE"), AMUNITION(3, "AM"), WATER(2, "WA"), FOOD(1, "FO");

	@Getter
	private int points;

	@Getter
	private final String eventName;

	private static Map<String, TypeEquipment> nameMap = new HashMap<>(TypeEquipment.values().length);

	@JsonCreator
	public static TypeEquipment forValue(String value) {
		return nameMap.get(value);
	}

	@JsonValue
	public String eventName() {
		return eventName;
	}

	static {
		for (TypeEquipment eventType : TypeEquipment.values()) {
			nameMap.put(eventType.eventName(), eventType);
		}
	}

}
