package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.MarketPlaceProductsDao;
import com.efo.entity.MarketPlaceProducts;
import com.efo.interfaces.IMarketPlaceProducts;

@Service
public class MarketPlaceProductsService implements IMarketPlaceProducts {
	
	@Autowired
	private MarketPlaceProductsDao marketPlaceProductsDao;

	@Override
	public void create(com.efo.entity.MarketPlaceProducts marketPlaceProducts) {
		marketPlaceProductsDao.create(marketPlaceProducts);
	}

	@Override
	public com.efo.entity.MarketPlaceProducts retrieve(Long product_reference) {
		return marketPlaceProductsDao.retrieve(product_reference);
	}

	@Override
	public List<com.efo.entity.MarketPlaceProducts> retrieveRawList() {
		return marketPlaceProductsDao.retrieveRawList();
	}

	public List<MarketPlaceProducts> keywordSearch(String search) {
		return marketPlaceProductsDao.keywordSearch(search);
	}
	
	@Override
	public void merge(com.efo.entity.MarketPlaceProducts marketPlaceProducts) {
		marketPlaceProductsDao.merge(marketPlaceProducts);

	}

	@Override
	public void delete(Long product_reference) {
		marketPlaceProductsDao.delete(product_reference);

	}

}
