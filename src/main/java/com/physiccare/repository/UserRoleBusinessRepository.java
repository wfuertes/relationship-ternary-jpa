package com.physiccare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.physiccare.model.UserRoleBusiness;

public interface UserRoleBusinessRepository extends JpaRepository<UserRoleBusiness, UserRoleBusiness.Id> {

}
