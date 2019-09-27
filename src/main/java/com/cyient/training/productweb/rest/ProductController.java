package com.cyient.training.productweb.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping(method=RequestMethod.GET, value="/hello/{name}")
	public String sayHello(@PathVariable String name, 
			@RequestParam(value="company", required=false)  String company) {
		return "Hello "+ name +", welcome to " + company;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="")
	public List<Product> getAllProduct() {
		return productRepository.findAll();
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
