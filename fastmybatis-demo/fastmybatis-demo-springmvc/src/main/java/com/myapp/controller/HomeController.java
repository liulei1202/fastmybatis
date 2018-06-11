package com.myapp.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.common.bean.ResultBean;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@GetMapping("/")
	@ResponseBody
	public ResultBean home(Locale locale, Model model) {
		ResultBean bean = new ResultBean();
		bean.setMsg("hello");
		return bean;
	}
	
}
