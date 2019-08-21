package com.lideyang.cms.service;

import java.util.List;

import com.lideyang.cms.domain.Article;
import com.lideyang.cms.domain.Special;

public interface SpecialService {

	List<Special> searchSpecial();

	List<Special> searchSpecialById();

}
