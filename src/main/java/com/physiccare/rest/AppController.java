package com.physiccare.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class AppController {

	@GetMapping
	public String redirect() {
		return "redirect:/static/index.html";
	}
}
