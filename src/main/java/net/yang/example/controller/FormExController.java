package net.yang.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.yang.example.service.ExampleService;
import net.yang.example.vo.FormExVo;

@Slf4j
@Controller
@RequestMapping(value = "/formex")
public class FormExController {

	@Autowired
	private ExampleService exampleService;
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("formex", new FormExVo());
		return "form_input";
	}
	
	@PostMapping("submit")
	public String formexSubmit(@Valid @ModelAttribute("formex") FormExVo vo, BindingResult bindingResult) {
		log.debug("FormExVo : {}", vo);
		if(bindingResult.hasErrors()) {
			return "form_input";
		}
		
		Long id = this.exampleService.saveExample(vo);
		log.debug("FormExVo : {}", vo);
		
		return "form_result";
	}
}
