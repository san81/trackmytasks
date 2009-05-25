package com.san.my.viewobj;

import java.util.Date;

import com.san.my.dataobj.AccountsDO;

public class AccountsView {
	
//	 Fields

	private Integer accountId;

	private String loginName;

	private String password;

	private String name;

	private Integer mobile;

	private String address;

	private String village;

	private Date regdate;

	public AccountsView(AccountsDO account){
		
		this.setAccountId(account.getAccountId());
		this.setLoginName(account.getLoginName());
		this.setPassword(account.getPassword());
		this.setName(account.getName());
		this.setMobile(account.getMobile());
		this.setAddress(account.getAddress());
		this.setVillage(account.getVillage());
		this.setRegdate(account.getRegdate());		
	}
	
	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

}
