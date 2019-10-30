package cn.hmxhy.timecircle.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AllInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		JSONObject jsonObject = (JSONObject) request.getSession().getAttribute("error");
		if (jsonObject != null) {
			if ("0".equals(jsonObject.getString("status"))) {
				jsonObject.put("status", "1");
			} else {
				request.getSession().removeAttribute("error");
			}
		}
		return true;
	}
}
