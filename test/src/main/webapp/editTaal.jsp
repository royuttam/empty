<%@page import="java.util.*,java.io.*,org.apache.commons.lang3.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

String inst = request.getParameter("inst");
//getServletContext().getResourceAsStream("/conf/system.conf");


java.util.Scanner scan = new java.util.Scanner(new java.io.InputStreamReader(getServletContext().getResourceAsStream("/conf/"+inst+".txt")));
            scan.useDelimiter("\\Z");
            String str=scan.next();
            scan.close();
out.println(str.trim());
%>

