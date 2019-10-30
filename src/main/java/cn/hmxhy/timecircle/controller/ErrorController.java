package cn.hmxhy.timecircle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	@RequestMapping("/notFound")
	public String notFound() {
		return "404";
	}
}
