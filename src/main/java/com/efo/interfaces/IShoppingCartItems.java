package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ShoppingCartItems;

public interface IShoppingCartItems {
	
	public void create(ShoppingCartItems item);
	public ShoppingCartItems retrieve(Long id);
	public List<ShoppingCartItems> retrieveRawList(String reference);
	public void changeShoppingCartQty(Long id, Integer qty);
	public void update(ShoppingCartItems item);
	public void delete(Long id);

}
