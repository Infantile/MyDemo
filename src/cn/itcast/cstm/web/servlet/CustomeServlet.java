package cn.itcast.cstm.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.cstm.daomain.Custome;
import cn.itcast.cstm.daomain.PageBean;
import cn.itcast.cstm.service.CustomeService;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

/**
 * Servlet implementation class CustomeSevlet
 */
public class CustomeServlet extends BaseServlet {
     private CustomeService customeService = new CustomeService();

	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	/*
	 * 1、封装获得的数据
	 * 2 、使用uuid做为主键
	 * 3、调用service中的add方法
	 * 4、想域中传递成功信息
	 * 5、转发到msg.jsp页面中
	 *
	 */
		Custome c = CommonUtils.toBean(request.getParameterMap(), Custome.class );
		c.setCid(CommonUtils.uuid());
		customeService.add(c);
		request.setAttribute("msg","注册成功！！！");
		
		return"f:/msg.jsp";
	}
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		/*
		 * 获得pc 就是页码 然后给定的ps 就是每页的数量
		 * 将pc ps 传到 customeService findAll 方法得到 PageBean
		 * 将PageBean保存到request域中
		 * 转发到list.jsp
		 */
		int pc = getPc(request);
		int ps =10;
		PageBean<Custome> pb = customeService.findAll(pc,ps);
		pb.setUrl(getUrl(request));
		request.setAttribute("pb", pb);
		
		
		return "f:/list.jsp";
	}
	public int getPc(HttpServletRequest request){
	  String value = request.getParameter("pc");
	  if(value==null||value.isEmpty()){
		  return 1;
	  }
	  return Integer.parseInt(value);
	}
	/**
	 * 编辑之前的加载工作
	 
	 */
	public String preEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1、获取cid
		 * 2、调用service方法获得Custome 
		 * 3、将Custome 保存到request域中去
		 * 4、转发到edit.jsp去
		 */
		String cid = request.getParameter("cid");
	    Custome custome =customeService.load(cid);
		request.setAttribute("cstm", custome);
		
		
		return "f:/edit.jsp";
	}
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   /*
		    * 封装表单数据到custome
		    * 调用service保存信息
		    * 保存成功信息到request域中
		    * 转发到msg.jsp显示成功信息
		    */
		 Custome c = CommonUtils.toBean(request.getParameterMap(), Custome.class);
		
		 c.setCid(request.getParameter("cid"));
		 customeService.edit(c);
		 request.setAttribute("msg", "编辑成功");		
		 return "f:/msg.jsp";
	}
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 获取cid
		 * 调用service删除信息
		 * 保存成功信息到request 域中
		 * 转发到msg.jsp
		 */
		
		String cid = request.getParameter("cid");
		 customeService.delete(cid);
		request.setAttribute("msg", "删除成功");
		return "f:/msg.jsp";
	}
/*	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		*//**
		 * 封装表单数据
		 * 调用service方法查询 获得beanlist 对象
		 * 讲获得的BeanList<custome>保存到request域中
		 * 转发到list.jsp
		 *//*
		Custome c = CommonUtils.toBean(request.getParameterMap(),Custome.class);
		List<Custome> CustomeList = customeService.query(c);
		request.setAttribute("cstmList", CustomeList);
		return "f:/list.jsp";
	}*/
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 封装表单数据
		 * 调用service方法查询 并 返回PageBean 对象
		 * 保存到request域中 
		 * 转发到 list.jsp
		 */
//		System.out.println(getUrl(request));
		
	    Custome c = CommonUtils.toBean(request.getParameterMap(), Custome.class);
	    /*
	     * 处理get请求方式的编码 但是我的eclipse全是设置的utf8 所以不需要 处理编码
	     */
	   
	   //  c = encoding(c);
	    int pc = getPc(request);
	    int ps = 10;
	   PageBean<Custome> pb = customeService.query(c,pc,ps);
	   //得到url 保存到pb中去
	   pb.setUrl(getUrl(request));
	   
	   request.setAttribute("pb", pb);
		return "f:/list.jsp";
	}
	
	/* private Custome encoding(Custome c) throws UnsupportedEncodingException {
         String cname = c.getCname();
         String gender = c.getGender();
         String cellphone = c.getCellphone();
         String email = c.getEmail();
         
        		 if(cname!=null && !cname.trim().isEmpty()){
        			 cname = new String(cname.getBytes("ISO-8859-1"),"utf-8");
        			 c.setCname(cname);
        		 }
        		 if(gender!=null && !gender.trim().isEmpty()){
        			 gender = new String(gender.getBytes("ISO-8859-1"),"utf-8");
        			 c.setGender(gender);
        		 }
        		 if(cellphone!=null && !cellphone.trim().isEmpty()){
        			 cellphone = new String(cellphone.getBytes("ISO-8859-1"),"utf-8");
        			 c.setCellphone(cellphone);
        		 }
        		 if(email!=null && !email.trim().isEmpty()){
        			 email = new String(email.getBytes("ISO-8859-1"),"utf-8");
        			 c.setEmail(email);
        		 }
        		 System.out.println(gender);
		 return c;
} */
	private String getUrl(HttpServletRequest request){
		  /*
		   * 截取url
		   * /项目名/sevlet?参数字符串		
		   */
         //项目名
		 String contextPath = request.getContextPath(); 
		 //servlet名
		 String servletPath = request.getServletPath();
		 //参数字符串 截取pc后的值 不要了
		 String queryString = request.getQueryString();
		  if(queryString.contains("&pc")){   //判断queryString中是否有“&pc”
			  int index = queryString.lastIndexOf("&pc"); //获取“&pc”后第一个值的下标
			  queryString = queryString.substring(0, index);
		  }
		 
		 
		 return contextPath + servletPath + "?" + queryString;
	 }
	
	
}
