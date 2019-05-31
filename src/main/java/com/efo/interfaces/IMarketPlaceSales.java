package com.efo.interfaces;

import java.util.List;

import com.efo.entity.MarketPlaceSales;

public interface IMarketPlaceSales {
	
	public void create(MarketPlaceSales marketPlaceSales);
	public MarketPlaceSales retrieve(Long id);
	public List<MarketPlaceSales> retrieveRawList(Long product_reference);
	public void update(MarketPlaceSales marketPlaceSales);
	public void delete(MarketPlaceSales marketPlaceSales);

}
