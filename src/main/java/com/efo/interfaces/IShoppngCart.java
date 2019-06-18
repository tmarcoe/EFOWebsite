package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ShoppingCart;

public interface IShoppngCart {
	
	public void create(ShoppingCart shoppingCart);
	public ShoppingCart retrieve(String reference);
	public ShoppingCart retrieveByUserId(Long user_id);
	public List<ShoppingCart> retrieveRawList();
	public void merge(ShoppingCart shoppingCart);
	public void delete(String reference);

}
