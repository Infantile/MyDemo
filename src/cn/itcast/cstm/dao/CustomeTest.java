package cn.itcast.cstm.dao;

import org.junit.Test;

import cn.itcast.cstm.daomain.Custome;
import cn.itcast.utils.CommonUtils;

public class CustomeTest {
  
	@Test
	public void fun(){
		CustomeDao customeDao = new CustomeDao();
		for(int i = 0;i < 300;i++){
			Custome c = new Custome();
			c.setCid(CommonUtils.uuid());
			c.setCname("cstm_" + i);
			c.setBirthday("2017-08-22");
			c.setGender(i%2==0?"男":"女");
			c.setCellphone("139"+"i");
			c.setEmail("cstm" + i + "@163.com");
			c.setDescription("我是客户_" + i);
			customeDao.add(c);
		}
		
		
	}
	@Test
	public void fun1(){
		Custome c = new Custome();
		int i = 1;
		c.setCid(CommonUtils.uuid());
		c.setCname("cstm_" + i);
		c.setBirthday("2017-08-22");
		c.setGender(i%2==0?"男":"女");
		c.setCellphone("139"+"i");
		c.setEmail("cstm" + i + "@163.com");
		c.setDescription("我是客户_" + i);
	System.out.println(c.toString());
	}
}
