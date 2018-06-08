package net.yang.example.vo;

import java.util.Calendar;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class FormExVo {

	private Long id;
	
	@NotEmpty
	private String name;
	
	@Size(max=200)
	private String comment;
	
	private Calendar createDatetime;
	
	private Calendar updateDatetime;
}
