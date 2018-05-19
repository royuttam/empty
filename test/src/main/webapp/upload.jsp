<%@page import="java.util.*,java.io.*,org.apache.commons.lang3.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
String fname=application.getRealPath("/")+"/conf/system.conf";
FileInputStream fis = new FileInputStream(fname);
prop.load(fis);
fis.close();

String dir = prop.getProperty("songRoot")+java.net.URLDecoder.decode(request.getParameter("dir"), "UTF-8");
//String path = dir+"/"+"copy"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");
String transcript=java.net.URLDecoder.decode(request.getParameter("my-textarea"));

String song=transcript;
song = song.substring(song.lastIndexOf(":"),song.indexOf("</table"));
song = song.substring(song.lastIndexOf("style3"),song.lastIndexOf("</span"));
song = song.substring(song.lastIndexOf(">")+1,song.length());
song=java.net.URLDecoder.decode(song)+".html";


//String song="sample.html";
String path=dir+"/"+song;

 System.out.println(song);
 
PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
pw.println("<body>");
pw.println(transcript);
pw.close();

System.out.println("upload.jsp called");
out.println("Uploaded successfully.");
%>
