<%@page import="java.util.*,java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

String taal = request.getParameter("alltaals");
System.out.println(taal);
String inst = request.getParameter("inst");
int bpm = Integer.parseInt(request.getParameter("bpm"));
int scale = Integer.parseInt(request.getParameter("scale"))-3;
String soundfont = request.getParameter("soundfont");

System.out.println(inst+"\t"+taal+"\t"+scale+"\t"+bpm+"\t"+1000+"\t"+soundfont);
//proxy.feval("Utils.playInst",inst,taal,scale,bpm,1000,soundfont);
ukr.Utils.playInst(inst,   taal,       scale,       bpm,     1000,    soundfont);

%>

