/**
 * 
 */
package c.s.producer;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 *
 * @author chineshine
 * @date 2018年10月23日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MvcTest {
	@Autowired WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		//remove::start[]
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		//remove::end[]
	}
}
