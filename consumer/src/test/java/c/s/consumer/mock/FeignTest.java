/**
 * 
 */
package c.s.consumer.mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import c.s.consumer.model.Person;


/**
 *
 * @author chineshine
 * @date 2018年10月22日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.CLASSPATH, ids = "c.s.contract:producer")
public class FeignTest {

	@StubRunnerPort("producer")
	int producerPort;
	
	@LocalServerPort
	int port;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private URI getUri(String path) {
		return URI.create("http://localhost:" + producerPort + "/"+path);
	}
	
	@Test
	// post
	public void test1() {
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(RequestEntity.post(URI.create("http://localhost:" + producerPort + "/check")).
				contentType(MediaType.APPLICATION_JSON).
				body(new Person("mary", "female")), String.class);
		System.out.println(responseEntity.getBody());
	}
	
	@Test
	//get  无参
	public void test2() {
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(RequestEntity.get(getUri("foo")).build(), String.class);
		System.out.println(responseEntity.getBody());
	}
	// get 传参
	@Test public void test3() {
		Map<String, Object> map = new HashMap<>();
		map.put("username", "zhangsan");
		HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<Map<String,Object>>(map);
		URI uri = UriComponentsBuilder.fromUri(getUri("getUserinfo")).queryParam("username", "zhangsan").build().toUri();
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		System.out.println(responseEntity.getBody());
	}
	
	//get 传参  第二种写法
	@Test public void test5() {
		Map<String, Object> map =new HashMap<>();
		map.put("username", "zhangsan");
		String uri = "http://localhost:" + producerPort + "/getUserinfo?username={username}";
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(uri, HttpMethod.GET, null, String.class, map);
		System.out.println(responseEntity.getBody());
	}
	
	@Test
	//post 另一种写法
	public void test4() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Person> httpEntity = new HttpEntity<Person>(new Person("marcin", "male"),headers);
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(getUri("check"), HttpMethod.POST, httpEntity, String.class);
		System.out.println(responseEntity.getBody());
	}
	
	// get 第三种写法
	@Test public void test6() throws URISyntaxException {
		URIBuilder builder = new URIBuilder(this.getUri("getUserinfo"));
		builder.addParameter("username", "zhangsan");
		URI uri = builder.build();
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(uri, HttpMethod.GET, null, String.class);
		System.out.println(responseEntity.getBody());
	}
}
