package edu.fjnu.oa.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	//测试sessionFactory
	@Test
	public void testSessionFactory(){
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}
	
	@Test
	public void test(){
		TestService ts = (TestService) ac.getBean("testService");
		ts.saveUser();
	}
}
