package com.efo.interfaces;

import java.util.List;

import com.efo.entity.TransactionProfiles;

public interface ITransactionProfiles {
	public void create(TransactionProfiles profiles);
	public TransactionProfiles retrieve(String name);
	public List<TransactionProfiles> retrieveRawList();
	public void update(TransactionProfiles profiles);
	public void delete(TransactionProfiles profiles);

}
