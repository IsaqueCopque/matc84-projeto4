package com.matc84demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("teste")
public class TestController {
	@GetMapping("/testar")
	public ResponseEntity<String> testar() {
		System.out.println("---> Bateu teste");
		
		String result = "Passou";
		
		return ResponseEntity.ok(result);
	}
}
