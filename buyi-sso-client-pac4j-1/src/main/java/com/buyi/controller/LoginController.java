package com.buyi.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/admin/login")
	public String login() {
		return "admin/login";
	}

	@RequestMapping(value = { "/admin/welcome" }, method = RequestMethod.GET)
	public String welcome() {
		return "admin/welcome";
	}

	@GetMapping("/admin/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return redirect("admin/login");
	}

	/**
	 * 带参重定向
	 *
	 * @param path
	 * @return
	 */
	protected String redirect(String path) {
		return "redirect:" + path;
	}

	@GetMapping("/previlige/no")
	public String noPrems() {
		return "/previlige/no";
	}
}
