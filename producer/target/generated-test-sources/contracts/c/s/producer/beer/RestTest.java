package c.s.producer.beer;

import c.s.producer.BeerRestBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import org.junit.Test;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;

public class RestTest extends BeerRestBase {

	@Test
	public void validate_shouldGrantABeerIfOldEnough() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"age\":20}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/check");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['status']").isEqualTo("OK");
	}

	@Test
	public void validate_shouldGrantABeerIfOldEnoughFromFile() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"age\":20}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/check");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['status']").isEqualTo("OK");
	}

	@Test
	public void validate_shouldRejectABeerIfTooYoung() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"age\":10}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/check");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
		// and:
			assertThat((Object) parsedJson.read("$.status")).isInstanceOf(java.lang.String.class);
	}

	@Test
	public void validate_shouldReturnStatsForAUser() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"name\":\"foo\"}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/stats");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json;charset=UTF-8");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['text']").isEqualTo("Dear foo thanks for your interested in drinking beer");
		// and:
			assertThat(parsedJson.read("$.quantity", String.class)).matches("-?(\\d*\\.\\d+|\\d+)");
	}

}
