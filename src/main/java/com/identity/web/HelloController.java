package com.identity.web;


import java.math.BigInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.identity.domain.User;
import com.identity.service.IdentityService;



@Controller
public class HelloController {
	
	@Resource(name="identityService")
	private IdentityService service;
	
	@RequestMapping(value="/index")
	public String index(Model model) {
	String contractAddress=service.getConstractAddress();
		BigInteger num=service.getNumRegister();
		model.addAttribute("contractAddress",contractAddress);
		model.addAttribute("numRegisters", num);
		return "index";
	}
	@RequestMapping("/publicKey")
	public @ResponseBody boolean index_log(User u,HttpSession session) {
		String publicKey=u.getPublicKey();
		session.setAttribute("publicKey", publicKey);
		boolean isPublicKey=service.isPublicKey(publicKey);
		return isPublicKey;
		
	}
	@RequestMapping("/to_register")
	public String to_register() {
		return "register";
		
	}
	@RequestMapping("/to_login")
	public String to_login() {
		return "login";
		
	}
	
	
	@RequestMapping("/register")
	public @ResponseBody boolean register(User u) {
		boolean isRegister=service.register(u);
		return isRegister;
		
	}
	@RequestMapping("/login")
	public String login(User u) {
		boolean isLogin=service.login(u);
		if(isLogin) {
			if(u.getCharacter().equals("admin")) {
				return "redirect:/admin";
			}
			if(u.getCharacter().equals("user")) {
				return "redirect:/user";
			}
		}
		return "logError";
	}
	
	@RequestMapping("/admin")
	public String to_admin() {
		return "admin";
		
	}
	@RequestMapping("/user")
	public String to_user() {
		return "user";
		
	}
	
	@RequestMapping("/to_money")
	public String money(HttpSession session,Model model) {
		BigInteger balance=service.getWallet(session.getAttribute("publicKey").toString());
		model.addAttribute("balance", balance);
		return "money";
	}
	
	@RequestMapping("/logout")
	public @ResponseBody boolean logout() {
		return service.logout();
	}
	

}
