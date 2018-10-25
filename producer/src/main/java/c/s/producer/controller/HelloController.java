package c.s.producer.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import c.s.producer.model.Person;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@PostMapping(value = "/check",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String if_perosn_wear_skirt(@RequestBody Person person) {
		if(("female").equals(person.sex)) {
			return "ok";
		}
		return "not_ok";
	}
}
