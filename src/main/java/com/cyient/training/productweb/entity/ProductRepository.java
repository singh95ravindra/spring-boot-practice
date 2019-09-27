package com.cyient.training.productweb.entity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	public Page<Product> findAll(Pageable page);
	
	public List<Product> findByName(String name);
	
	public List<Product> findByNameAndPriceBetween(String name, Double min, Double max);
}
