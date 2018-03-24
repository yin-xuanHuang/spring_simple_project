package com.spring5demo.demo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String newMessage(@ModelAttribute("user") @Valid UserRegister user, BindingResult result,
			@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, HttpServletRequest request,
			Model model) {

		String ip = request.getRemoteAddr();
		List<String> captchaVerifyMessage = captchaService.verifyRecaptcha(ip, recaptchaResponse);

		if (captchaVerifyMessage != null) {
			model.addAttribute("captcha_errors", captchaVerifyMessage);
			model.addAttribute("user", user);
			return "registration";
		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "registration";
		}

		if (!user.getPassword().equals(user.getMatchingPassword())) {
			model.addAttribute("message", "Password and confirm password don't match.");
			model.addAttribute("user", user);
			return "registration";
		}

		User userDao = new User();
		userDao.setUsername(user.getUsername());
		userDao.setPassword(user.getPassword());
		userDao.setEmail(user.getEmail());

		int status = userService.save(userDao);
		if (status == 1) {
			model.addAttribute("message", "The username already exist.");
			model.addAttribute("user", user);
			return "registration";
		} else if (status == 2) {
			model.addAttribute("message", "The email already exist.");
			model.addAttribute("user", user);
			return "registration";
		}

		userService.sendOrderConfirmation(userDao);
		return "redirect:/todos";
	}

	@GetMapping("/forgetPassword")
	public String forgetPassword(Model model) {
		model.addAttribute("user", new UserEmail());
		return "forget-password";
	}

	@PostMapping("/forgetPassword")
	public String forgetPassword(@ModelAttribute("user") @Valid UserEmail user, BindingResult result,
			@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, HttpServletRequest request,
			Model model) {

		String ip = request.getRemoteAddr();
		List<String> captchaVerifyMessage = captchaService.verifyRecaptcha(ip, recaptchaResponse);

		if (captchaVerifyMessage != null) {
			model.addAttribute("captcha_errors", captchaVerifyMessage);
			model.addAttribute("user", user);
			return "forget-password";
		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "forget-password";
		}

		User checkUser = userService.findOneByEmail(user.getEmail());

		if (checkUser == null) {
			model.addAttribute("email_error", user.getEmail() + " doesn't exist.");
			model.addAttribute("user", user);
			return "forget-password";
		}

		userService.sendPasswordReset(checkUser);

		model.addAttribute("email_password_reset", "Email has sent. Please check your email.");

		return "login";
	}

	@GetMapping("/resetPassword")
	public String resetPassword(Model model) {
		model.addAttribute("user", new UserPassword());
		return "reset-password";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("user") @Valid UserPassword user, BindingResult result, Model model,
			Principal principal) {

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "reset-password";
		}

		if (!user.getPassword().equals(user.getMatchingPassword())) {
			model.addAttribute("password", "Password and confirm password don't match.");
			model.addAttribute("user", user);
			return "reset-password";
		}

		User checkUser = userService.findOneByUsername(principal.getName());

		if (!userService.passwordReset(checkUser, user)) {
			model.addAttribute("password", "Old password is wrong.");
			model.addAttribute("user", user);
			return "reset-password";
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

		return "forbidden";

	}
}
