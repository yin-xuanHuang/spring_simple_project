package com.spring5demo.demo.controller;

import java.security.Principal;
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
import com.spring5demo.demo.dto.UserEmail;
import com.spring5demo.demo.dto.UserPassword;
import com.spring5demo.demo.dto.UserRegister;
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
		model.addAttribute("user", new UserRegister());
		return "registration";
	}

	@PostMapping("/registration")
	public String newMessage(@ModelAttribute @Valid UserRegister user,
			@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, HttpServletRequest request,
			Errors errors, Model model) {

		String ip = request.getRemoteAddr();
		List<String> captchaVerifyMessage = captchaService.verifyRecaptcha(ip, recaptchaResponse);

		if (captchaVerifyMessage != null) {
			model.addAttribute("captcha_errors", captchaVerifyMessage);
			model.addAttribute("usr", user);
			return "registration";
		}

		if (errors.hasErrors()) {
			return "registration";
		}

		if (!user.getPassword().equals(user.getMatchingPassword())) {
			model.addAttribute("password", "Password and confirm password don't match.");
			model.addAttribute("user", user);
			return "registration";
		}

		User userDao = new User();
		userDao.setUsername(user.getUsername());
		userDao.setPassword(user.getPassword());
		userDao.setEmail(user.getEmail());

		userService.save(userDao);
		userService.sendOrderConfirmation(userDao);
		return "redirect:/todos";
	}

	@GetMapping("/forgetPassword")
	public String forgetPassword(Model model) {
		model.addAttribute("user", new UserEmail());
		return "forgetPassword";
	}

	@PostMapping("/forgetPassword")
	public String forgetPassword(@ModelAttribute @Valid UserEmail user,
			@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, HttpServletRequest request,
			Errors errors, Model model) {

		String ip = request.getRemoteAddr();
		List<String> captchaVerifyMessage = captchaService.verifyRecaptcha(ip, recaptchaResponse);

		if (captchaVerifyMessage != null) {
			model.addAttribute("captcha_errors", captchaVerifyMessage);
			model.addAttribute("usr", user);
			return "registration";
		}

		if (errors.hasErrors()) {
			return "registration";
		}

		User checkUser = userService.findOneByEmail(user.getEmail());

		if (checkUser == null) {
			model.addAttribute("email_error", user.getEmail() + "doesn't exist.");
			return "registratin";
		}

		userService.sendPasswordReset(checkUser);

		model.addAttribute("email_password_reset", "Email has sent. Please check your email.");

		return "login";
	}

	@GetMapping("/resetPassword")
	public String resetPassword(Model model) {
		model.addAttribute("user", new UserPassword());
		return "resetPassword";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute @Valid UserPassword user, Errors errors, Model model, Principal principal) {

		if (errors.hasErrors()) {
			return "resetPassword";
		}
		
		if (!user.getPassword().equals(user.getMatchingPassword())) {
			model.addAttribute("password", "Password and confirm password don't match.");
			model.addAttribute("user", user);
			return "resetPassword";
		}

		User checkUser = userService.findOneByUsername(principal.getName());

		if (!userService.passwordReset(checkUser, user)) {
			model.addAttribute("password", "Old password is wrong.");
			model.addAttribute("user", user);
			return "resetPassword";
		}
		
		model.addAttribute("username", checkUser.getUsername());
		
		return "password-change-success";
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
