package c.s.producer.controller;

import org.junit.Before;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class HelloControllerBase {

	@Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new HelloController());
    }
}
