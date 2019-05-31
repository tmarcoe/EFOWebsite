package com.efo.interfaces;

import java.util.List;

import com.efo.entity.MarketPlaceProducts;

public interface IMarketPlaceProducts {
	
	public void create(MarketPlaceProducts marketPlaceProducts);
	public MarketPlaceProducts retrieve(Long product_reference);
	public List<MarketPlaceProducts> retrieveRawList();
	public void merge(MarketPlaceProducts marketPlaceProducts);
	public void delete(MarketPlaceProducts marketPlaceProducts);

}
