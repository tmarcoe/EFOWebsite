package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ShoppingCartItemsDao;
import com.efo.entity.ShoppingCartItems;
import com.efo.interfaces.IShoppingCartItems;

@Service
public class ShoppingCartItemsService implements IShoppingCartItems {
	
	@Autowired
	private ShoppingCartItemsDao shoppingCartItemDao;

	@Override
	public void create(ShoppingCartItems item) {
		shoppingCartItemDao.create(item);

	}

	@Override
	public ShoppingCartItems retrieve(Long id) {
		return shoppingCartItemDao.retrieve(id);
	}

	@Override
	public List<ShoppingCartItems> retrieveRawList(String reference) {
		return shoppingCartItemDao.retrieveRawList(reference);
	}

	@Override
	public void changeShoppingCartQty(Long id, Integer qty) {
		shoppingCartItemDao.changeShoppingCartQty(id, qty);

	}

	@Override
	public void update(ShoppingCartItems item) {
		shoppingCartItemDao.update(item);

	}

	@Override
	public void delete(Long id) {
		shoppingCartItemDao.delete(id);

	}

}
