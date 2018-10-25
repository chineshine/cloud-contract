package c.s.producer;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import c.s.producer.controller.HelloController;
import c.s.producer.controller.UserController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProducerBase.Config.class)
public class ProducerBase {

	@Autowired WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}
	
	@Configuration
	@EnableAutoConfiguration
	public class Config {
		@Bean HelloController helloController() {
			return new HelloController();
		}
		
		@Bean UserController userController() {
			return new UserController();
		}
	}

}
