
package com.springboot.vouchermanagement.voucher;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@GetMapping("users/{role}/vouchers")
	public List<Voucher> getVouchersByRole(@PathVariable String role) {
		List<Voucher> vouchers = voucherRepository.findByRole(role);
		if(role.equalsIgnoreCase("Admin"))
			return vouchers;
		Predicate<? super Voucher> predicate = voucher -> voucher.isRedeemed() == true;
		vouchers.removeIf(predicate);
		return vouchers;
	}
	
	// Add voucher
	
	@PostMapping("users/{userId}/vouchers")
	public ResponseEntity<Object> addVoucher(@PathVariable int userId, 
			@RequestBody Voucher voucher) {
		
		if(adminCheck(userId).isPresent()) {
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
	
		if(adminCheck(userId).isPresent()) {
			voucherRepository.deleteById(voucherId);
			voucherRepository.save(voucher);
			return ResponseEntity.noContent().build();
		}
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}

	private Optional<Voucher> adminCheck(int userId) {
		List<Voucher> users = voucherRepository.findByUserId(userId);
		Predicate<? super Voucher> predicate = user -> user.getRole().equalsIgnoreCase("Admin");
		Optional<Voucher> admin = users.stream().filter(predicate).findFirst();
		return admin;
	}
	

}

	









