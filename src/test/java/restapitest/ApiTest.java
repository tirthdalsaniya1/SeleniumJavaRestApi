package restapitest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.ConfigReader;
import io.restassured.response.Response;
import testbase.BasePage;

public class ApiTest extends BasePage {

	@Test
	public void verifyCurrentPriceGetRequest() {
		
		//Fetch GET CALL URL from the config.xml file
		String apiURL = ConfigReader.getConfig("environment." + BasePage.environment + ".restURL");
		
		//Hit Get api rest request
		Response getApiResponse = given().get(apiURL).then().log().all().extract().response();

		// Get the JSON response as a string
		String jsonResponse = getApiResponse.asString();

		ObjectMapper mapper = new ObjectMapper();
		try {

			// Parse the JSON response into a Map
			Map<String, Object> responseMap = mapper.readValue(jsonResponse, Map.class);

			// Extract the "bpi" object from the response
			Map<String, Object> bpiMap = (Map<String, Object>) responseMap.get("bpi");

			// Assert that the bpi map contains keys for USD, GBP, and EUR
			assertTrue(bpiMap.containsKey("USD"), "USD key is missing in the response");
			assertTrue(bpiMap.containsKey("GBP"), "GBP key is missing in the response");
			assertTrue(bpiMap.containsKey("EUR"), "EUR key is missing in the response");

			// Extract the GBP object and validate its description
			Map<String, Object> gbpDetails = (Map<String, Object>) bpiMap.get("GBP");
			String gbpDescription = (String) gbpDetails.get("description");
			assertEquals(gbpDescription, "British Pound Sterling", "GBP description is incorrect");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to parse JSON response: " + e.getMessage());
		}
	}

}
