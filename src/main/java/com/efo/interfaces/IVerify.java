package com.efo.interfaces;

import com.efo.entity.Verify;

public interface IVerify {
	public void Create(Verify verify);
	public Verify retrieve(String id);
	public void delete(Verify verify);
}
