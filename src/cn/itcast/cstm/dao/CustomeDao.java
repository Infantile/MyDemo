package cn.itcast.cstm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.util.SchemaResolver;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.cstm.daomain.Custome;
import cn.itcast.cstm.daomain.PageBean;
import cn.itcast.jdbc.TxQueryRunner;

public class CustomeDao {
   private QueryRunner qr = new TxQueryRunner();
   /*
    * 添加客户到数据库中
    */
   public void add(Custome c){
	   String sql = "insert into t_customer values(?,?,?,?,?,?,?)";
	   Object[] params = { c.getCid(), c.getCname(), c.getGender(),
				c.getBirthday(), c.getCellphone(), c.getEmail(),
				c.getDescription() };
	   try{
		   qr.update(sql, params);
	   }catch(SQLException e){
		   throw new RuntimeException(e);
	   }   
   }
   
   public PageBean<Custome> findAll(int pc,int ps){
	   
	   try {
		      PageBean<Custome> pb = new PageBean<Custome>();
		      pb.setPc(pc);
		      pb.setPs(ps);
		      String sql = "select count(*) from t_customer ";
			   Number nb = (Number) qr.query(sql, new ScalarHandler());
			   int tr = nb.intValue();
			   pb.setTr(tr);
			   String sql1 = "select * from t_customer order by cname limit ?,?";
			 List<Custome> beanList = qr.query(sql1, new BeanListHandler<Custome>(Custome.class),
					   (pc-1)*ps,ps);
			 pb.setBeanList(beanList);
			 return pb;
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
   }

public Custome load(String cid) {
	try{
	String sql = "select* from t_customer where cid = ?";
	Object param = cid;
   return qr.query(sql, new BeanHandler<Custome>(Custome.class), param);
 
	}catch(SQLException e ){
		throw new RuntimeException(e);
	}
	
}

public void edit(Custome c) {
	String sql = 
			"update  t_customer set cname=? ,gender=?,birthday=?,cellphone=?,email=?,description=? where cid=? ";
	Object[] params = {c.getCname(),c.getGender(),c.getBirthday(),c.getCellphone(),c.getEmail(),c.getDescription(),c.getCid()};
	try {
		qr.update(sql,params);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}
  public void delete(String cid){
	  String sql = "delete from t_customer where cid=?";
	  try {
		qr.update(sql,cid);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
  }

  /*
   * 搜索
   */
/*public List<Custome> query(Custome c) {
	
	 * sql语句的前半句 因为不确定搜索给的参数的具体个数
	 *  通过判断条件来添加sql语句
	 *   创建Arraylist 
	 
	List<Object> params = new ArrayList<Object>();
	StringBuilder sql = new StringBuilder("select * from t_customer where '1'='1' ");
	String cname = c.getCname();
	if(cname!=null && !cname.trim().isEmpty()){
		sql.append(" and cname like ?");
		params.add("%"+cname+"%");
	}
	String gender = c.getGender();
	if(gender!=null && !gender.trim().isEmpty()){
		sql.append(" and gender=?");
		params.add(gender);
	}
	String cellphone = c.getCellphone();
	if(cellphone!=null && !cellphone.trim().isEmpty()){
		sql.append(" and cellphone like ?");
		params.add("%" + cellphone + "%");
	}
	String email = c.getEmail();
	if(email!=null && !email.trim().isEmpty()){
		sql.append(" and email like ?");
		params.add("%" + email + "%");
	}
	System.out.println(sql.toString());
	try {
		
		return qr.query(sql.toString(), new BeanListHandler<Custome>(Custome.class), params.toArray());
	} catch (SQLException e) {
	throw new RuntimeException(e);
	}
}
*/
  /**
   * 模糊查询功能
   * @param c
   * @param pc
   * @param ps
   * @return
   */
  public PageBean<Custome> query(Custome c,int pc,int ps)  {
		try{
	  PageBean<Custome> pb = new PageBean<Custome>();
	     pb.setPc(pc);
	     pb.setPs(ps);
	     List<Object> params = new ArrayList<Object>();
	     
	      /*
	       * 查询符合条件的数量
	       */
	     StringBuilder cntSql = new StringBuilder("select count(*) from t_customer ");
	     StringBuilder whereSql = new StringBuilder("where '1'='1'");
	     String cname = c.getCname();
	     if(cname!=null&&!cname.trim().isEmpty()){
	    	 whereSql.append(" and cname like ?");
	    	 params.add("%"+cname+"%");
	    	 }
	     String gender = c.getGender();
	     if(gender!=null&&!gender.trim().isEmpty()){
	    	 whereSql.append(" and gender=?");
	    	 params.add(gender);
	    	 }
	     String cellphone = c.getCellphone();
	     if(cellphone!=null&&!cellphone.trim().isEmpty()){
	    	 whereSql.append(" and cellphone like ?");
	    	 params.add("%"+cellphone+"%");
	    	 }
	     String email = c.getEmail();
	     if(email!=null&&!email.trim().isEmpty()){
	    	 whereSql.append(" and email like ?");
	    	 params.add("%"+email+"%");
	    	 }
	
	     Number nb = (Number)qr.query(cntSql.append(whereSql).toString(), new ScalarHandler(),params.toArray());
	     int tr = nb.intValue();
	     pb.setTr(tr);
	     /*
	      * 
	      * 拼凑sql 语句
	      */
	    StringBuilder selectSql = new StringBuilder("select * from t_customer ");
	    StringBuilder limitSql = new StringBuilder(" order by cname "+"limit ?,?");
	    StringBuilder sql = new StringBuilder();
	    sql.append(selectSql).append(whereSql).append(limitSql); 
	     //根据页来封装数据到List 集合中
	    params.add((pc-1)*10);
	    params.add(ps);
	    List<Custome> beanList = qr.query(
	    		sql.toString(), new BeanListHandler<Custome>(Custome.class),params.toArray());
	     pb.setBeanList(beanList);
	     return pb;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	 
	}

   
   
   
   
}
