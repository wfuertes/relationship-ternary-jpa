package com.physiccare.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.physiccare.dto.EquipmentData;
import com.physiccare.model.tenancy.Equipment;
import com.physiccare.repository.EquipmentRepository;
import com.physiccare.repository.UserRepository;

@RestController
@RequestMapping(path = "/equipments")
public class EquipmentRS {

	private static Logger LOGGER = LoggerFactory.getLogger(EquipmentRS.class);

	@Autowired
	private EquipmentRepository repository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<EquipmentData> create(@RequestBody EquipmentData data) {

		// teste de utilização de repositorio no schema "public"
		LOGGER.info(userRepository.findAll().toString());

		Equipment equipment = new Equipment();
		equipment.setName(data.getName());
		equipment.setDetails(data.getDetails());

		// repositorio do "tencancy" corrent
		equipment = repository.save(equipment);
		data.setId(equipment.getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}

	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<EquipmentData>> getAll() {

		List<EquipmentData> equipmentDataList = repository.findAll().stream().map(equipment -> {
			EquipmentData data = new EquipmentData();
			data.setId(equipment.getId());
			data.setName(equipment.getName());
			data.setDetails(equipment.getDetails());
			return data;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(equipmentDataList);
	}
}
