<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.File,java.util.Properties,java.util.Map"%>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

Map<String, String[]> map = (Map<String, String[]>)application.getAttribute("map");
if(map == null) {
	map = ukr.Utils.getDb(prop.getProperty("mapFile"));
	application.setAttribute("map",map);
}
String dir=prop.getProperty("songRoot")+request.getParameter("dir");

File[] paths = new File(dir).listFiles();
int index=0;
String song = "";
for(int in=0;in<paths.length;in++) {
	if (paths[in].isFile()) {
		if (paths[in].isFile() && paths[in].length() > 16000) {
			
			String file=paths[in].getName().toString();
			String f3 = java.net.URLEncoder.encode(file, "UTF-8");
			out.println("<option value='"+f3+"'>"+map.get(file)[1]+"</option>");    //for linux	
			//out.println("<option value='"+f3+"'>"+file+"</option>");    	   		//for windows
			
			if(index == 0) {song=f3;index=1;}
		}
	}
}
out.println("##");
String d = request.getParameter("dir");
String inst = request.getParameter("inst");
%>

<jsp:include page="<%=\"getTaal.jsp?dir=\"+d+\"&song=\"+song+\"&inst=\"+inst%>"/>

