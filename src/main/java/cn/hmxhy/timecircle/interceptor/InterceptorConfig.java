package cn.hmxhy.timecircle.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
	@Autowired
	private UserInterceptor userInterceptor;
	@Autowired
	private AllInterceptor allInterceptor;
	List<String> staticPath;

	public InterceptorConfig() {
		staticPath = new ArrayList<>();
		staticPath.add("/bootstrap-3.3.7-dist/**");
		staticPath.add("/bootstrap-4.3.1-dist/**");
		staticPath.add("/css/**");
		staticPath.add("/headPortrait/**");
		staticPath.add("/Hplus/**");
		staticPath.add("/img/**");
		staticPath.add("/jquery/**");
		staticPath.add("/js/**");
		staticPath.add("/users/**");
		//默认资源路径，添加静态资源路径时排除，单独配置，永远放在最后
		staticPath.add("/static/**");
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		List<String> userExcludePath = new ArrayList<>();
		userExcludePath.add("/toLogin");
		userExcludePath.add("/toRegister");
		userExcludePath.add("/login");
		userExcludePath.add("/register");
		userExcludePath.add("/exit");
		userExcludePath.add("/error/**");
		userExcludePath.addAll(staticPath);
		registry.addInterceptor(userInterceptor).addPathPatterns("/**").excludePathPatterns(userExcludePath);
		registry.addInterceptor(allInterceptor).addPathPatterns("/**");
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		for (int i = 0; i < staticPath.size() - 1; i++) {
			registry.addResourceHandler(staticPath.get(i)).addResourceLocations("classpath:/static" + staticPath.get(i).substring(0, staticPath.get(i).lastIndexOf("/") + 1));
		}
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
	}
}
