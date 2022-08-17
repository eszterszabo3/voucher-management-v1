package com.springboot.vouchermanagement.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.vouchermanagement.model.Voucher;

@Repository
public interface VoucherRepository extends CrudRepository<Voucher, Integer>{
	public List<Voucher> findByUserId(int userId);
	public List<Voucher> findByRole(String role);
	public List<Voucher> findByDescription(String description);
}
