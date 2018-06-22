package com.kpleasing.crm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kpleasing.crm.ejb.pojo.Menu;
import com.kpleasing.crm.ejb.pojo.UserProfile;
import com.kpleasing.crm.ejb.util.NavigationUtil;
import com.kpleasing.crm.service.EjbService;

/**
 * 
 * @author howard.huang
 *
 */
@Controller
@RequestMapping(value = "")
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private EjbService ejbService;

	/**
	 * 用戶登錄
	 * 
	 * @param loginUser
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ModelAndView LoginPage(UserProfile loginUser, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
		try {
			if (null == loginUser || StringUtils.isEmpty(loginUser.getUsername())
					|| StringUtils.isEmpty(loginUser.getPassword())) {
				if (null == session) {
					return new ModelAndView("login");
				} else {
					loginUser = (UserProfile) session.getAttribute("LOGIN_USER");
					if (null == loginUser || StringUtils.isEmpty(loginUser.getUsername())
							|| StringUtils.isEmpty(loginUser.getPassword())) {
						return new ModelAndView("login");
					}
				}
				return new ModelAndView("login");
			}

			UserProfile user = ejbService.getLoginServ().getLoginUserProfile(loginUser);

			if (null != user && user.getLoginStatus().equals("SUCCESS")) {
				session.setAttribute("LOGIN_USER", user);
				return new ModelAndView("redirect:main");
			} else {
				logger.info(user.getLoginInfo());
				model.put("loginInfo", user.getLoginInfo());
			}
		} catch (Exception e) {
			logger.error("用户登录异常：" + e.getMessage(), e);
			model.put("loginInfo", "用户登录异常");
		}
		return new ModelAndView("login", model);
	}

	
	@RequestMapping(value = { "main" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String goHomePage(HttpServletRequest request) {
		return "main";
	}

	/**
	 * 导航菜单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/menu/navigation", method = RequestMethod.POST)
	public @ResponseBody String getNavigationTree(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (null != session) {
				UserProfile loginUser = (UserProfile) session.getAttribute("LOGIN_USER");
				logger.info(loginUser.getUsername());
				logger.info(loginUser.getPassword());

				NavigationUtil navigation = NavigationUtil.getInstance();
				List<Menu> menus = navigation.getMenus();

				StringBuilder jsonTree = new StringBuilder();
				jsonTree.append("[").append(navigation.getJsonMenuTree(menus)).append("]");

				return jsonTree.toString();
			}
		} catch (Exception e) {
			logger.error("菜单异常:" + e.getMessage(), e);
		}
		return "[]";
	}
}
