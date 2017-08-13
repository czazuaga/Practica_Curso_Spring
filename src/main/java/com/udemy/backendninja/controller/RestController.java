package com.udemy.backendninja.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udemy.backendninja.model.ContactModel;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

	@GetMapping("/checkrest")
	private ResponseEntity<ContactModel> checkRest(){
		ContactModel cm = new ContactModel(2, "Miguel", "Brasero Perez", "689758746", "Sevilla");
		return new ResponseEntity<>(cm, HttpStatus.OK);
	}
	
}
