package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.LoanPaymentsDao;
import com.efo.entity.LoanPayments;
import com.efo.interfaces.ILoanPayments;

@Service
public class LoanPaymentsService implements ILoanPayments {
	
	@Autowired
	private LoanPaymentsDao loanPaymentsDao;

	@Override
	public void create(LoanPayments loanPayments) {
		loanPaymentsDao.create(loanPayments);
	}

	@Override
	public LoanPayments retreive(Long id) {
		return loanPaymentsDao.retreive(id);
	}

	@Override
	public List<LoanPayments> retreiveRawList(Long reference) {
		return loanPaymentsDao.retreiveRawList(reference);
	}

	@Override
	public void update(LoanPayments loanPayments) {
		loanPaymentsDao.update(loanPayments);
	}

	@Override
	public void delete(LoanPayments loanPayments) {
		loanPaymentsDao.delete(loanPayments);
	}
	public Double sumMontlyPayments(int month, int year) {
		return loanPaymentsDao.sumMontlyPayments(month, year);
	}
}
