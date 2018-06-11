package com.myapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.common.bean.ResultBean;
import com.myapp.common.util.RequestUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@GetMapping("/")
	@ResponseBody
	public ResultBean home(Locale locale, Model model,HttpServletRequest request) {
		ResultBean bean = new ResultBean();
		bean.setMsg("hello,your ip:" + RequestUtil.getClientIP(request));
		return bean;
	}
	
}
