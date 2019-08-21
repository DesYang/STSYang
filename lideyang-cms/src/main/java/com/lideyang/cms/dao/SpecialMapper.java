package com.lideyang.cms.dao;

import java.util.List;

import com.lideyang.cms.domain.Special;

public interface SpecialMapper {

	List<Special> searchSpecial();

	List<Special> searchSpecialById();

}
