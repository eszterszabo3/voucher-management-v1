//package com.springboot.vouchermanagement.voucher;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Predicate;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class VoucherService {
//
//	public List<Voucher> getAllVouchers(int userId) {
//		User user = findByUserId(userId);
//		if(user.getRole() == "Admin" || user.getRole() == "User") 
//			return user.getVouchers();
//		return null;
//	}
//
//	private User findByUserId(int userId) {
//		
//		Predicate<? super User> predicate = user -> user.getUserid() == userId;
//		return users.stream().filter(predicate).findFirst().get();
//	}
//
//	public int addVoucher(int userId, Voucher voucher) {
//		User user = findByUserId(userId);
//		if(user.getRole() == "Admin") {
//			List<Voucher> vouchers = getAllVouchers(userId);
//			voucher.setId(generateRandomId());
//			vouchers.add(voucher);
//			return voucher.getId();
//		}
//		return 0;
//	}
//	
//	private int generateRandomId() {
//		SecureRandom secureRandom = new SecureRandom();
//		return new BigInteger(32, secureRandom).intValue();
//	}
//
//	public int updateVoucherById(int userId, int voucherId, Voucher voucher) {
//		User user = findByUserId(userId);
//		if(user.getRole() == "Admin") {
//			List<Voucher> vouchers = getAllVouchers(userId);
//			Predicate<? super Voucher> predicate = v -> v.getId() == voucherId;
//			boolean removed = vouchers.removeIf(predicate);
//			if(!removed)
//				return 0;
//			voucher.setId(voucherId);
//			vouchers.add(voucher);
//			return voucherId;
//		}
//		return 0;
//	}
//
//	public int deleteVoucherById(int userId, int voucherId) {
//		User user = findByUserId(userId);
//		if(user.getRole() == "Admin") {
//			List<Voucher> vouchers = getAllVouchers(userId);
//			
//			Predicate<? super Voucher> predicate = v -> v.getId() == voucherId;
//			
//			boolean removed = vouchers.removeIf(predicate);
//			if(!removed)
//				return 0;
//			
//			return voucherId;
//		}
//		return 0;
//	}
//	
//}
