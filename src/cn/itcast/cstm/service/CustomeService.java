package cn.itcast.cstm.service;

import java.util.List;

import cn.itcast.cstm.dao.CustomeDao;
import cn.itcast.cstm.daomain.Custome;
import cn.itcast.cstm.daomain.PageBean;

public class CustomeService {
   private CustomeDao customeDao = new CustomeDao();
   
   //添加用户
   public void add(Custome c){
	   customeDao.add(c);

   }
     /*
      *查询所有
      */
   public PageBean<Custome> findAll(int pc,int ps){
	   return customeDao.findAll(pc,ps);
   }
public Custome load(String cid) {
	Custome c = customeDao.load(cid);
	return c;
}
public void edit(Custome c) {
	customeDao.edit(c);
	
}
  public void delete(String cid){
	  customeDao.delete(cid);
  }
/*public List<Custome> query(Custome c) {
	return customeDao.query(c);
}*/
  public PageBean<Custome> query(Custome c ,int pc,int ps) {
		return customeDao.query(c,pc,ps);
	}
}
