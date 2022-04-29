package com.project.sportyshoes.global;

import java.util.ArrayList;
import java.util.List;

import com.project.sportyshoes.model.Product;

public class GlobalData {
	
	public static List<Product>cart;
	
	static {
		cart = new ArrayList<Product>();
	}
	
	

}