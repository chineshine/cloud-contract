package c.s.consumer.mock;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.REMOTE, ids = "${spring.cloud.contract.stubs.ids}",
	repositoryRoot="${spring.cloud.contract.stubs.git.url}",properties= {"git.username=${spring.cloud.contract.stubs.git.username}","git.password=${spring.cloud.contract.stubs.git.password}"})
public class FeignGitTest {

	@StubRunnerPort("producer-git")
	int producerPort;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private URI getUri(String path) {
		return URI.create("http://localhost:" + producerPort + "/" + path);
	}

	@Test
	public void test1() {
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(RequestEntity.get(getUri("foo")).build(),
				String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
