<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.Properties,java.util.Scanner"%>
<%@page import="java.io.InputStreamReader,java.io.FileInputStream"%>

<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

String dir = prop.getProperty("songRoot")+java.net.URLDecoder.decode(request.getParameter("dir"), "UTF-8");
String path = dir+"/"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");

Scanner scan = new Scanner(new InputStreamReader(new FileInputStream(path), "UTF-8"));
scan.useDelimiter("\\Z");
String content = scan.next();
scan.close();

int first = content.lastIndexOf("<body");
int last = content.lastIndexOf("</table>");
String result=content.substring(first,last+8);
first = result.indexOf("<div");
String result1=result.substring(first,result.length());

result1="<style type='text/css'>    \n"+
"<!--    \n"+
".style1 {   \n"+
"        font-family: Swarabitan;\n"+
"        font-size: 18px;\n"+
"        }\n"+
".style2 {\n"+
"        font-family: Swarabitan;\n"+
"        font-size: 18px;\n"+
"        }\n"+
".style3 {\n"+
"	font-family: Swarabitan;\n"+
"	font-size: 24px;\n"+
"        }\n"+
"-->\n"+
"</style>"+result1;

result1="<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"+result1;

result1 = result1+"</div>";

out.println(result1);
//System.out.println(result1);
%>

