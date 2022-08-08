//package com.springboot.vouchermanagement.voucher;
//
//import java.time.LocalDate;
////import java.time.LocalDate;
////import java.util.ArrayList;
////import java.util.Arrays;
//import java.util.List;
////
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class VoucherCommandLineRunner implements CommandLineRunner{
//	
//	public VoucherCommandLineRunner(VoucherRepository voucherRepository) {
//		super();
//		this.voucherRepository = voucherRepository;
//	}
//
//	private VoucherRepository voucherRepository;
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Override
//	public void run(String... args) throws Exception {
////		voucherRepository.save(new Voucher(1, "Discount 10%", "3 x Discount on all children's toy products by 10%", false,
////			LocalDate.of(1, 1, 1), 0, 3, 1, "Admin"));	
////		voucherRepository.save(new Voucher(2, "Discount 5%", "2 x Discount on bed linen products by 5 until August 20th 2022%", false,
////				LocalDate.of(2022, 8, 20), 0, 2, 2, "User"));
//		
////		Voucher voucher1 = new Voucher(1, "Discount 10%", "3 x Discount on all children's toy products by 10%", false,
////				LocalDate.of(1, 1, 1), 0, 3);
////			
////		Voucher voucher2 = new Voucher(2, "Discount 5%", "2 x Discount on bed linen products by 5 until August 20th 2022%", false,
////				LocalDate.of(2022, 8, 20), 0, 2);
////		
////		Voucher voucher3 = new Voucher(3, "Discount 30%", "Discount on selected Stephen King books by 30%", false,
////				LocalDate.of(1, 1, 1), 0, 1);
////			
////		Voucher voucher4 = new Voucher(4, "$50 off", "$50 off Samsung products until September 1st 2022", false,
////					LocalDate.of(2022, 9, 1), 0, 1);
////			
////		List<Voucher> all_vouchers = new ArrayList<>(Arrays.asList(voucher1, voucher2, voucher3, voucher4));
////		List<Voucher> user_vouchers = new ArrayList<>(Arrays.asList(voucher1, voucher2));
//			
//		
//		voucherRepository.save(new Voucher("Discount 10%", "3 x Discount on all children's toy products by 10%", false,
//				LocalDate.of(1, 1, 1), 0, 3, 1, "Admin"));
//		voucherRepository.save(new Voucher("Discount 5%", "2 x Discount on bed linen products by 5 until August 20th 2022%", false,
//				LocalDate.of(2022, 8, 20), 0, 2, 2, "User"));
//		
//		List<Voucher> adminVouchers = voucherRepository.findByRole("Admin");
//		
//		adminVouchers.forEach(voucher -> logger.info(voucher.toString()));
//	}
//
//	
//	
//}
