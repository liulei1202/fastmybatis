package com.myapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.myapp.dao.TUserMapper;
import com.myapp.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	TUserMapper tUserMapper;
	
	@GetMapping("/")
	@ResponseBody
	public String home(Locale locale, Model model,HttpServletRequest request) {
		return "hello";
	}

	@GetMapping("/selectByName")
	@ResponseBody
	public String selectByName() {
		TUser tUser = tUserMapper.selectByName("张三");
		TUser user = tUserMapper.getById(1);
		return tUser.toString() + user;
	}

}
