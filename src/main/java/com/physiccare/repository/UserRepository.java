package com.physiccare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.physiccare.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
