package com.springboot.vouchermanagement.service;

import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.vouchermanagement.DAO.VoucherRepository;
import com.springboot.vouchermanagement.model.Voucher;

@Service
public class VoucherService {
	
	@Autowired
	private VoucherRepository voucherRepository;

	public Iterable<Voucher> getAllVouchers() {
		return voucherRepository.findAll();
	}
	
	public static boolean userAdd = false;
	
	public List<Voucher> getVouchersByRole(String role) {
		
		List<Voucher> vouchers = voucherRepository.findByRole(role);
		
		if(vouchers.isEmpty())
			return null;
		
		if(role.equalsIgnoreCase("Admin"))
			return vouchers;
		Predicate<? super Voucher> predicate = voucher -> voucher.isRedeemed() == true;
		Predicate<? super Voucher> predicate2 = voucher -> LocalDate.now().compareTo(voucher.getExpiryDate()) >= 0 && voucher.getExpiryDate().compareTo(LocalDate.of(1902, 1, 1)) != 0;
		vouchers.removeIf(predicate);
		vouchers.removeIf(predicate2);
		
		return vouchers;
	}

	public List<Voucher> getVouchersByUserId(int userId) {
		List<Voucher> vouchers = voucherRepository.findByUserId(userId);
		
		if(vouchers.isEmpty())
			return null;
				
		Predicate<? super Voucher> predicate = voucher -> voucher.isRedeemed() == true;
		Predicate<? super Voucher> predicate2 = voucher -> LocalDate.now().compareTo(voucher.getExpiryDate()) >= 0 && voucher.getExpiryDate().compareTo(LocalDate.of(1902, 1, 1)) != 0;
		vouchers.removeIf(predicate);
		vouchers.removeIf(predicate2);
		
		return vouchers;
	}

	public Voucher getVoucherById(int voucherId) {
		Optional<Voucher> voucher = voucherRepository.findById(voucherId);
		
		if(voucher.isEmpty() || voucher.get().isRedeemed() || (LocalDate.now().compareTo(voucher.get().getExpiryDate()) >= 0 && voucher.get().getExpiryDate().compareTo(LocalDate.of(1902, 1, 1)) != 0)) 
		{
			return null;
		}
		return voucher.get();
	}

	public ResponseEntity<Object> addVoucher(int userId, Voucher voucher) {
				
		Iterable<Voucher> allVouchers = getAllVouchers();
		Iterator<Voucher> vouchers = allVouchers.iterator();
		
		if(vouchers.hasNext() == false || roleCheck(userId, "Admin").isPresent()) {
			voucher.setId(generateRandomId());
			voucherRepository.save(voucher);
			
			
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(voucher.getId())
					.toUri();

			ArrayList<Integer> userIds = getUserIdByRole("User", voucherRepository.findByRole("User").size());
			if(userIds != null) {
				for(int user : userIds) {
					addDuplicateVoucher(user, voucher);
				}
			}
			
			return ResponseEntity.created(location).build(); 
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		
	}
	
	public ResponseEntity<Object> addDuplicateVoucher(int userId, Voucher voucher) {
			
			voucher.setId(generateRandomId());
			voucher.setUserId(userId);
			voucher.setRole("User");
			
			voucherRepository.save(voucher);
						
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(voucher.getId())
					.toUri();
			
			return ResponseEntity.created(location).build(); 
		}


	public ResponseEntity<Object> updateVoucherById(int userId, int voucherId, Voucher voucher) {
		
		Voucher beforeVoucher = voucherRepository.findById(voucherId).get();
		String description = beforeVoucher.getDescription();
				
		if(roleCheck(userId, "Admin").isPresent()) {
			voucher.setId(voucherId);
			voucherRepository.save(voucher);
			
			List<Voucher> duplicates = voucherRepository.findByDescription(description);
			if(duplicates != null) {
				duplicates.removeIf(duplicate -> duplicate.getRole().equals("Admin"));
				for(Voucher duplicate : duplicates) {
					updateDuplicateVoucherById(duplicate.getUserId(), duplicate.getId(), voucher);
				}
			}
			
			return ResponseEntity.noContent().build();
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);		
	}

	private void updateDuplicateVoucherById(int userId, int id, Voucher voucher) {
		voucher.setId(id);
		voucher.setUserId(userId);
		voucher.setRole("User");
		
		voucherRepository.save(voucher);
		
	}

	public ResponseEntity<Object> redeemVoucherById(int userId, int voucherId) {
		
		if(roleCheck(userId, "User").isPresent()) {
			Voucher voucher = getVoucherById(voucherId);
			if(voucher != null && voucher.getUserId() == userId) {
				voucher.setTimesUsed(voucher.getTimesUsed() + 1);
				if((voucher.getTimesUsed() >= voucher.getMaxUsage() && voucher.getMaxUsage() != -1) || (LocalDate.now().compareTo(voucher.getExpiryDate()) > 0 && voucher.getExpiryDate().compareTo(LocalDate.of(1902, 1, 1)) != 0)) {
					voucher.setRedeemed(true);
				}
				else if (LocalDate.now().compareTo(voucher.getExpiryDate()) >= 0 && voucher.getExpiryDate().compareTo(LocalDate.of(1902, 1, 1)) != 0){
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
						}
				
				voucherRepository.save(voucher);
				return ResponseEntity.noContent().build();
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<Object> deleteVoucherById(int userId, int voucherId) {
		
		Voucher beforeVoucher = voucherRepository.findById(voucherId).get();
		String description = beforeVoucher.getDescription();
				
		if(roleCheck(userId, "Admin").isPresent()) {
			Voucher voucher = getVoucherById(voucherId);
			if(voucher != null) {
				voucher.setRedeemed(true);
				voucherRepository.save(voucher);
				
				List<Voucher> duplicates = voucherRepository.findByDescription(description);
				
				if(duplicates != null) {
					duplicates.removeIf(duplicate -> duplicate.getRole().equals("Admin"));

					for(Voucher duplicate : duplicates) {
						Voucher duplicateVoucher = voucherRepository.findById(duplicate.getId()).get();
						duplicateVoucher.setRedeemed(true);
						voucherRepository.save(duplicateVoucher);
					}
				}
				
				return ResponseEntity.noContent().build();
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}
		
	public void deleteVoucher(int userId, int voucherId) {
		
		if(roleCheck(userId, "Admin").isPresent()) {
			Voucher voucher = voucherRepository.findById(voucherId).get();
			System.out.println(voucher);
			if(voucher != null) 
				voucherRepository.delete(voucher);
			else 
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
		} else 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}
	
	private Optional<Voucher> roleCheck(int userId, String role) {
		List<Voucher> users = getVouchersByUserId(userId);
		Predicate<? super Voucher> predicate = user -> user.getRole().equalsIgnoreCase(role);
		Optional<Voucher> admin = users.stream().filter(predicate).findFirst();
		return admin;
	}
	
	private int generateRandomId() {
		SecureRandom secureRandom = new SecureRandom();
		int randomId = new BigInteger(32, secureRandom).intValue();
		return Math.abs(randomId);
	}
	

	
	public ArrayList<Integer> getUserIdByRole(String role, int size) {
		ArrayList<Integer> users = new ArrayList<Integer>();
		ArrayList<Integer> uniqueUsers = new ArrayList<Integer>();
		List<Voucher> userVouchers = voucherRepository.findByRole(role);
		userVouchers.forEach(voucher -> users.add(voucher.getUserId()));

		for(int user : users) {
			if(!uniqueUsers.contains(user))
				uniqueUsers.add(user);
		}
		
		return uniqueUsers;
	}

}