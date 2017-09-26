package com.physiccare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.physiccare.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
