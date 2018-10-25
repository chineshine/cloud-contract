package c.s.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	private String ok = "ok";

	@GetMapping("/foo")
	public String foo() {
		return ok;
	}
	
	@GetMapping("/getUserinfo")
	public String userinfo(String username) {
		return ok;
	}
}
