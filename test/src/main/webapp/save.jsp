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

//System.out.println(request.getCharacterEncoding());

//String transcript = request.getParameter("my-textarea");
//String transcript = new String(request.getParameter("my-textarea").getBytes(),"UTF-8");
String transcript=java.net.URLDecoder.decode(request.getParameter("my-textarea"));

//System.out.println(transcript);


PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
//System.out.println(java.net.URLDecoder.decode(transcript));
pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
pw.println("<body>");
pw.println(transcript);
//pw.println(java.net.URLDecoder.decode(transcript));
pw.close();

out.println("Saved successfully.");
//out.println(transcript);
 

/*
Scanner scan = new Scanner(new InputStreamReader(new FileInputStream(path),"UTF-8"));
scan.useDelimiter("\\Z");
String content = scan.next();
scan.close();
*/

//out.println(content);
/*
String path1 = dir+"/"+"sample"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");
FileOutputStream fos = new FileOutputStream(path1);
fos.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>".getBytes());
for(int i=0;i<transcript.length();i++)
   //System.out.println(transcript.charAt(i));
    fos.write(transcript.charAt(i));
fos.close();
 
System.out.println("content length: "+content.length());
System.out.println("Done");    
*/
//String path = dir+"/"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");

 /*
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
    String line;
    while((line = br.readLine()) != null) {
      out.print(line);
    }
  */

/*
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
"        font-family: Vidya;\n"+
"        font-size: 18px;\n"+
"        }\n"+
".style3 {\n"+
"	font-family: Vidya;\n"+
"	font-size: 24px;\n"+
"        }\n"+
"-->\n"+
"</style>"+result1;


out.println(result1);
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
