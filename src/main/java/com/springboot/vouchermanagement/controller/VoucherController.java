
package com.springboot.vouchermanagement.controller;

import java.util.List;

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

import com.springboot.vouchermanagement.model.Voucher;
import com.springboot.vouchermanagement.service.VoucherService;

@RestController
public class VoucherController {

	@Autowired
	private VoucherService voucherService;

	// VIEW ALL VOUCHERS

	@GetMapping("/users/vouchers")
	public Iterable<Voucher> getAllVouchers() {
		Iterable<Voucher> allVouchers = voucherService.getAllVouchers();

		if (allVouchers.iterator().hasNext() == false)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return allVouchers;
	}

	// View all vouchers based on role
	@GetMapping("{role}/vouchers")
	public List<Voucher> getVouchersByRole(@PathVariable String role) {

		List<Voucher> vouchers = voucherService.getVouchersByRole(role);

		if (vouchers == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return vouchers;
	}

	// View vouchers based on userId

	@GetMapping("users/{userId}/vouchers")
	public List<Voucher> getVouchersByUserId(@PathVariable int userId) {

		List<Voucher> vouchers = voucherService.getVouchersByUserId(userId);

		if (vouchers == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return vouchers;
	}

	// View voucher based on voucherId

	@GetMapping("/users/{userId}/vouchers/{voucherId}")
	public Voucher getVoucherById(@PathVariable int voucherId) {

		Voucher voucher = voucherService.getVoucherById(voucherId);

		if (voucher == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return voucher;
	}

	// Add voucher -- ONLY VALID FOR ADMIN

	@PostMapping("users/{userId}/vouchers")
	public ResponseEntity<Object> addVoucher(@PathVariable int userId, @RequestBody Voucher voucher) {

		return voucherService.addVoucher(userId, voucher);

	}

	// Update Voucher by Id

	@PutMapping("users/{userId}/vouchers/{voucherId}")
	public ResponseEntity<Object> updateVoucherById(@PathVariable int userId, @PathVariable int voucherId,
			@RequestBody Voucher voucher) {

		return voucherService.updateVoucherById(userId, voucherId, voucher);
	}

	// User functions

	@PutMapping("/users/{userId}/vouchers/{voucherId}/redeem")
	public ResponseEntity<Object> redeemVoucherById(@PathVariable int userId, @PathVariable int voucherId) {

		return voucherService.redeemVoucherById(userId, voucherId);

	}

	@DeleteMapping("users/{userId}/vouchers/{voucherId}")
	public ResponseEntity<Object> deleteVoucherById(@PathVariable int userId, @PathVariable int voucherId) {

		return voucherService.deleteVoucherById(userId, voucherId);
	}
	
	// Needed to test addVoucher method

	@DeleteMapping("users/{userId}/vouchers/{voucherId}/delete")
	public void deleteVoucher(@PathVariable int userId, @PathVariable int voucherId) {
		voucherService.deleteVoucher(userId, voucherId);
	}

}
