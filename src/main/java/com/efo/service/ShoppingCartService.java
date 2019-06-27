package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ShoppingCartDao;
import com.efo.entity.ShoppingCart;
import com.efo.interfaces.IShoppngCart;

@Service
public class ShoppingCartService implements IShoppngCart {
	
	@Autowired
	private ShoppingCartDao shoppingCartDao;

	@Override
	public void create(ShoppingCart shoppingCart) {
		shoppingCartDao.create(shoppingCart);

	}

	@Override
	public ShoppingCart retrieve(String reference) {
		return shoppingCartDao.retrieve(reference);
	}

	@Override
	public ShoppingCart retrieveByUserId(Long user_id) {
		return shoppingCartDao.retrieveByUserId(user_id);
	}

	@Override
	public List<ShoppingCart> retrieveRawList() {
		return shoppingCartDao.retrieveRawList();
	}

	@Override
	public void merge(ShoppingCart shoppingCart) {
		shoppingCartDao.merge(shoppingCart);

	}
	
	public Long getShoppingCartCount(String username) {
		return shoppingCartDao.getShoppingCartCount(username);
	}

	@Override
	public void delete(String reference) {
		shoppingCartDao.delete(reference);

	}

}
