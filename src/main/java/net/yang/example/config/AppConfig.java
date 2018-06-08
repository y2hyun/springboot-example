package net.yang.example.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

import net.yang.core.CoreConfig;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@PropertySource("classpath:database.properties")
@Import({CoreConfig.class})
public class AppConfig {

	/**
	 * メッセージソース設定
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("i18n/messages");
		messageSource.setCacheSeconds(30);
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return messageSource;
	}
	
	/**
	 * thymeleaf-layout-dialect設定
	 * @return
	 */
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

}
