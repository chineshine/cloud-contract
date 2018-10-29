package c.s.producer;

import org.junit.Before;

import c.s.producer.controller.FooController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class FooRestBase {
	

	@Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new FooController());
    }
}
