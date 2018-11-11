package com.efo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PaymentsReceivedDao;
import com.efo.entity.PaymentsReceived;
import com.efo.interfaces.IPaymentsReceived;

@Service
public class PaymentsReceivedService implements IPaymentsReceived {
	
	@Autowired
	PaymentsReceivedDao receivedDao;
	
	@Override
	public void create(PaymentsReceived payments) {
		receivedDao.create(payments);
	}

	@Override
	public PaymentsReceived retreive(Long id) {
		return receivedDao.retreive(id);
	}

	@Override
	public void update(PaymentsReceived payments) {
		receivedDao.update(payments);

	}

	@Override
	public void delete(Long id) {
		receivedDao.delete(id);
	}
	
	public PagedListHolder<PaymentsReceived> retreiveList(Long reference) {
		
		return new PagedListHolder<PaymentsReceived>(receivedDao.retreiveList(reference));
	}
	
	public Date latestDate(Long reference) {
		return receivedDao.lastestDate(reference);
	}
	
	public List<Object[]> totalPayentsByPeriod(Date from, Date to) {
		return receivedDao.getReceivablesByPeriod(from, to);
	}
}
