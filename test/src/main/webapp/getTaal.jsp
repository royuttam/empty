<%@page import="java.util.*,java.io.*,matlabcontrol.*,org.apache.commons.lang3.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

String dir = prop.getProperty("songRoot")+java.net.URLDecoder.decode(request.getParameter("dir"), "UTF-8");
String path = dir+"/"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");

Object[] res = ukr.Utils.searchTaal(path);
out.println(res[1]);

%>

