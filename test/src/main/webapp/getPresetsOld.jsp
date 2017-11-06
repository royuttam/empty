<%@page import="java.util.*,java.io.*"%>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

String soundfont = request.getParameter("soundfont");
System.out.println(soundfont);
Scanner scan = new Scanner(getServletContext().getResourceAsStream("/"+soundfont+".txt"));
scan.useDelimiter("\\Z");
StringTokenizer st = new StringTokenizer(scan.next(),"|");
scan.close();int i=1;
while(st.hasMoreTokens()) {
    out.println("<option value='"+i+"'>"+(i++)+". "+st.nextToken()+"</option>");    
}
%>