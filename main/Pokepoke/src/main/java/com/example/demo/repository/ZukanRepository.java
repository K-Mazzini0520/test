package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Zukan;


public interface ZukanRepository extends JpaRepository<Zukan, Integer> {
	//@Query("SELECT t FROM zukan t ORDER BY t.id ASC")
    List<Zukan> findAllByOrderByIdAsc();
	
}