<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.*,java.util.*,org.apache.commons.lang3.*"%>
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
for(int in=0;in<paths.length;in++) {
     if (paths[in].isFile()) {
    //if (paths[in].isFile() && paths[in].length() > 16000) {
        String file=paths[in].getName().toString();
        String f3 = java.net.URLEncoder.encode(file, "UTF-8");
    out.println("<option value='"+f3+"'>"+map.get(file)[1]+"</option>");    //for linux
    //out.println("<option value='"+f3+"'>"+file+"</option>");    	   		//for windows
    }
}

%>
<%!
public String buildHtmlEntityCode(String input, PrintWriter FID) {
    StringBuffer output = new StringBuffer(input.length() * 2);
    int len = input.length();
    int code,code1,code2,code3,code4;
    char ch;
    
    for( int i=0; i<len; ) {
        code1 = input.codePointAt(i);
        FID.print(code1+" ");
        if( code1 >> 3 == 30 ){
            code2 = input.codePointAt(i + 1);
            code3 = input.codePointAt(i + 2);
            code4 = input.codePointAt(i + 3);
            code = ((code1 & 7) << 18) | ((code2 & 63) << 12) | ((code3 & 63) << 6) | ( code4 & 63 );
            i += 4;
            output.append("&#" + code + ";");
        }
        else if( code1 >> 4 == 14 ){
            code2 = input.codePointAt(i + 1);
            code3 = input.codePointAt(i + 2);
            code = ((code1 & 15) << 12) | ((code2 & 63) << 6) | ( code3 & 63 );
            i += 3;
            output.append("&#" + code + ";");
        }
        else if( code1 >> 5 == 6 ){
            code2 = input.codePointAt(i + 1);
            code = ((code1 & 31) << 6) | ( code2 & 63 );
            i += 2;
            output.append("&#" + code + ";");
        }
        else {
            code = code1;
            i += 1;
            
            ch = (char)code;
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9') {
                output.append(ch);
            }
            else {
                output.append("&#" + code + ";");
            }
        }
    }
    return output.toString();
}

public String htmlEncode(final String string) {
    final StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < string.length(); i++) {
        final Character character = string.charAt(i);
        if (CharUtils.isAscii(character)) {
            // Encode common HTML equivalent characters
            stringBuffer.append(
            StringEscapeUtils.escapeHtml4(character.toString()));
        } else {
            // Why isn't this done in escapeHtml4()?
            stringBuffer.append(
            String.format("&#x%x;",
            Character.codePointAt(string, i)));
        }
    }
    return stringBuffer.toString();
}
%>