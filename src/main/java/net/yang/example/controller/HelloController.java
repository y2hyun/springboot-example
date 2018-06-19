package net.yang.example.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/")
	public String index(Model model, Locale locale) {
		String hello = this.messageSource.getMessage("hello", null, locale);
		model.addAttribute("message", hello);
		return "top";
	}
}
