package com.efo.interfaces;

import java.util.List;

import com.efo.entity.MarketPlaceVendors;

public interface IMarketPlaceVendors {
	
	public void create(MarketPlaceVendors marketPlaceVendors);
	public MarketPlaceVendors retrieve(Long reference);
	public List<MarketPlaceVendors> retrieveRawList();
	public void merge(MarketPlaceVendors marketPlaceVendors);
	public void delete(MarketPlaceVendors marketPlaceVendors);

}
