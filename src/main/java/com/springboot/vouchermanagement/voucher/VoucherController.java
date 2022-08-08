
package com.springboot.vouchermanagement.voucher;

import java.net.URI;
import java.util.List;

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
	
	private VoucherService voucherService;

	public VoucherController(VoucherService voucherService) {
		super();
		this.voucherService = voucherService;
	}
	
	// Admin functions
	
	// View vouchers 
	@GetMapping("/{userId}/vouchers")
	public List<Voucher> listAllVouchers(@PathVariable int userId) {
		
		List<Voucher> vouchers = voucherService.getAllVouchers(userId);
		
		if(vouchers == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return vouchers;
	}
	
	// Add voucher
	
	@PostMapping("/{userId}/vouchers")
	public ResponseEntity<Object> addVoucher(@PathVariable int userId, 
			@RequestBody Voucher voucher) {
		
		int id = voucherService.addVoucher(userId, voucher);
		if(id == 0)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();;
				
		return ResponseEntity.created(location).build(); 
	
	}
	
	// Update Voucher by Id
	@PutMapping("/{userId}/vouchers/{voucherId}")
	public ResponseEntity<Object> updateVoucherById(@PathVariable int userId, 
			@PathVariable int voucherId, @RequestBody Voucher voucher) {
		
		int id = voucherService.updateVoucherById(userId, voucherId, voucher);
		
		if(id == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	// "Delete" voucher by Id
	@DeleteMapping("/{userId}/vouchers/{voucherId}")
	public ResponseEntity<Object> deleteVoucherById(@PathVariable int userId, 
			@PathVariable int voucherId) {
		
		int id = voucherService.deleteVoucherById(userId, voucherId);
		
		if(id == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.noContent().build();
	}	
}

	









