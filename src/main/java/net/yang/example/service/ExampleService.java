package net.yang.example.service;

import java.util.List;

import net.yang.example.vo.FormExVo;

public interface ExampleService {

	/**
	 * 検索
	 * @param vo
	 * @return
	 */
	List<FormExVo> searchExample(FormExVo vo);
	
	/**
	 * 保存
	 * @param vo
	 * @return
	 */
	Long saveExample(FormExVo vo);
	
	/**
	 * 削除
	 * @param id
	 * @return
	 */
	boolean deleteExample(Long id);
	
}
