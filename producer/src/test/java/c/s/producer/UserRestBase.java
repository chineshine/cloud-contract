package c.s.producer;

import org.junit.Before;

import c.s.producer.controller.UserController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class UserRestBase {

	@Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new UserController());
    }
}
