package br.com.phoebus.rebel.api.exceptions;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExchangerRebelNotFound extends GenericException implements Supplier<GenericException> {

	private static final long serialVersionUID = 6378868553329315917L;

	private long id;

	public ExchangerRebelNotFound() {

	}

	public ExchangerRebelNotFound(long id) {
		this.id = id;
	}

	public ExchangerRebelNotFound(long id, String message) {
		super(message);
		this.id = id;
	}

	public ExchangerRebelNotFound(long id, Throwable cause) {
		super(cause);
		this.id = id;
	}

	public ExchangerRebelNotFound(long id, String message, Throwable cause) {
		super(message, cause);
		this.id = id;
	}

	@Override
	public GenericException get() {
		return this;
	}

}
