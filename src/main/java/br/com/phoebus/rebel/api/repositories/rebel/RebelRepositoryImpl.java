package br.com.phoebus.rebel.api.repositories.rebel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.phoebus.rebel.api.dtos.RebelExchangerDTO;
import br.com.phoebus.rebel.api.dtos.RebelReportDTO;
import br.com.phoebus.rebel.api.models.QEquipment;
import br.com.phoebus.rebel.api.models.QRebel;

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
@SuppressWarnings("all")
public class RebelRepositoryImpl implements RebelRepositoryQuery {

	/***
	 * The entity manager of the repository
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Retrieve data related to the rebel who will participate in an exchange
	 * process
	 */
	@Override
	public Optional<RebelExchangerDTO> findExchanger(long id) {
		QRebel reb = new QRebel("p");
		QEquipment equ = new QEquipment("e");

		final JPQLQuery<Tuple> jpaQuery = new JPAQuery(em).select(reb.id, reb.block, equ.id, equ.type).from(equ)
				.join(reb.inventory, equ);
		jpaQuery.where(reb.id.eq(id));

		RebelExchangerDTO dto = null;
		List<Tuple> result = jpaQuery.fetch();
		for (Tuple row : result) {
			if (dto == null) {
				dto = new RebelExchangerDTO();
				dto.setId(row.get(reb.id));
				dto.setBlock(row.get(reb.block));
				dto.setExchanges(new ArrayList<RebelExchangerDTO.EquipmentDTO>());
			} else {
				RebelExchangerDTO.EquipmentDTO equipmentDTO = new RebelExchangerDTO.EquipmentDTO();
				equipmentDTO.setId(row.get(equ.id));
				equipmentDTO.setType(row.get(equ.type));
				dto.getExchanges().add(equipmentDTO);
			}
		}

		return Optional.ofNullable(dto);
	}

	/**
	 * Create a object with all the data necessary to generate the report
	 */
	@Override
	public List<RebelReportDTO> getRebelReportData() {
		QRebel reb = new QRebel("p");
		QEquipment equ = new QEquipment("e");

		final JPQLQuery<Tuple> jpaQuery = new JPAQuery(em).select(reb.id, reb.block, reb.name, equ.id, equ.type)
				.from(equ).join(reb.inventory, equ);

		List<RebelReportDTO> list = new ArrayList<RebelReportDTO>();
		List<Tuple> result = jpaQuery.fetch();
		for (Tuple row : result) {
			final long id = row.get(reb.id);
			RebelReportDTO dto = new RebelReportDTO(id);

			if (list.contains(dto)) {
				dto = list.get(list.indexOf(dto));
			} else {
				dto.setName(row.get(reb.name));
				dto.setBlock(row.get(reb.block));
				dto.setItens(new ArrayList<RebelReportDTO.EquipmentDTO>());
				list.add(dto);
			}

			RebelReportDTO.EquipmentDTO equipmentDTO = new RebelReportDTO.EquipmentDTO();
			equipmentDTO.setId(row.get(equ.id));
			equipmentDTO.setType(row.get(equ.type));
			dto.getItens().add(equipmentDTO);
		}

		return list;
	}

}
