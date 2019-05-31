package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.MarketPlaceVendorsDao;
import com.efo.entity.MarketPlaceVendors;
import com.efo.interfaces.IMarketPlaceVendors;

@Service
public class MarketPlaceVendorsService implements IMarketPlaceVendors {
	
	@Autowired
	MarketPlaceVendorsDao marketPlaceVendorsDao;

	@Override
	public void create(MarketPlaceVendors marketPlaceVendors) {
		marketPlaceVendorsDao.create(marketPlaceVendors);

	}

	@Override
	public MarketPlaceVendors retrieve(Long reference) {
		return marketPlaceVendorsDao.retrieve(reference);
	}

	@Override
	public List<MarketPlaceVendors> retrieveRawList() {
		return marketPlaceVendorsDao.retrieveRawList();
	}

	@Override
	public void merge(MarketPlaceVendors marketPlaceVendors) {
		marketPlaceVendorsDao.merge(marketPlaceVendors);

	}

	@Override
	public void delete(MarketPlaceVendors marketPlaceVendors) {
		marketPlaceVendorsDao.delete(marketPlaceVendors);

	}

}
