package com.identity.web;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.protocol.core.methods.response.EthBlock;

import com.identity.domain.Block;
import com.identity.domain.Event;
import com.identity.domain.Transfer;
import com.identity.domain.User;
import com.identity.service.IdentityService;



@Controller
public class HelloController {
	
	@Resource(name="identityService")
	private IdentityService service;
	
	@RequestMapping(value="/index")
	public String index(Model model,HttpSession session) {
		String contractAddress=service.getConstractAddress();
		BigInteger num=service.getNumRegister();
		model.addAttribute("contractAddress",contractAddress);
		model.addAttribute("numRegisters", num);
		return "index";
	}
	@RequestMapping("/publicKey")
	public @ResponseBody boolean index_log(User u,HttpSession session) {
		String publicKey=u.getPublickey();
		session.setAttribute("publicKey", publicKey);
		boolean isPublicKey=service.isPublicKey(publicKey);
		try {
			//service.EventFilter();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	
	/*@RequestMapping("/register")
	public @ResponseBody boolean register(User u) {
		boolean isRegister=service.register(u);
		return isRegister;
		
	}*/
	
	@RequestMapping("/register")
	public String register(User u,Model model) {
		boolean isRegister=service.register(u);
		if(isRegister) {
			model.addAttribute("registerState","注册成功，");
		}else {
		model.addAttribute("registerState", "已经注册过，请登录，");
	    }
		return "changePage";
	}
	
	
	@RequestMapping("/login")
	public String login(User u) {
		boolean isLogin=service.login(u);
		if(isLogin) {
			if(u.getRole().equals("admin")) {
				return "redirect:/admin";
			}
			if(u.getRole().equals("user")) {
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
	
	@RequestMapping("/to_changeInfo")
	public String toChangeInfo() {
		return "changeInfo";
	}
	
	@RequestMapping("/changeInfo")//修改密码，修改成功重新登录，失败返回登录页
	public String changeInfo(User u,Model model,HttpServletResponse response) {
		boolean isChange=service.changeInfo(u);
		if(isChange) {
			model.addAttribute("changeState", "修改成功");
			return "changePage";
		}else {
			return "returnPage";
	    }
	}
	
	@RequestMapping("/to_blockInfo")
	public String toBlockInfo(HttpSession session) {
		List<Event> blockInfo= service.getBlock();
		
		session.setAttribute("blockInfo", blockInfo);
		return "blockInfo";
	}
	
	@RequestMapping("/to_regUsers")
	public String toRegUsers(HttpSession session) {
		session.setAttribute("regUser", service.getRegUsers());
		return "redirect:/regUsers";
	}
	@RequestMapping("/regUsers")
	public String toRegUsers() {
		return "regUsers";
	}
	
	@RequestMapping("/transfer")
	public @ResponseBody boolean transfer(Transfer t) {
		BigInteger count=new BigInteger(t.getCount());
	    boolean isTransfer=service.transfer(t.getTo(), count);
	    return isTransfer;
	}
	
	@RequestMapping("/searchBlockInfo")
	public String searchBlockInfo(Event t,HttpSession session) {
		session.setAttribute("blockInfo", service.serachBlock(t));
		return "blockInfo";
		
	}
	@RequestMapping("/check")
	public String toCheck(HttpSession session) {
		HashMap<String,List<Event>> r=service.getCheck();
		session.setAttribute("check_out", r.get("out"));
		session.setAttribute("check_in", r.get("in"));
		return "/check";
	}
	
	@RequestMapping("/selectRegUsers")
	public String selectRegUsers(User u,HttpSession session) {
		System.out.println(u.getPublickey());
		session.setAttribute("regUser", service.selectByRegUser(u));
		return "regUsers";
		
	}
	
	@RequestMapping("/selectCheck")
	public String selectCheck(Event e,HttpSession session) {
		HashMap<String,List<Event>> r=service.selectByCheck(e);
		session.setAttribute("check_out", r.get("out"));
		session.setAttribute("check_in", r.get("in"));
		return "check";
		
	}
	

  
		
    
	
	

}
