package com.brs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brs.entities.BrsError;


public interface BrsErrorRepository extends JpaRepository<BrsError, Long> {

}
