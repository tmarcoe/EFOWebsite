package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.TransactionProfilesDao;
import com.efo.entity.TransactionProfiles;
import com.efo.interfaces.ITransactionProfiles;

@Service
public class TransactionProfilesService implements ITransactionProfiles {
	
	@Autowired
	TransactionProfilesDao profilesDao;

	@Override
	public void create(TransactionProfiles profiles) {
		profilesDao.create(profiles);

	}

	@Override
	public TransactionProfiles retrieve(String name) {
		return profilesDao.retrieve(name);
	}

	@Override
	public List<TransactionProfiles> retrieveRawList() {
		return profilesDao.retrieveRawList();
	}

	@Override
	public void update(TransactionProfiles profiles) {
		profilesDao.update(profiles);
	}

	@Override
	public void delete(TransactionProfiles profiles) {
		profilesDao.delete(profiles);
	}

}
