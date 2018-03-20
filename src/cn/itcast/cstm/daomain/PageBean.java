package cn.itcast.cstm.daomain;

import java.util.List;

public class PageBean<T> {
 private int pc ;//当前页码 page code
 private int tr ;//总记录数total record
 private int ps ;//每页记录数 page size
 private String url;//路径
 public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}

private List<T> beanList; //当前页的记录
public int getPc() {
	return pc;
}
public void setPc(int pc) {
	this.pc = pc;
}
public int getTr() {
	return tr;
}
public void setTr(int tr) {
	this.tr = tr;
}
public int getPs() {
	return ps;
}
public void setPs(int ps) {
	this.ps = ps;
}
public List<T> getBeanList() {
	return beanList;
}
public void setBeanList(List<T> beanList) {
	this.beanList = beanList;
}
 
 public int getTp(){
	 int tp = tr/ps;
	 return tr%ps==0?tp:tp+1;
 }
}
