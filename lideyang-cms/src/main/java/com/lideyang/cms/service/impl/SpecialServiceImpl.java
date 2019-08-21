package com.lideyang.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lideyang.cms.dao.SpecialMapper;
import com.lideyang.cms.domain.Special;
import com.lideyang.cms.service.SpecialService;

@Service
public class SpecialServiceImpl implements SpecialService {

	@Resource
	private SpecialMapper mapper;
	@Override
	public List<Special> searchSpecial() {
		// TODO Auto-generated method stub
		return mapper.searchSpecial();
	}
	@Override
	public List<Special> searchSpecialById() {
		// TODO Auto-generated method stub
		return mapper.searchSpecialById();
	}

}
