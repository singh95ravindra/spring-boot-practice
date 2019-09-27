package com.cyient.training.productweb.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyient.training.productweb.entity.Product;
import com.cyient.training.productweb.entity.ProductRepository;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping(method=RequestMethod.GET, value="/hello/{name}")
	public String sayHello(@PathVariable String name, 
			@RequestParam(value="company", required=false)  String company) {
		return "Hello "+ name +", welcome to " + company;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="")
	public List<Product> getAllProduct(@RequestParam (value="page", required=false, defaultValue="0") int page, 
			@RequestParam(value="pageSize", required=false, defaultValue="10")  @Max(20) int pageSize) {
		Page<Product> pageRequest = productRepository.findAll(PageRequest.of(page, pageSize));
		return pageRequest.getContent();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="")
	public Product addProduct(@RequestBody Product p) {
		Product savedProduct = productRepository.save(p);
		return savedProduct;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public Product getProduct(@PathVariable Long id) {
		Optional<Product> findById = productRepository.findById(id);
		if(findById.isPresent()){
			return findById.get();
		}
		return null;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{name}")
	public List<Product> getProductByName(@PathVariable String name) {
		List<Product> listOfProducts = productRepository.findByName(name);
		return listOfProducts;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/by-price/{name}")
	public List<Product> getProductByNameAndPriceBetween(@PathVariable("name") String name, 
			@RequestParam(value="min", required=false) Double min, @RequestParam(value="max", required=false) Double max) {
		List<Product> listOfProducts = productRepository.findByNameAndPriceBetween(name, min, max);
		return listOfProducts;
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product uP) {
		List<Product> products = productRepository.findAll();
		for(Product p : products) {
			if(p.getId().equals(id)) {
				p.setName(uP.getName());
				p.setPrice(uP.getPrice());
				return p;
			}
		}
		return null;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public Product deleteProduct(@PathVariable Long id) {
		Optional<Product> findById = productRepository.findById(id);
		productRepository.deleteById(id);
		return findById.get();
	}
}
