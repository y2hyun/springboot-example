package net.yang.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 認可設定
		http.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/webjars/**").permitAll()
			.antMatchers("/", "/register").permitAll()
			.anyRequest().authenticated()
			.and()
		// ログインページ、ログイン成功遷移先、ログイン失敗遷移先設定
			.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/")
			.failureUrl("/login-error").permitAll();

		// ログアウト設定
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // HTTP GET logoutリクエスト対策
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// userDetailsService設定
		// passwordEncoder設定
		auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
