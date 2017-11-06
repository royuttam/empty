<%@page import="java.util.*,java.io.*,matlabcontrol.*,org.apache.commons.lang3.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
try {
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

String inst = request.getParameter("inst");
String webRoot=prop.getProperty("webRoot");
String transcript=request.getParameter("my-textarea");

String path= webRoot+"conf/"+inst+"1.txt";

PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(webRoot+"conf/"+inst+".txt")));
pw.println(transcript);
pw.close();
out.println("Saved sucessfully");
}catch(Exception e) {out.println(e);}
%>
