package com.springboot.vouchermanagement.voucher;

import java.util.List;

public class User {
	
	public User() {
		
	}

	private int userId;
	private String role;
	private List<Voucher> vouchers;
	
	public User(int userId, String role, List<Voucher> vouchers) {
		super();
		this.userId = userId;
		this.role = role;
		this.vouchers = vouchers;
	}

	public int getUserid() {
		return userId;
	}

	public String getRole() {
		return role;
	}

	public List<Voucher> getVouchers() {
		return vouchers;
	}
	
}
