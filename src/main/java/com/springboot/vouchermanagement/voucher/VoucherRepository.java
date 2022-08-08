package com.springboot.vouchermanagement.voucher;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends CrudRepository<Voucher, Integer>{
	public List<Voucher> findByUserId(int userId);
	public List<Voucher> findByRole(String role);
}
