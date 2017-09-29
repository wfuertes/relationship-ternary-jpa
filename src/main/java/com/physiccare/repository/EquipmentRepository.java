package com.physiccare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.physiccare.model.tenancy.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}
