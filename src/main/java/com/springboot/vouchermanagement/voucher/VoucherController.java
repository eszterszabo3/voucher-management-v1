
package com.springboot.vouchermanagement.voucher;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class VoucherController {
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	// VIEW ALL VOUCHERS
	
	@GetMapping("/users/vouchers")
	public Iterable<Voucher> getAllVouchers() {
		return voucherRepository.findAll();
	}
	
	// View all vouchers based on role
	@GetMapping("{role}/vouchers")
	public List<Voucher> getVouchersByRole(@PathVariable String role) {
		List<Voucher> vouchers = voucherRepository.findByRole(role);
		if(role.equalsIgnoreCase("Admin"))
			return vouchers;
		Predicate<? super Voucher> predicate = voucher -> voucher.isRedeemed() == true;
		vouchers.removeIf(predicate);
		return vouchers;
	}
	
	//View vouchers based on userId
	
	@GetMapping("users/{userId}/vouchers")
	public List<Voucher> getVouchersByUserId(@PathVariable int userId) {
		return voucherRepository.findByUserId(userId);
	}
	
	
	//View voucher based on voucherId
	
	@GetMapping("/users/{userId}/vouchers/{voucherId}")
	public Optional<Voucher> getVoucherById(@PathVariable int voucherId) {
		return voucherRepository.findById(voucherId);
	}
	
	// Add voucher
	
	@PostMapping("users/{userId}/vouchers")
	public ResponseEntity<Object> addVoucher(@PathVariable int userId, 
			@RequestBody Voucher voucher) {
		
		if(roleCheck(userId, "Admin").isPresent()) {
			voucherRepository.save(voucher);
			
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(voucher.getId())
					.toUri();;
					
			return ResponseEntity.created(location).build(); 
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}
	
	// Update & Delete Voucher by Id
	
	@PutMapping("users/{userId}/vouchers/{voucherId}")
	public ResponseEntity<Object> updateVoucherById(@PathVariable int userId, 
			@PathVariable int voucherId, @RequestBody Voucher voucher) {
	
		if(roleCheck(userId, "Admin").isPresent()) {
			voucherRepository.deleteById(voucherId);
			voucherRepository.save(voucher);
			return ResponseEntity.noContent().build();
		}
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}

	private Optional<Voucher> roleCheck(int userId, String role) {
		List<Voucher> users = getVouchersByUserId(userId);
		Predicate<? super Voucher> predicate = user -> user.getRole().equalsIgnoreCase(role);
		Optional<Voucher> admin = users.stream().filter(predicate).findFirst();
		return admin;
	}
	
	// User functions
	
	@PutMapping("/users/{userId}/vouchers/{voucherId}/redeem")
	public ResponseEntity<Object> redeemVoucherById(@PathVariable int userId,
			@PathVariable int voucherId) {
		if(roleCheck(userId, "User").isPresent()) {
			Optional<Voucher> optionalVoucher = getVoucherById(voucherId);
			Voucher voucher = optionalVoucher.get();
			if(voucher.getUserId() == userId && voucher.getTimesUsed() < voucher.getMaxUsage()) {
				int increment = voucher.getTimesUsed() + 1;
				voucher.setTimesUsed(increment);
				if(voucher.getTimesUsed() >= voucher.getMaxUsage()) 
					voucher.setRedeemed(true);
				
				voucherRepository.save(voucher);
				return ResponseEntity.noContent().build();
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}

	
	@DeleteMapping("users/{userId}/vouchers/{voucherId}/delete")
	public ResponseEntity<Object> deleteVoucher(@PathVariable int userId, 
			@PathVariable int voucherId) {
		
		if(roleCheck(userId, "Admin").isPresent()) {
			Optional<Voucher> voucher = getVoucherById(voucherId);
			if(voucher.isPresent())
				voucherRepository.delete(voucher.get());
		
			return ResponseEntity.noContent().build();
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}
	
	
}



	









