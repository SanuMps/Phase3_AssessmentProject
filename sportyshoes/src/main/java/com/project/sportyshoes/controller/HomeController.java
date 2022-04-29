package com.project.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.sportyshoes.global.GlobalData;
import com.project.sportyshoes.service.CategoryService;
import com.project.sportyshoes.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping({"/" , "/home"})
	public String home(Model model) {
		model.addAttribute("cartCount" , GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("cartCount" , GlobalData.cart.size());
		model.addAttribute("categories" , categoryService.getAllCategories());
		model.addAttribute("products" , productService.getAllProducts());
		
		return "shop";
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model , @PathVariable int id) {
		model.addAttribute("cartCount" , GlobalData.cart.size());
		model.addAttribute("categories" , categoryService.getAllCategories());
		model.addAttribute("products" , productService.getAllProductsByCategoryId(id));
		
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model , @PathVariable long id) {
		model.addAttribute("product" , productService.getProductById(id).get());
		model.addAttribute("cartCount" , GlobalData.cart.size());
		return "viewProduct";
	}
	
	
}

