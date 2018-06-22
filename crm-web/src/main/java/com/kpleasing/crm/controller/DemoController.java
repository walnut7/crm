package com.kpleasing.crm.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/demo")
public class DemoController {
	
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String doTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "test/main";
	}
	
	@RequestMapping(value = "yjzf/getDyn")
	public String toGetDyn() throws IOException {
		return "test/yjzf/getDyn/getDyn";
	}
	
	@RequestMapping(value = "yjzf/quickPay")
	public String toQuickPay() throws IOException {
		return "test/yjzf/quickPay/quickPay";
	}
	
	@RequestMapping(value = "yjzf/QPay02")
	public String toQPay02() throws IOException {
		return "test/yjzf/QPay02/QPay";
	}
	
	@RequestMapping(value = "yjzf/ind_auth")
	public String toIndAuth() throws IOException {
		return "test/yjzf/ind_auth/ind_auth";
	}
	
	@RequestMapping(value = "yjzf/ind_auth_verify")
	public String toIndAuthVerify() throws IOException {
		return "test/yjzf/ind_auth_verify/ind_auth_verify";
	}
	
	@RequestMapping(value = "yjzf/pciQuery")
	public String toPCIQuery() throws IOException {
		return "test/yjzf/pciQuery/pciQuery";
	}
	
	@RequestMapping(value = "yjzf/pciDelete")
	public String toPCIDelete() throws IOException {
		return "test/yjzf/pciDelete/pciDelete";
	}
	
	@RequestMapping(value = "yjzf/cxjylsbw")
	public String toCxjylsbw() throws IOException {
		return "test/yjzf/cxjylsbw/index";
	}
	
	
	@RequestMapping(value = "menu")
	public @ResponseBody String getMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "[{\"id\":\"1\",\"name\":\"测试页面一\",\"url\":\"/crm-web/demo/page1\"},{\"id\":\"2\",\"name\":\"测试页面二\",\"url\":\"/crm-web/demo/page2\"},{\"id\":\"3\",\"name\":\"测试页面三\",\"url\":\"/crm-web/demo/page3\"}]";
	}
	
	@RequestMapping(value = "page1")
	public String toPage1() throws IOException {
		return "test/page1";
	}
	
	@RequestMapping(value = "page2")
	public String toPage2() throws IOException {
		return "test/page2";
	}
	
	@RequestMapping(value = "page3")
	public String toPage3() throws IOException {
		return "test/page3";
	}
	
	@RequestMapping(value = "page4")
	public String toPage4() throws IOException {
		return "test/page4";
	}
	
}
