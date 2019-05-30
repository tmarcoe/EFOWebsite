package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.VerifyDao;
import com.efo.entity.Verify;
import com.efo.interfaces.IVerify;

@Service
public class VerifyService implements IVerify {
	
	@Autowired
	private VerifyDao verifyDao;

	@Override
	public void Create(Verify verify) {
		verifyDao.Create(verify);
	}

	@Override
	public Verify retrieve(String id) {
		return verifyDao.retrieve(id);
	}

	@Override
	public void delete(Verify verify) {
		verifyDao.delete(verify);

	}

}
