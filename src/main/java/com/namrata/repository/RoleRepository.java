package com.namrata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.namrata.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
