package com.vipshop.microscope.job.hive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hive.HiveTemplate;

public class HiveFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(HiveFactory.class);
	
	public static HiveTemplate HIVE_TEMPLATE;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext-hive.xml", HiveFactory.class);
		context.registerShutdownHook();

		logger.info("init hive template bean");
		HIVE_TEMPLATE = context.getBean(HiveTemplate.class);
		
		context.close();
	}
	
}
