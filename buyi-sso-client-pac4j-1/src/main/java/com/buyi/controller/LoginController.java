package com.buyi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@GetMapping("/admin/login")
	public String login() {

		return "admin/login";
	}

	@RequestMapping(value = { "/admin/welcome" }, method = RequestMethod.GET)
	public String welcome() {

		return "admin/welcome";
	}

	@RequestMapping(value = { "/admin/logout" }, method = RequestMethod.GET)
	public String logout() {
		return "admin/logout";
	}
}
