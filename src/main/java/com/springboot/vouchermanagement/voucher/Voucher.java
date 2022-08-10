package com.springboot.vouchermanagement.voucher;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Voucher {

	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String description;
	private boolean redeemed = false;
	private LocalDate expiryDate;
	private int timesUsed;
	private int maxUsage;
	private int userId;
	private String role;

	public Voucher() {

	}

	public Voucher(String title, String description, boolean redeemed, LocalDate expiryDate, int timesUsed,
			int maxUsage, int userId, String role) {
		super();
		this.title = title;
		this.description = description;
		this.redeemed = redeemed;
		this.expiryDate = expiryDate;
		this.timesUsed = timesUsed;
		this.maxUsage = maxUsage;
		this.userId = userId;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public boolean isRedeemed() {
		return redeemed;
	}

	public void setRedeemed(boolean redeemed) {
		this.redeemed = redeemed;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public int getTimesUsed() {
		return timesUsed;
	}

	public void setTimesUsed(int timesUsed) {
		this.timesUsed = timesUsed;
	}

	public int getMaxUsage() {
		return maxUsage;
	}

	public int getUserId() {
		return userId;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "Voucher [id=" + id + ", title=" + title + ", description=" + description + ", redeemed=" + redeemed
				+ ", expiryDate=" + expiryDate + ", timesUsed=" + timesUsed + ", maxUsage=" + maxUsage + ", userId="
				+ userId + ", role=" + role + "]";
	}

}
