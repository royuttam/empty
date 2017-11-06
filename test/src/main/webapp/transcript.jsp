<%@page import="java.util.*,java.io.*,matlabcontrol.*,org.apache.commons.lang3.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
//String fname=application.getRealPath("/")+"/conf/system.conf";
//FileInputStream fis = new FileInputStream(fname);
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));
//fis.close();

String dir = prop.getProperty("songRoot")+java.net.URLDecoder.decode(request.getParameter("dir"), "UTF-8");
String path = dir+"/"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");

 /*
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
    String line;
    while((line = br.readLine()) != null) {
      out.print(line);
    }
  */



Scanner scan = new Scanner(new InputStreamReader(new FileInputStream(path), "UTF-8"));
//            Scanner scan = new Scanner(new FileInputStream(path));
scan.useDelimiter("\\Z");
String content = scan.next();
scan.close();

int first = content.lastIndexOf("<body");
int last = content.lastIndexOf("</table>");
String result=content.substring(first,last+8);
first = result.indexOf("<div");
String result1=result.substring(first,result.length());


/*
int first = content.lastIndexOf("<table");
int last = content.lastIndexOf("</table>");
String result1=content.substring(first,last+8);
*/

//Swarabitan
//Vidya
//Vidya



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


//result1=content;

out.println(result1);

//System.out.println(result1);

//PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("sample.html"), "UTF-16"));
//pw.println(result1);
//pw.close();

//FileOutputStream fos = new FileOutputStream("sample.html");
//fos.write(content.getBytes());
//fos.close();

/*
InputStream in = new FileInputStream(path);

//int len =in.available();
//byte[] bytes = new byte[len];
//in.read(bytes,0,len);
//out.println(new String(bytes,"UTF-8"));
int c;  
StringBuffer sb = new StringBuffer();
FileOutputStream fos = new FileOutputStream("sample1.html");
        while (((c = in.read()) != -1)) {
          fos.write((char)c);
          sb.append((char)c);
        }
fos.close();
fos = new FileOutputStream("sample2.html");

in = new FileInputStream(path);
int len1 =in.available();
byte[] bytes = new byte[len1];
System.out.println(in.read(bytes,0,len1));
String content1 = new String(bytes,"UTF-8");

String content2 = new String(sb.toString().getBytes(),"UTF-8");
String content3 = new String(content.getBytes(),"UTF-8");

String str = new String(bytes);
int len =str.length();
for(int i=0;i<len;i++)
    //fos.write(sb.charAt(i));
    fos.write(str.charAt(i));
out.println(sb.toString());
System.out.println("len1 = "+len1);
System.out.println(sb.toString().length()+" "+content.length()+" "+content1.length()+" "+content2.length()+" "+content3.length());


//System.out.println(sb.toString());
//out.println(sb.toString());

//int len =result1.length();
//for(int i=0;i<len;i++)
//fos.write((char)result1.charAt(i));
fos.close();


System.out.println("transcript.jsp called");

*/
    /*
        InputStream in = new FileInputStream(path);
        int c;
        while (((c = in.read()) != -1)) {
          out.print((char)c);
          //System.out.print((char) c);
        }
        in.close();
     */

%>
<%!
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
