package net.yang.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.yang.example.interceptor.LoginStatusInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	MessageSource messageSource;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginStatusInterceptor()).excludePathPatterns("/css/**", "/js/**", "/webjars/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(
					"/css/**",
					"/js/**",
					"/webjars/**")
			.addResourceLocations(
					"classpath:/static/css/",
					"classpath:/static/js/",
					"classpath:/META-INF/resources/webjars/");
		
	}

	/**
	 * カスタムバリデーションエラーメッセージ追加
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}
	
	@Override
	public Validator getValidator() {
		return validator();
	}
}
