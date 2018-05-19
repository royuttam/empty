<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.File,java.util.Properties"%>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

String dir=prop.getProperty("soundFontRoot");

File[] paths = new File(dir).listFiles();
for(int in=0;in<paths.length;in++) {
	String file=paths[in].getName();
	out.println("<option value='"+file+"'>"+file+"</option>");    
}

%>
