package br.com.phoebus.rebel.api.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

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
public enum TypeGender {

	MALE("M"), FEMALE("F");

	private final String eventName;

	private static Map<String, TypeGender> nameMap = new HashMap<>(TypeGender.values().length);

	@JsonCreator
	public static TypeGender forValue(String value) {
		return nameMap.get(value);
	}

	@JsonValue
	public String eventName() {
		return eventName;
	}

	static {
		for (TypeGender eventType : TypeGender.values()) {
			nameMap.put(eventType.eventName(), eventType);
		}
	}
}
