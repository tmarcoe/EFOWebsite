package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Products;

public interface IProducts {
	
	public void create(Products products);
	public Products retrieve(String product_id);
	public List<Products> retrieveRawList();
	public void update(Products products);
	public void delete(String product_id);

}
