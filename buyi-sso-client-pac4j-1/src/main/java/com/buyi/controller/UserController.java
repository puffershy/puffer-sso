package com.buyi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.buji.pac4j.subject.Pac4jPrincipal;

@Controller
public class UserController {

	/**
	 * 用户信息
	 *
	 * @author buyi
	 * @date 2017年12月23日下午11:50:26
	 * @since 1.0.0
	 * @return
	 */
	@GetMapping("/admin/user")
	public String user(HttpServletRequest request, Model model) {
		Pac4jPrincipal principal = (Pac4jPrincipal) request.getUserPrincipal();
		model.addAttribute("user", principal);
		return "admin/user";
	}

	@ResponseBody
	@GetMapping("/admin/user/detail")
	public Object detail(HttpServletRequest request, Model model) {
		// 用户详细信息
		return request.getUserPrincipal();
	}

}
