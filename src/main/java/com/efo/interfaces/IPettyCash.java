package com.efo.interfaces;

import com.efo.entity.PettyCash;

public interface IPettyCash {
	
	public void saveOrUpdate(PettyCash pettyCash);
	public PettyCash retrieve(int id);

}
