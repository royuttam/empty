<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

String taal = request.getParameter("taal");
String inst = request.getParameter("inst");

Object[] values = ukr.Utils.taals(inst,taal);
        
        StringTokenizer st=new StringTokenizer((String)values[1],"|");
String str="";
while(st.hasMoreTokens()) {
    String tk=st.nextToken();
    str=str+"<option value='"+tk+"'>"+tk+"</option>";
}

str=str+"##";
String[] tokens = (String[])values[0];
for(int i=0;i<tokens.length;i++){    
    String tk=tokens[i];
    str=str+"<option value='"+tk+"'>"+tk+"</option>";
}

out.println(str);
%>

