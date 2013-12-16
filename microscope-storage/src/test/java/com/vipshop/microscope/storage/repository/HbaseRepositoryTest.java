package com.vipshop.microscope.storage.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.SpanMock;
import com.vipshop.microscope.storage.domain.AppTable;
import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.HbaseRepository;
import com.vipshop.microscope.thrift.gen.Span;

public class HbaseRepositoryTest {

	public static final Logger logger = LoggerFactory.getLogger(HbaseRepositoryTest.class);
	
	@Test(priority = 1)
	public void reinit() {
		HbaseRepository.drop();
		HbaseRepository.init();
	}
	
	@Test(priority = 2)
	public void save() {
		Span span = SpanMock.mockSpan();
		HbaseRepository.save(AppTable.build(span));
		HbaseRepository.save(TraceTable.build(span));
		HbaseRepository.save(span);
		
		List<Map<String, Object>> apps = HbaseRepository.findAll();
		for (Map<String, Object> map : apps) {
			Set<Entry<String, Object>> entry = map.entrySet();
			int size = 0;
			for (Entry<String, Object> entry2 : entry) {
				size++;
				if (size == 1) {
					Assert.assertEquals("app", entry2.getKey());
				}
				if (size == 2) {
					Assert.assertEquals("trace", entry2.getKey());
				}
			}
		}
		
		List<Span> spans = HbaseRepository.findSpanByTraceId("8053381312019065847");
		for (Span tmpspan : spans) {
			Assert.assertEquals("localhost", tmpspan.getAppIp());
		}
		
		List<TraceTable> tables = HbaseRepository.findByTraceId("8053381312019065847");
		for (TraceTable traceTable : tables) {
			Assert.assertEquals("appname", traceTable.getAppName());
		}
	}
	
}
