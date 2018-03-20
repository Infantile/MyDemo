<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<h3 align="center">客户列表</h3>
<table border="1" width="70%" align="center">
	<tr>
		<th>客户姓名</th>
		<th>性别</th>
		<th>生日</th>
		<th>手机</th>
		<th>邮箱</th>
		<th>描述</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${requestScope.pb.beanList }" var="cstm">
	<tr>
		<td>${cstm.cname}</td>
		<td>${cstm.gender}</td>
		<td>${cstm.birthday}</td>
		<td>${cstm.cellphone}</td>
		<td>${cstm.email}</td>
		<td>${cstm.description}</td>
		<td>
			<a href="<c:url value='/CustomeServlet?method=preEdit&cid=${ cstm.cid}'/>">编辑</a>
			<a href="<c:url value='/CustomeServlet?method=delete&cid=${cstm.cid }' />">删除</a>
		</td>
	</tr>

	</c:forEach>
</table>
<br/>
<center>
 第${pb.pc }页/共${pb.tp }页 
 <c:choose>
     <c:when test="${pb.pc eq 1 }">首页</c:when>
     <c:otherwise>
     <a href="${pb.url }&pc=1">首页</a>
     </c:otherwise>
 </c:choose>
 <c:if test="${pb.pc > 1 }">
 <a href="${pb.url }&pc=${pb.pc-1 }">上一页</a>
   </c:if> 
<%--
页码列表，最多显示10个页码！当前页在5位置上，例如：1 2 3 4 (5) 6 7 8 9 10
 --%>
   <c:choose>
      <c:when test="${pb.tp<=10}">
      <c:set var="begin"value="1"/>
      <c:set var="end"value="${pb.tp }"/>
      </c:when>
      <c:otherwise>
	       <c:set var="begin" value="${pb.pc-4 }"/>
	       <c:set var="end" value="${pb.pc+5 }"/>
	       <!-- 头溢出 -->   
	       <c:if test="${begin<1 }">
	         <c:set var="begin" value="1"/>
	         <c:set var="end" value="10"/>
	       </c:if>
	       <!-- 尾溢出 -->
	       <c:if test="${end>pb.tp }">
		        <c:set var="begin" value="${pb.tp-9 }"/>
		        <c:set var="end" value="${pb.tp }"/>
	       </c:if>
       </c:otherwise>
   </c:choose>
   <c:forEach begin="${begin }" end="${end }"var="i">
    <c:choose>
       <c:when test="${i eq pb.pc }">
          ${i }
       </c:when>
       <c:otherwise>
     <a href="${pb.url }&pc=${i }">${i }</a>
    </c:otherwise>
    </c:choose>
   </c:forEach>
  <c:if test="${pb.pc < pb.tp}">
 <a href="${pb.url }&pc=${pb.pc+1 }">下一页</a>
 </c:if>
 <a href="${pb.url }&pc=${pb.tp }">尾页</a>
 


</center>
  </body>
</html>
