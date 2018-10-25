package c.s.producer;

import org.junit.Before;

import c.s.producer.controller.HelloController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class SkirtRestBase {

	@Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new HelloController());
    }
}
