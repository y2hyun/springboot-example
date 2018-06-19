package net.yang.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import net.yang.example.service.AccountService;
import net.yang.example.vo.RegistrationVo;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/login")
	public String index(Model model) {
		model.addAttribute("is-error", false);
		return "login";
	}
	
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("isError", true);
		return "login";
	}
	
	@ModelAttribute("registrationVo")
	public RegistrationVo registrationVo() {
		return new RegistrationVo();
	}
	
	@PostMapping("/register")
	public String register(HttpServletRequest request, @Valid RegistrationVo vo, BindingResult bindingResult, Model model) {
		
		this.additionalValidate(vo, bindingResult);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("showRegister", true);
			return "login";
		}
		
		UserDetails user = this.accountService.createAccount(vo);
		this.accountService.doAuthenticationWithoutPassword(user, request);
		log.debug("account created : {}", user.getUsername());
		return "top";
	}
	
	/**
	 * 検証
	 * @param vo
	 * @param bindingResult
	 */
	private void additionalValidate(RegistrationVo vo, BindingResult bindingResult) {
		// デフォルトバリデーションエラーがある場合、何もしない。
		if(bindingResult.hasErrors()) {
			return;
		}
		
		// ユーザー重複チェック
		if(StringUtils.isNotBlank(vo.getId()) && this.accountService.isExistsUser(vo.getId())) {
			String existsIdMsg = messageSource.getMessage("validation.custom.message.existsId", null, LocaleContextHolder.getLocale());
			FieldError error = new FieldError("id", "id", existsIdMsg);
			bindingResult.addError(error);
			return;
		}
		
	}
}
