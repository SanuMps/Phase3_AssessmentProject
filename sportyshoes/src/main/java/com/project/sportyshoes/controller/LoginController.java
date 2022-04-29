package com.project.sportyshoes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.sportyshoes.global.GlobalData;
import com.project.sportyshoes.model.Role;
import com.project.sportyshoes.model.User;
import com.project.sportyshoes.repository.RoleRepository;
import com.project.sportyshoes.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}

	@PostMapping("/login")
	public String loginPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		System.out.println("loginPost::" + user.getEmail() + user.getPassword());
		if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
			System.out.println("1"); // check kro sahi username password daala hai ki ni
			User user2 = userRepository.findUserByEmail(user.getEmail()).get();
			System.out.println("1.5" + user2.getPassword());
			if (bCryptPasswordEncoder.matches(user.getPassword(), user2.getPassword())) {
				System.out.println("2");

				if (user.getEmail().equals("sanu@gmail.com") && user.getPassword().equals("1234")) {
					System.out.println("3");
					request.login(user.getEmail(), user.getPassword());
					return "adminHome";
				} else {
					System.out.println("4");
					request.login(user.getEmail(), user.getPassword());
					return "redirect:/";
				}
			}
		}
		return "redirect:login?error";
	}

	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}

	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {

		String password = user.getPassword();

		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<Role>();
		System.out.println("roles::" + user.getEmail() + user.getPassword());
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";

	}

}