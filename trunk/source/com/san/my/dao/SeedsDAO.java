package com.san.my.dao;

import java.util.List;

import com.san.my.dataobj.SeedsDO;

public interface SeedsDAO {
	
	/**
	 * @param seed
	 */
	public void saveSeed(SeedsDO seed);
	/**
	 * @param seedName
	 * @return
	 */
	public boolean isSeedNameExists(String seedName);
	
	/**
	 * Return all the list of seeds exists
	 * 
	 * @return
	 */
	public List listAllSeeds();
}
