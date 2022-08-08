package com.springboot.vouchermanagement.voucher;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;

public class VoucherCommandLineRunner implements CommandLineRunner{
	
	public VoucherCommandLineRunner(VoucherRepository voucherRepository) {
		super();
		this.voucherRepository = voucherRepository;
	}

	private VoucherRepository voucherRepository;
	
	@Override
	public void run(String... args) throws Exception {
		voucherRepository.save(new Voucher(1, "Discount 10%", "3 x Discount on all children's toy products by 10%", false,
			LocalDate.of(1, 1, 1), 0, 3, 1, "Admin"));	
		voucherRepository.save(new Voucher(2, "Discount 5%", "2 x Discount on bed linen products by 5 until August 20th 2022%", false,
				LocalDate.of(2022, 8, 20), 0, 2, 2, "User"));
	}

	
	
}
