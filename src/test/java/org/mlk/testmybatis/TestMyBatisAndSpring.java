package org.mlk.testmybatis;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.ucoon.listener.OnlineCountListener;
import com.cn.ucoon.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestMyBatisAndSpring {
	private static Logger logger = Logger.getLogger(TestMyBatisAndSpring.class);
//	private ApplicationContext ac = null;
	@Resource
	private UserService userService = null;

//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

//	@Test
//	public void test1() {
//		User user = userService.getUserById(1);
//		// System.out.println(user.getUserName());
//		// logger.info("值："+user.getUserName());
//		logger.info(JSON.toJSONString(user));
//	}
	
	
	@Test
	public void test2(){
		
		System.out.println(OnlineCountListener.getMemberSum());
	}

}