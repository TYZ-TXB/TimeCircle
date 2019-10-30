package cn.hmxhy.timecircle.controller;

import cn.hmxhy.timecircle.manager.ReturnJSONManager;
import cn.hmxhy.timecircle.pojo.UserDo;
import cn.hmxhy.timecircle.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;


	@RequestMapping("/toProfile")
	public String toProfile(Long uid, Model model) {
		UserDo userDo = userService.findOtherUserInfo(uid);
		if (userDo != null) {
			model.addAttribute("userInfo", ReturnJSONManager.getUserInfoJSON(userDo));
		}
		return "profile";
	}

	@RequestMapping("/getOtherUserInfo")
	@ResponseBody
	public JSON getOtherUserInfo(Long uid) {
		UserDo userDo = userService.findOtherUserInfo(uid);
		if (userDo != null) {
			return ReturnJSONManager.getSuccessStatus("", ReturnJSONManager.getUserInfoJSON(userDo));
		}
		return ReturnJSONManager.getErrorStatus();
	}

	@RequestMapping("/getMyUserInfo")
	@ResponseBody
	public JSON getMyUserInfo() {
		return ReturnJSONManager.getSuccessStatus("");
	}


	@RequestMapping({
			"/",
			"/toLogin"
	})
	public String toLogin() {
		return "/login";
	}

	@RequestMapping({
			"/toRegister"
	})
	public String toRegister() {
		return "/register";
	}

	@RequestMapping("/login")
	public String login(String email, String password, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		UserDo userDo = userService.userLogin(email, password, request);
		if (userDo == null) {
			modelAndView.addObject("error", "邮箱或者密码错误！");
			modelAndView.setViewName("redirect:/toLogin");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg","邮箱或者密码错误!");
			jsonObject.put("status","0");
			request.getSession().setAttribute("error", jsonObject);
			return "redirect:/toLogin";
		} else {
			modelAndView.setViewName("redirect:/toIndex");
			return "redirect:/toIndex";
		}
		//return modelAndView;
	}

	@PostMapping("/register")
	public String register(UserDo userDo,HttpServletRequest request) {
		System.out.println("初始值" + userDo.toString());
		userDo = userService.addUser(userDo);
		if (userDo == null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg","邮箱已被占用!");
			jsonObject.put("status","0");
			request.getSession().setAttribute("error", jsonObject);
			return "redirect:/toRegister";
		}
		return "redirect:/toLogin";
	}

	@RequestMapping("/exit")
	public String exit(HttpSession session) {
		session.invalidate();
		return "redirect:/toLogin";
	}
}
