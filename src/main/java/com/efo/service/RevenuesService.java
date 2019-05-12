package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.RevenuesDao;
import com.efo.entity.Revenues;
import com.efo.interfaces.IRevenues;

@Service
public class RevenuesService implements IRevenues {
	
	@Autowired
	private RevenuesDao revenuesDao;

	@Override
	public void create(Revenues revenues) {
		revenuesDao.create(revenues);
	}

	@Override
	public Revenues retrieve(Long reference) {
		return revenuesDao.retrieve(reference);
	}

	@Override
	public List<Revenues> retrieveRawList() {
		return revenuesDao.retrieveRawList();
	}
	
	public Double sumCashRevenue(int month, int year) {
		return revenuesDao.sumCashRevenue(month, year);
	}

	@Override
	public void merge(Revenues revenues) {
		revenuesDao.merge(revenues);

	}

	@Override
	public void delete(Revenues revenues) {
		revenuesDao.delete(revenues);

	}

}
