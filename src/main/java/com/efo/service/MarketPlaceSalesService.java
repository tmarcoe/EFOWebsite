package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.efo.dao.MarketPlaceSalesDao;
import com.efo.entity.MarketPlaceSales;
import com.efo.interfaces.IMarketPlaceSales;

public class MarketPlaceSalesService implements IMarketPlaceSales {
	
	@Autowired
	private MarketPlaceSalesDao marketPlaceSalesDao;

	@Override
	public void create(MarketPlaceSales marketPlaceSales) {
		marketPlaceSalesDao.create(marketPlaceSales);

	}

	@Override
	public MarketPlaceSales retrieve(Long id) {
		return marketPlaceSalesDao.retrieve(id);
	}

	@Override
	public List<MarketPlaceSales> retrieveRawList(Long product_reference) {
		return marketPlaceSalesDao.retrieveRawList(product_reference);
	}

	@Override
	public void update(MarketPlaceSales marketPlaceSales) {
		marketPlaceSalesDao.update(marketPlaceSales);

	}

	@Override
	public void delete(MarketPlaceSales marketPlaceSales) {
		marketPlaceSalesDao.delete(marketPlaceSales);

	}

}
