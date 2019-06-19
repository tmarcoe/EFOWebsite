package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.ProductsDao;
import com.efo.entity.Products;
import com.efo.interfaces.IProducts;

@Service
public class ProductsService implements IProducts {
	
	@Autowired
	private ProductsDao productsDao;

	@Override
	public void create(Products products) {
		productsDao.create(products);

	}

	@Override
	public Products retrieve(String product_id) {
		return productsDao.retrieve(product_id);
	}

	@Override
	public List<Products> retrieveRawList() {
		return productsDao.retrieveRawList();
	}
	
	public PagedListHolder<Products> retrieveList() {
		
		return new PagedListHolder<Products>(productsDao.retrieveRawList());
	}

	@Override
	public void update(Products products) {
		productsDao.update(products);

	}

	@Override
	public void delete(String product_id) {
		productsDao.delete(product_id);

	}

}
