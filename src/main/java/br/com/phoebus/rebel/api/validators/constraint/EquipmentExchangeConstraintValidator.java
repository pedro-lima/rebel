package br.com.phoebus.rebel.api.validators.constraint;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.phoebus.rebel.api.dtos.RebelExchangerDTO;
import br.com.phoebus.rebel.api.dtos.in.EquipmentExchangeDTO;
import br.com.phoebus.rebel.api.exceptions.ExchangerRebelNotFound;
import br.com.phoebus.rebel.api.services.rebel.RebelService;
import br.com.phoebus.rebel.api.validators.EquipmentExchangeValidator;

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
@Component
public class EquipmentExchangeConstraintValidator
		implements ConstraintValidator<EquipmentExchangeValidator, EquipmentExchangeDTO> {

	/**
	 * The service of the rebel
	 */
	@Autowired
	private RebelService rebelService;

	/**
	 * The annotation used in validation process
	 */
	private EquipmentExchangeValidator annotation;

	/**
	 * Get the service of the rebel
	 */
	public RebelService getRebelService() {
		return rebelService;
	}

	/**
	 * Get the annotation used in validation process
	 */
	public EquipmentExchangeValidator getAnnotation() {
		return annotation;
	}

	/**
	 * Initializes the validator in preparation for isValid(Object,
	 * ConstraintValidatorContext) calls. The constraint annotation for a given
	 * constraint declaration is passed.
	 */
	@Override
	public void initialize(EquipmentExchangeValidator constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	/**
	 * Implements the validation logic. The state of value must not be altered.
	 */
	@Override
	public boolean isValid(EquipmentExchangeDTO value, ConstraintValidatorContext context) {
		final long idExchanger1 = value.getExchanger1().getId();
		RebelExchangerDTO exchangerDB1 = this.getRebelService().findExchanger(idExchanger1)
				.orElseThrow(new ExchangerRebelNotFound(idExchanger1));

		final long idExchanger2 = value.getExchanger2().getId();
		RebelExchangerDTO exchangerDB2 = this.getRebelService().findExchanger(idExchanger2)
				.orElseThrow(new ExchangerRebelNotFound(idExchanger2));

		if (exchangerDB1.isBlock() || exchangerDB2.isBlock()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(this.annotation.message()).addConstraintViolation();
			return false;
		}

		if (validateOwnerItens(value.getExchanger1(), exchangerDB1)
				|| validateOwnerItens(value.getExchanger1(), exchangerDB2)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(this.annotation.message()).addConstraintViolation();
			return false;
		}

		if (validatePoints(value)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(this.annotation.message()).addConstraintViolation();
			return false;
		}

		return true;
	}

	/**
	 * Validate the total points of items that are exchanged between two rebels
	 */
	private boolean validatePoints(EquipmentExchangeDTO value) {
		int total1 = value.getExchanger1().getExchanges().stream().map(p -> p.getType().getPoints()).reduce(0,
				(subtotal, element) -> subtotal + element);

		int total2 = value.getExchanger2().getExchanges().stream().map(p -> p.getType().getPoints()).reduce(0,
				(subtotal, element) -> subtotal + element);

		return total1 != total2;
	}

	/**
	 * Valid if the rebel has a certain item
	 */
	private boolean validateOwnerItens(RebelExchangerDTO exchanger, RebelExchangerDTO exchangerDB) {
		List<Long> itensExchangeDB = exchangerDB.getExchanges().stream().map(p -> p.getId())
				.collect(Collectors.toList());

		return CollectionUtils.isNotEmpty(exchanger.getExchanges().stream().map(p -> p.getId())
				.filter(i -> !itensExchangeDB.contains(i)).collect(Collectors.toList()));
	}

}
