package com.efo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.SalesItemDao;
import com.efo.entity.SalesItem;
import com.efo.interfaces.ISalesItem;

@Service
public class SalesItemService implements ISalesItem {

	@Autowired
	private SalesItemDao salesItemDao;
	
	@Override
	public void create(SalesItem salesItem) {
		salesItemDao.create(salesItem);
	}

	@Override
	public SalesItem retrieve(Long item_id) {
		return salesItemDao.retrieve(item_id);
	}

	@Override
	public List<SalesItem> retrieveRawList() {
		return salesItemDao.retrieveRawList();
	}
	
	public List<SalesItem> retrieveRawList(Long reference) {
		return salesItemDao.retrieveRawList(reference);
	}

	public PagedListHolder<SalesItem> retrieveList() {
		return new PagedListHolder<SalesItem>(salesItemDao.retrieveRawList());
	}
	
	@Override
	public void update(SalesItem salesItem) {
		salesItemDao.update(salesItem);
	}

	@Override
	public void delete(SalesItem salesItem) {
		salesItemDao.delete(salesItem);
	}
	
	public void delete(Long item_id) {
		SalesItem salesItem = salesItemDao.retrieve(item_id);
		salesItemDao.delete(salesItem);
	}
	
	public void deleteSalesItem(Long item_id) {
		salesItemDao.deleteSalesItem(item_id);
	}
	
	public SalesItem getItemBySku(Long reference, String sku) {
		return salesItemDao.getItemBySku(reference, sku);
	}
	
	public void addQuantity(SalesItem salesItem, double qty) {
		salesItemDao.addQuantity(salesItem, qty);
	}
	
	public Long rowCount(Long reference) {
		return salesItemDao.rowCount(reference);
	}
	
	public Double totalItems(Long reference) {
		return salesItemDao.totalItems(reference);
	}
	
}
