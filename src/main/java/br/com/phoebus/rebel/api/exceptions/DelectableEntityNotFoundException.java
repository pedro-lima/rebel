package br.com.phoebus.rebel.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DelectableEntityNotFoundException extends GenericException {

	private static final long serialVersionUID = -7847011151089273396L;

	public DelectableEntityNotFoundException() {

	}

	public DelectableEntityNotFoundException(String message) {
		super(message);
	}

	public DelectableEntityNotFoundException(Throwable cause) {
		super(cause);
	}

	public DelectableEntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
