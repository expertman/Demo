package com.example.biz;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class SpringBootBbsDemoApplicationTests {
	@Autowired
	private SqlSession sqlSession;

	@Test
	void test1() {
		assertNotNull(this.sqlSession);
	}
	
	@Test
	void test2() {
		Date today = (Date)this.sqlSession.selectOne("Board.currentDate");
		log.warn(today.toLocaleString());
	}
	

}
