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
//	public static List<User> users = new ArrayList<>();
//	public static List<Voucher> all_vouchers;
//	public static List<Voucher> admin_vouchers;
//	public static List<Voucher> user_vouchers;
//	
//	static {
//		
//		Voucher voucher1 = new Voucher(1, "Discount 10%", "3 x Discount on all children's toy products by 10%", false,
//			LocalDate.of(1, 1, 1), 0, 3);
//		
//		Voucher voucher2 = new Voucher(2, "Discount 5%", "2 x Discount on bed linen products by 5 until August 20th 2022%", false,
//				LocalDate.of(2022, 8, 20), 0, 2);
//		
//		Voucher voucher3 = new Voucher(3, "Discount 30%", "Discount on selected Stephen King books by 30%", false,
//				LocalDate.of(1, 1, 1), 0, 1);
//		
//		Voucher voucher4 = new Voucher(4, "$50 off", "$50 off Samsung products until September 1st 2022", false,
//				LocalDate.of(2022, 9, 1), 0, 1);
//		
//		all_vouchers = new ArrayList<>(Arrays.asList(voucher1, voucher2, voucher3, voucher4));
//		user_vouchers = new ArrayList<>(Arrays.asList(voucher1, voucher2));
//		
//		User user1 = new User(1, "Admin", all_vouchers);
//		User user2 = new User(2, "User", user_vouchers);
//
//		users.add(user1);
//		users.add(user2); 
//		
//		
//	}
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
