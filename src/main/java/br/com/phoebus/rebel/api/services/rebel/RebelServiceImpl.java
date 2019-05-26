package br.com.phoebus.rebel.api.services.rebel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phoebus.rebel.api.dtos.RebelExchangerDTO;
import br.com.phoebus.rebel.api.dtos.RebelReportDTO;
import br.com.phoebus.rebel.api.dtos.RebelReportDataDTO;
import br.com.phoebus.rebel.api.dtos.in.EquipmentExchangeDTO;
import br.com.phoebus.rebel.api.exceptions.ReportException;
import br.com.phoebus.rebel.api.models.Equipment;
import br.com.phoebus.rebel.api.models.Rebel;
import br.com.phoebus.rebel.api.models.TypeEquipment;
import br.com.phoebus.rebel.api.repositories.rebel.RebelRepository;
import br.com.phoebus.rebel.api.services.GenericServiceImp;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
@Service("rebelService")
public class RebelServiceImpl extends GenericServiceImp<Rebel> implements RebelService {

	/**
	 * The repository of the rebel
	 */
	@Autowired
	private RebelRepository repository;

	/**
	 * Get the repository of the rebel
	 */
	protected RebelRepository getRepository() {
		return this.repository;
	}

	/**
	 * Make an exchange of items between two rebels
	 * 
	 * @param dto The data of the exchange
	 */
	@Override
	public void exchange(EquipmentExchangeDTO dto) {
		Rebel rebel1 = this.getRepository().findById(dto.getExchanger1().getId()).get();
		Rebel rebel2 = this.getRepository().findById(dto.getExchanger2().getId()).get();

		List<Equipment> equipmentsRebel1 = rebel1.getInventory().stream()
				.filter(i -> getItensDTO(dto.getExchanger1()).contains(i.getId())).collect(Collectors.toList());

		List<Equipment> equipmentsRebel2 = rebel2.getInventory().stream()
				.filter(i -> getItensDTO(dto.getExchanger2()).contains(i.getId())).collect(Collectors.toList());

		rebel1.getInventory().removeAll(equipmentsRebel1);
		rebel2.getInventory().addAll(equipmentsRebel2);

		rebel2.getInventory().removeAll(equipmentsRebel2);
		rebel2.getInventory().addAll(equipmentsRebel1);

		this.edit(rebel1);
		this.edit(rebel2);
	}

	/**
	 * Create a list with the id of the items that participate in the exchange
	 */
	private List<Long> getItensDTO(RebelExchangerDTO dto) {
		return dto.getExchanges().stream().map(p -> p.getId()).collect(Collectors.toList());
	}

	/**
	 * Retrieve all the data necessary to make a exchange that matches a rebel's id
	 * 
	 * @param id The a id of the rebel
	 * @return The data necessary to make and validate a exchange
	 */
	@Override
	public Optional<RebelExchangerDTO> findExchanger(long id) {
		return this.getRepository().findExchanger(id);
	}

	/**
	 * Generate a report of rebels
	 * 
	 * @return The generated report in PDF format
	 */
	@Override
	public byte[] generateReport() {
		List<RebelReportDTO> dto = this.getRepository().getRebelReportData();

		if (CollectionUtils.isNotEmpty(dto)) {
			throw new ReportException();
		}

		RebelReportDataDTO data = generateReportDate(dto);

		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			String fileName = classLoader.getResource("reports/rebels.jrxml").getFile();
			JasperReport jasperReport = JasperCompileManager.compileReport(fileName);

			Map<String, Object> params = new HashMap<String, Object>();
			fileName = classLoader.getResource("reports/logo.png").getFile();
			params.put("logo", fileName);
			params.put("traitorsPercentage", data.getSummary().getTraitorsPercentage());
			params.put("rebelsPercentage", data.getSummary().getRebelsPercentage());
			params.put("avgRessourceTypeFood", data.getSummary().getAvgRessourceTypeFood());
			params.put("avgRessourceTypeWater", data.getSummary().getAvgRessourceTypeWater());
			params.put("avgRessourceTypeAmmunition", data.getSummary().getAvgRessourceTypeAmmunition());
			params.put("avgRessourceTypeWeapon", data.getSummary().getAvgRessourceTypeWeapon());
			params.put("totalPointsResource", data.getSummary().getTotalPointsResource());
			params.put("totalPointsResourceLost", data.getSummary().getTotalPointsResourceLost());

			JRBeanCollectionDataSource reportSource = new JRBeanCollectionDataSource(data.getRebels());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, reportSource);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException(e);
		}
	}

	/**
	 * Generate the report DTO
	 */
	private RebelReportDataDTO generateReportDate(List<RebelReportDTO> dto) {
		RebelReportDataDTO data = new RebelReportDataDTO();
		data.setSummary(createReportSummaryDTO(dto));
		data.setRebels(createReportRebelDTO(dto));

		return data;
	}

	/**
	 * Generate the list of RebelReportDataDTO.RebelDTO
	 */
	private List<RebelReportDataDTO.RebelDTO> createReportRebelDTO(List<RebelReportDTO> listDTO) {
		return listDTO.stream().map(p -> new RebelReportDataDTO.RebelDTO(p.getId(), p.getName()))
				.collect(Collectors.toList());
	}

	/**
	 * Generate the a object ogf type RebelReportDataDTO.SummaryDTO
	 */
	private RebelReportDataDTO.SummaryDTO createReportSummaryDTO(List<RebelReportDTO> listDTO) {
		RebelReportDataDTO.SummaryDTO summary = new RebelReportDataDTO.SummaryDTO();

		if (CollectionUtils.isNotEmpty(listDTO)) {
			final List<RebelReportDTO.EquipmentDTO> allItens = listDTO.stream().flatMap(p -> p.getItens().stream())
					.collect(Collectors.toList());
			final List<RebelReportDTO.EquipmentDTO> allItensLost = listDTO.stream().filter(p -> p.isBlock())
					.flatMap(p -> p.getItens().stream()).collect(Collectors.toList());

			if (CollectionUtils.isNotEmpty(allItens)) {
				long totalItens = allItens.size();
				long totalFood = allItens.stream().filter(p -> p.getType() == TypeEquipment.FOOD).count();
				long totalWater = allItens.stream().filter(p -> p.getType() == TypeEquipment.WATER).count();
				long totalWeapon = allItens.stream().filter(p -> p.getType() == TypeEquipment.WATER).count();
				long totalAmmunition = allItens.stream().filter(p -> p.getType() == TypeEquipment.AMUNITION).count();

				double avgRessourceTypeFood = (totalFood * 100.0) / totalItens;
				double avgRessourceTypeWater = (totalWater * 100.0) / totalItens;
				double avgRessourceTypeAmmunition = (totalAmmunition * 100.0) / totalItens;
				double avgRessourceTypeWeapon = (totalWeapon * 100.0) / totalItens;
				long totalPointsResource = allItens.stream().map(p -> p.getType().getPoints()).reduce((x, y) -> x + y)
						.get();

				summary.setAvgRessourceTypeAmmunition(avgRessourceTypeAmmunition);
				summary.setAvgRessourceTypeFood(avgRessourceTypeFood);
				summary.setAvgRessourceTypeWater(avgRessourceTypeWater);
				summary.setAvgRessourceTypeWeapon(avgRessourceTypeWeapon);
				summary.setTotalPointsResource(totalPointsResource);
			}

			if (CollectionUtils.isNotEmpty(allItensLost)) {
				long totalPointsResourceLost = allItensLost.stream().map(p -> p.getType().getPoints())
						.reduce((x, y) -> x + y).get();
				summary.setTotalPointsResourceLost(totalPointsResourceLost);
			}

			final long totalRebels = listDTO.size();
			final long totalTraitors = listDTO.stream().filter(p -> p.isBlock()).count();
			final long totalNotTraitors = listDTO.stream().filter(p -> !p.isBlock()).count();

			final double traitorsPercentage = (totalTraitors * 100.0) / totalRebels;
			final double rebelsPercentage = (totalNotTraitors * 100.0) / totalRebels;

			summary.setTraitorsPercentage(traitorsPercentage);
			summary.setRebelsPercentage(rebelsPercentage);
		}

		return summary;
	}

}
