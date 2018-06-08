package net.yang.example.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.yang.core.entity.Example;
import net.yang.core.repository.ExampleRepository;
import net.yang.example.service.ExampleService;
import net.yang.example.vo.FormExVo;

@Service
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleRepository exampleRepository;
	
	@Override
	public List<FormExVo> searchExample(FormExVo vo) {
		// TODO Auto-generated method stub
		Example entity = new Example();
		entity.setId(vo.getId());
		return null;
	}

	@Override
	@Transactional
	public Long saveExample(FormExVo vo) {
		Example entity = new Example();
		BeanUtils.copyProperties(vo, entity);
		return this.exampleRepository.save(entity).getId();
	}

	@Override
	@Transactional
	public boolean deleteExample(Long id) {
		this.exampleRepository.deleteById(id);
		return true;
	}

}
