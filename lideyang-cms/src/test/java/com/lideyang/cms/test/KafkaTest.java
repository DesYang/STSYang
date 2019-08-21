package com.lideyang.cms.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.kafka.core.KafkaTemplate;

import com.lideyang.cms.BaseTestCase;

public class KafkaTest extends BaseTestCase{

	@Resource
	private KafkaTemplate kafkaTemplate;
	
	@Test
	public void testKafka() {
		kafkaTemplate.send("first","test_cms","hello cms");
	}
}
