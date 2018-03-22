package com.spring5demo.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring5demo.demo.domain.User;
import com.spring5demo.demo.service.RecaptchaService;
import com.spring5demo.demo.service.UserService;

@Controller
public class UserController {

	private UserService userService;

	private RecaptchaService captchaService;

	@Autowired
	public UserController(UserService userService, RecaptchaService captchaService) {
		this.userService = userService;
		this.captchaService = captchaService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout-success")
	public String logoutSuccess() {
		return "logout-success";
	}

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String newMessage(@ModelAttribute @Valid User user,
			@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, HttpServletRequest request,
			Errors errors, Model model) {

		String ip = request.getRemoteAddr();
		List<String> captchaVerifyMessage = 
			captchaService.verifyRecaptcha(ip, recaptchaResponse);
		
		if ( captchaVerifyMessage != null) {
			model.addAttribute("captcha_errors", captchaVerifyMessage);
			return "registration";
		}
		
		if (errors.hasErrors()) {
			return "registration";
		}
		
		userService.save(user);
		userService.sendOrderConfirmation(user);
		return "redirect:/todos";
	}

	@GetMapping("/activate")
	public String activateAccount(@RequestParam(value = "key") String key, Model model) {

		User user = userService.activateRegistration(key);

		if (user != null) {
			model.addAttribute("username", user.getUsername());
			return "activate-success";
		}

		return "redirect:/todos";

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// We don't want to bind the id, enable,and authorities fields as we control
		// them in this controller and service instead.
		binder.setDisallowedFields("id", "enable", "authorities");
	}

}
