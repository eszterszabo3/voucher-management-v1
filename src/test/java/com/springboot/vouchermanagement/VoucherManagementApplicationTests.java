package com.springboot.vouchermanagement;

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
class VoucherManagementApplicationTests {
	
	private static String GENERAL_VOUCHER_URL = "/users/1/vouchers";
	
	@Autowired
	private TestRestTemplate template;

	@Test
	void getAllVouchers() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/vouchers", String.class);

		// System.out.println(responseEntity.getStatusCode());
		// 200 OK
		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// System.out.println(responseEntity.getHeaders());
		// [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Tue, 09
		// Aug 2022 10:53:29 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		// System.out.println(responseEntity.getBody());
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
	}

	@Test
	void getVouchersByRole_Admin() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/Admin/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		// System.out.println(responseEntity.getBody());

		String expectedResponse = """

				[{"id":2,"role":"Admin"},
				{"id":3,"role":"Admin"},
				{"id":5,"role":"Admin"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void getVouchersByRole_User() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/User/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		// System.out.println(responseEntity.getBody());

		String expectedResponse = """

				[{"id":1,"role":"User"},
				{"id":4,"role":"User"},
				{"id":6,"role":"User"},
				{"id":7,"role":"User"},
				{"id":8,"role":"User"},
				{"id":9,"role":"User"}]

				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void getVouchersByUserId_1() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/1/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		// System.out.println(responseEntity.getBody());

		String expectedResponse = """

				[{"id":2,"userId":1,"role":"Admin"},
				{"id":3,"userId":1,"role":"Admin"},
				{"id":5,"userId":1,"role":"Admin"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void getVouchersByUserId_2() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/2/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		// System.out.println(responseEntity.getBody());

		String expectedResponse = """

				[{"id":1,"userId":2,"role":"User"},
				{"id":4,"userId":2,"role":"User"},
				{"id":6,"userId":2,"role":"User"},
				{"id":7,"userId":2,"role":"User"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void getVouchersByUserId_3() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/3/vouchers", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		// System.out.println(responseEntity.getBody());

		String expectedResponse = """

				[{"id":8,"userId":3,"role":"User"},
				{"id":9,"userId":3,"role":"User"}]


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}
	
	@Test
	void getVouchersById_user3_id8() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/3/vouchers/8", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		// System.out.println(responseEntity.getBody());

		String expectedResponse = """

				{"id":8,"userId":3,"role":"User"}


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}
	
	@Test
	void getVouchersById_user3_id9() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity("/users/3/vouchers/9", String.class);

		// Check status code:

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		// Check content-type:

		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		// Check content:
		//System.out.println(responseEntity.getBody());

		String expectedResponse = """

				{"id":9,"userId":3,"role":"User"}


				""";

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

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

	}

}
