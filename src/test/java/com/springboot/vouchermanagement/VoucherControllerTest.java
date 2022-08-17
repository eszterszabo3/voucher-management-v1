package com.springboot.vouchermanagement;

/* DO NOT TEST CONTROLLER LOGIC 
 * - ONLY BUSINESS LOGIC AKA SERVICE 
 */


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class VoucherControllerTest {
	
	private static String GENERAL_VOUCHER_URL = "/users/1/vouchers";
	
	@Autowired
	private TestRestTemplate template;

	@Test
	void getAllVouchers_basic() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		String expectedResponse = """

				[{"id":1,"role":"User"},
				{"id":2,"role":"Admin"},
				{"id":3,"role":"Admin"},
				{"id":4,"role":"User"},
				{"id":5,"role":"Admin"},
				{"id":6,"role":"User"},
				{"id":7,"role":"User"},
				{"id":8,"role":"User"},
				{"id":9,"role":"User"}]
				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getAllVouchers_basic BODY " + responseEntity.getBody());
//		System.out.println("getAllVouchers_basic HEADER " + responseEntity.getHeaders());
//		System.out.println("getAllVouchers_basic STATUS " + responseEntity.getStatusCode());

	
	}
	
	@Test
	void getAllVouchers_empty() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/vouchers", String.class);
		
		//assertTrue(responseEntity.getStatusCode().is4xxClientError());
		
		System.out.println("getAllVouchers_empty BODY " + responseEntity.getBody());
		System.out.println("getAllVouchers_empty HEADER " + responseEntity.getHeaders());
		System.out.println("getAllVouchers_empty STATUS " + responseEntity.getStatusCode());
		
//		getAllVouchers_empty BODY [{"id":1,"title":"15% off","description":"2 x 15% off skincare products","redeemed":false,"expiryDate":"0001-12-31","timesUsed":0,"maxUsage":2,"userId":2,"role":"User"},{"id":2,"title":"20% off","description":"3 x 20% off children plush products valid until July 23rd 2024","redeemed":false,"expiryDate":"2024-07-23","timesUsed":0,"maxUsage":3,"userId":1,"role":"Admin"},{"id":3,"title":"10% off","description":"10% off Samsung products until September 4th 2022","redeemed":false,"expiryDate":"2022-09-04","timesUsed":0,"maxUsage":1,"userId":1,"role":"Admin"},{"id":4,"title":"20% off","description":"20% off Apple products valid until February 19th 2023","redeemed":false,"expiryDate":"2023-02-19","timesUsed":0,"maxUsage":1,"userId":2,"role":"User"},{"id":5,"title":"$100 off","description":"$100 off selected gaming monitors until August 10th 2022","redeemed":false,"expiryDate":"2022-08-10","timesUsed":0,"maxUsage":1,"userId":1,"role":"Admin"},{"id":6,"title":"20% off","description":"20% off Apple products","redeemed":false,"expiryDate":"0001-12-31","timesUsed":0,"maxUsage":1,"userId":2,"role":"User"},{"id":7,"title":"90% off","description":"90% off Douglas products","redeemed":false,"expiryDate":"0001-12-31","timesUsed":0,"maxUsage":1,"userId":2,"role":"User"},{"id":8,"title":"30% off","description":"3 x 30% off hardware tools until November 25th 2022","redeemed":true,"expiryDate":"2022-11-25","timesUsed":3,"maxUsage":3,"userId":3,"role":"User"},{"id":9,"title":"$30 off","description":"$30 off haircare products","redeemed":true,"expiryDate":"0001-12-30","timesUsed":0,"maxUsage":1,"userId":3,"role":"User"},{"id":1002,"title":"$10 off","description":"$10 off Stabilo pens valid until 2022 August 20th","redeemed":false,"expiryDate":"2022-08-20","timesUsed":0,"maxUsage":1,"userId":1,"role":"Admin"}]
//		getAllVouchers_empty HEADER [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Wed, 10 Aug 2022 13:47:19 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
//		getAllVouchers_empty STATUS 200 OK
		
	}

	@Test
	void getVouchersByRole_Admin() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/Admin/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		

		String expectedResponse = """

				[{"id":2,"role":"Admin"},
				{"id":3,"role":"Admin"},
				{"id":5,"role":"Admin"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getVouchersByRole_Admin BODY " + responseEntity.getBody());
//		System.out.println("getVouchersByRole_Admin HEADER " + responseEntity.getHeaders());
//		System.out.println("getVouchersByRole_Admin STATUS " + responseEntity.getStatusCode());

	}

	@Test
	void getVouchersByRole_User() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/User/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:

		String expectedResponse = """

				[{"id":1,"role":"User"},
				{"id":4,"role":"User"},
				{"id":6,"role":"User"},
				{"id":7,"role":"User"},
				{"id":8,"role":"User"},
				{"id":9,"role":"User"}]

				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getVouchersByRole_User BODY " + responseEntity.getBody());
//		System.out.println("getVouchersByRole_User HEADER " + responseEntity.getHeaders());
//		System.out.println("getVouchersByRole_User STATUS " + responseEntity.getStatusCode());
		

	}

	@Test
	void getVouchersByUserId_1() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/1/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:

		String expectedResponse = """

				[{"id":2,"userId":1,"role":"Admin"},
				{"id":3,"userId":1,"role":"Admin"},
				{"id":5,"userId":1,"role":"Admin"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getVouchersByUserId_1 BODY " + responseEntity.getBody());
//		System.out.println("getVouchersByUserId_1 HEADER " + responseEntity.getHeaders());
//		System.out.println("getVouchersByUserId_1 STATUS " + responseEntity.getStatusCode());

	}

	@Test
	void getVouchersByUserId_2() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/2/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:

		String expectedResponse = """

				[{"id":1,"userId":2,"role":"User"},
				{"id":4,"userId":2,"role":"User"},
				{"id":6,"userId":2,"role":"User"},
				{"id":7,"userId":2,"role":"User"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getVouchersByUserId_2 BODY " + responseEntity.getBody());
//		System.out.println("getVouchersByUserId_2 HEADER " + responseEntity.getHeaders());
//		System.out.println("getVouchersByUserId_2 STATUS " + responseEntity.getStatusCode());
		
	}

	@Test
	void getVouchersByUserId_3() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/3/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:

		String expectedResponse = """

				[{"id":8,"userId":3,"role":"User"},
				{"id":9,"userId":3,"role":"User"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getVouchersByUserId_3 BODY " + responseEntity.getBody());
//		System.out.println("getVouchersByUserId_3 HEADER " + responseEntity.getHeaders());
//		System.out.println("getVouchersByUserId_3 STATUS " + responseEntity.getStatusCode());

	}
	
	@Test
	void getVouchersById_user3_id8() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/3/vouchers/8", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:

		String expectedResponse = """

				{"id":8,"userId":3,"role":"User"}


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getVouchersById_user3_id8 BODY " + responseEntity.getBody());
//		System.out.println("getVouchersById_user3_id8 HEADER " + responseEntity.getHeaders());
//		System.out.println("getVouchersById_user3_id8 STATUS " + responseEntity.getStatusCode());

	}
	
	@Test
	void getVouchersById_user3_id9() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/3/vouchers/9", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:

		String expectedResponse = """

				{"id":9,"userId":3,"role":"User"}


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		System.out.println("getVouchersById_user3_id9 BODY " + responseEntity.getBody());
//		System.out.println("getVouchersById_user3_id9 HEADER " + responseEntity.getHeaders());
//		System.out.println("getVouchersById_user3_id9 STATUS " + responseEntity.getStatusCode());

	}
	
	@Test
	void addVoucher_basic() throws JSONException {

		String requestBody = """

				{
				    "title": "20% off",
				    "description": "20% off kitchen products until ",
				    "redeemed": false,
				    "expiryDate": "0001-01-01",
				    "timesUsed": 0,
				    "maxUsage": 1,
				    "userId": 3,
				    "role": "User"
				}

				""";
		
		// Add header
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		//Combine header and body
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
		
		ResponseEntity<String> responseEntity = template.exchange(GENERAL_VOUCHER_URL, HttpMethod.POST, httpEntity, String.class);
		
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		System.out.println(locationHeader);
		assertTrue(locationHeader.contains(GENERAL_VOUCHER_URL));
		
		String deleteHeader = locationHeader + "/delete";
		System.out.println(deleteHeader);

		
		// Check status code:
		
		ResponseEntity<String> deleteEntity = template.exchange(deleteHeader, HttpMethod.DELETE, httpEntity, String.class);
		System.out.println(deleteEntity.getStatusCode().toString());
		
		assertTrue(deleteEntity.getStatusCode().is2xxSuccessful());


//		org.springframework.web.client.ResourceAccessException: I/O error on DELETE request for "http://localhost:50176/users/1/vouchers/1602/delete": cannot retry due to server authentication, in streaming mode
//		Caused by: Caused by: java.net.HttpRetryException: cannot retry due to server authentication, in streaming mode

	}
	
	@Test
	void deleteVoucher_basic() throws JSONException {

		String requestBody = """

				{
				    "title": "20% off",
				    "description": "20% off kitchen products until ",
				    "redeemed": false,
				    "expiryDate": "0001-01-01",
				    "timesUsed": 0,
				    "maxUsage": 1,
				    "userId": 3,
				    "role": "User"
				}

				""";
		
		// Add header
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		//Combine header and body
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
		
		ResponseEntity<String> responseEntity = template.exchange(GENERAL_VOUCHER_URL, HttpMethod.POST, httpEntity, String.class);
		
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		System.out.println(locationHeader);
		assertTrue(locationHeader.contains(GENERAL_VOUCHER_URL));
		
		template.delete(locationHeader);
		
//		String deleteHeader = GENERAL_VOUCHER_URL + "/";
//		System.out.println(deleteHeader);
//
//		ResponseEntity<String> deleteEntity = template.exchange(deleteHeader, HttpMethod.DELETE, httpEntity, String.class);
//		System.out.println(deleteEntity.getStatusCode().toString());
//		
//		assertTrue(deleteEntity.getStatusCode().is2xxSuccessful());
	}

}
