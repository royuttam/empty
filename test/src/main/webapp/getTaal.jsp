<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

String dir = prop.getProperty("songRoot")+java.net.URLDecoder.decode(request.getParameter("dir"), "UTF-8");
String path = dir+"/"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");

String[] res = ukr.Utils.searchTaal(path);
out.println(res[1]);

out.println("##");
//String taal = res[1].toString();
String taal = res[1];
String inst = request.getParameter("inst");
%>

<jsp:include page="<%=\"getAvailableTaals.jsp?taal=\"+taal+\"&inst=\"+inst%>"/>

<%
out.println("##");
String d = request.getParameter("dir");
String song = request.getParameter("song");
%>

<jsp:include page="<%=\"transcript.jsp?dir=\"+d+\"&song=\"+song%>"/>