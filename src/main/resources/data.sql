INSERT INTO voucher (ID, title, description, redeemed, expiry_Date, times_Used, max_Usage, user_Id, role) 
VALUES (1001, 'Discount 10%', '3 x Discount on all children toy products by 10%', false, 
						CURRENT_DATE(), 0, 3, 1, 'Admin');
INSERT INTO voucher (ID, title, description, redeemed, expiry_Date, times_Used, max_Usage, user_Id, role) 
VALUES (1002, 'Discount 5%', '2 x Discount on bed linen products by 5 until August 20th 2022%', false,
				CURRENT_DATE(), 0, 2, 2, 'User');