package com.physiccare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.physiccare.model.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {

}
