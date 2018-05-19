<%@page import="java.util.*,java.io.*,matlabcontrol.*,org.apache.commons.lang3.*,org.w3c.dom.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%
try {
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

String dir = prop.getProperty("songRoot")+java.net.URLDecoder.decode(request.getParameter("dir"), "UTF-8");
String path = dir+"/"+java.net.URLDecoder.decode(request.getParameter("song"), "UTF-8");
String inst = request.getParameter("inst");
int scale = Integer.parseInt(request.getParameter("scale"))-3;
int preset1 = Integer.parseInt(request.getParameter("preset1"));
int preset2 = Integer.parseInt(request.getParameter("preset2"));
int bpm = Integer.parseInt(request.getParameter("bpm"));
String taal = request.getParameter("taals");
String soundfont = request.getParameter("soundfont");
String v = request.getParameter("tablaonoff");
String v1 = request.getParameter("songonoff");
boolean tablaonoff=true, songonoff=true;
if(v==null) tablaonoff=false;
if(v1==null) songonoff=false;

String flag= request.getParameter("flag");

//System.out.println(path+" "+scale+" "+inst+" "+preset1+" "+preset2+" "+bpm+" "+taal+" "+soundfont+" "+tablaonoff+" "+songonoff+" "+flag);

String file = ukr.Utils.process(path,scale,inst,preset1,preset2,bpm,taal,soundfont,tablaonoff,songonoff,flag);
 //String file = ukr.Utils.process("/var/lib/openshift/56c73c5a2d5271142600017d/jbossews/webapps/songs/final/AiTobeSohoChari.html", 2, "khol", 3, 89, 180, "4444", "ukr.SF2", false,        false, "wav");
 out.println("<a href='"+file+"'>here</a>");

  //ukr.Utils.process("E:/nltr/out/final/AliBarBarFireJay.html", 2, "tabla", 3, 89, 180, "33v7", "ukr.SF2", false, false, "play");
//ukr.Utils.process("E:/nltr/out/final/AnekKothaBole.html", 2, "tabla", 3, 89, 180, "33v7", "ukr.SF2", false,        false, "play");
//System.out.println("process.jsp called");
}catch(Exception e) {out.println("<a href='"+e+"'>here</a>");}

//System.out.print("Waiting...");
//Thread.sleep(600000);
//System.out.println("Completed");

%>
<%!

class Info {
  class Meta {
        public int bpm, scale;
        public String taal;
        public int inst1, inst2;
        public Meta(int bpm, String taal, int inst1, int inst2,int scale) {
            this.bpm=bpm;this.scale=scale;this.taal=taal;this.inst1=inst1;this.inst2=inst2;
        }
    }
   public Info(int bpm, String taal, int inst1, int inst2, int scale) {
		System.out.println("In Meta");
       //meta = new Meta(bpm,taal, inst1, inst2, scale);
   }
    public Meta meta;   
}
 //----------------------
class Song {
    class Meta {
        public int pre = 0;
    }
    class Segment {
        String[] T = null;
        String name = "noName";
    }
    class Data {
        public Segment segment = new Segment();
    }
    public Meta meta = new Meta();
    public Data[] data = null;
}
//----------------------------

static class Utils {
        //----------------------------------------------
    static String process(String path, int scale, String inst,int preset1, int preset2,int bpm, String taal, String soundfont,boolean tablaonoff, boolean songonoff,String flag) throws Exception{
        
        System.out.println(path +" "+ scale+" "+inst+" "+preset1 +" "+preset2+" "+bpm+" "+taal+" "+soundfont+" "+ tablaonoff+" "+songonoff +" "+flag);
            //System.out.println(Utils.readHtml(path));    
            String midifile=Utils.createMidi1(path, scale, inst,preset1,preset2,bpm,taal,tablaonoff,songonoff);

            //midifile=Utils.createMidi1(path, scale, inst,preset1,preset2,bpm,taal,tablaonoff,songonoff)
    /*
            if strcmp(flag,'play')
                Utils.playMidi(midifile,[Utils.soundFontRoot soundfont]);
                file=midifile;
            elseif strcmp(flag,'mp3')
                tokens=strsplit(path,'/');
                file=['mp3/' bengToEng(tokens{length(tokens)}) '.mp3'];
                Utils.midi2mp3(midifile,[Utils.webRoot file],[Utils.soundFontRoot soundfont]);
            elseif strcmp(flag,'midi')
              str=strsplit(midifile,'\');
              file=[ 'midi/' str{length(str)}];
              elseif strcmp(flag,'wav')
                tokens=strsplit(path,'/');
                file=['wav/' bengToEng(tokens{length(tokens)}) '.wav'];
                Utils.midi2wav(midifile,[Utils.webRoot file],[Utils.soundFontRoot soundfont]);
            end
     **/
            return "";
    }
    //----------------------------------------------
    
    static String createMidi1(String path, int scale, String inst,int preset1, int preset2,int bpm,String taal,boolean tablaonoff, boolean songonoff) throws Exception {
        String[] str=path.split("/");
        String str1=str[str.length-1];
        //String mat=Utils.readHtml(path);
	
        //Info info = new Info(bpm, taal, preset1, preset2,scale);
        //    String midifile=Utils.createMidi(bengToEng(str),mat, inst, info, tablaonoff,songonoff);            
           
            return "";
    }

    //----------------------------------------------
        static String[] concat(String[]... arrays) {
            int length = 0;
            for (String[] array : arrays) {
                if(array !=null)
                    length += array.length;
            }
            String[] result = new String[length];
            int pos = 0;
            for (String[] array : arrays) {
                if(array !=null)
                    for (String element : array) {
                        result[pos] = element;
                        pos++;
                    }
            }
            return result;
        }
        //----------------------------------------------
        static String readHtml(String path) throws java.io.IOException{
            java.util.Scanner scan = new java.util.Scanner(new java.io.InputStreamReader(new java.io.FileInputStream(path), "UTF-8"));
            scan.useDelimiter("\\Z");
            String str=scan.next();
            scan.close();
            return Utils.htmlstr2mat(Utils.removeNonASCII(str));
            //return "";
        }
        
        static String htmlstr2mat(String str) {
            int t1=str.lastIndexOf("<table");
            int t2=str.lastIndexOf("</table>")+7;
            System.out.println(t1+" "+t2);
            String s = str.substring(t1,t2+1);
//s=s.replaceAll("'","\"");
            s=s.replaceAll("<table [^>]*>","<table>");
            s=s.replaceAll("<td [^>]*>","<td>");
            s=s.replaceAll("<span [^>]*>","<span>");
            s=s.replaceAll("<div [^>]*>","<div>");
            s=s.replaceAll("&nbsp;","");
            
//int t1=str.lastIndexOf("<table");
            t1 = (s.length() - s.replaceAll("<tr", "").length())/3;
            t2 = (s.length() - s.replaceAll("</tr", "").length())/4;
            
            if(t1 != t2) {
                System.out.println("======no of <tr ("+t1+") and </tr ("+t2+")are unequal========");
                s=s.replaceAll("</tr>","</tr><tr>");
                int l=s.lastIndexOf("<tr>");
                System.out.println(l+" "+s.charAt(l));
                s=s.substring(0,l)+s.substring(l+4,s.length());
            }
            else
                s=s.replaceAll("<br>","<br/>");
            
            t1 = (s.length() - s.replaceAll("<tr", "").length())/3;
            t2 = (s.length() - s.replaceAll("</tr", "").length())/4;
            
            System.out.println(t1+" "+t2);
            
            s=s.replaceAll("\\s*","");
            s=s.trim();
            s="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+s;
//System.out.println(s);
            try {
                javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.StringBufferInputStream(s));
                Document xmldoc = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.StringBufferInputStream(s));
                NodeList rows = xmldoc.getElementsByTagName("tr");
                String[][] mat = new String[rows.getLength()][];
                for (int i = 0; i < rows.getLength(); i++) {
                    
                    Element element = (Element) rows.item(i);
                    NodeList cols = element.getElementsByTagName("td");
                    mat[i] = new String[cols.getLength()];
                    for (int j = 0; j < cols.getLength(); j++) {
                        Element e = (Element) cols.item(j);
                        NodeList div = e.getElementsByTagName("div");
                        String data="";
                        Node child = ((Element) div.item(0)).getFirstChild();
                        if (child instanceof CharacterData) {
                            CharacterData cd = (CharacterData) child;
                            data= cd.getData();
                        }
                        mat[i][j] = data;
                    }
                }
                
                String[][] mat1 = Utils.mat2seq(mat);
                
                for(int i = 0;i<mat1.length;i++){
                    System.out.println("\n");
                    for(int j = 0;j<mat1[i].length;j++)
                        System.out.print(mat1[i][j]+" ");
                }
            }catch(Exception e) {}
            
            
            return "";
        }
        /*
        static void seq2t(String[][] s) {
            System.out.println("Start seq2t.m..............");
            String[] tn=null,repeatseg=null;String state="start";
            int c=1;boolean box=false;
            int last=0,nstanza=1;
            int itnNo=0; boolean success=true;
            Song song = new Song();
            while (c<=s[0].length) {
                itnNo=itnNo+1;if (itnNo > 10000) {success=false;break;}
                String str=s[2][c];
                str=Utils.removeInvalidChar(str);
                switch (state) {
                    case "start": break;
                }
            }
        }
        
        
        static String removeInvalidChar(String s) {
            String x="svrtgmkpdqunSVRTGMKPDQUNhfHF-aiA|lL[]{}()";
            String Y="";
            s=s.replaceAll("\\s+","");
            for (int i=0;i<s.length();i++)
                if (x.indexOf(s.charAt(i)) != -1)
                    Y=Y+s.charAt(i);
            return Y;
        }
         */
        static String removeNonASCII(String in) {
            StringBuffer out = new StringBuffer();
            char current; int len=in.length();
            if (in == null || ("".equals(in))) return ""; // vacancy test.
            for (int i = 0; i < len; i++) {
                current = in.charAt(i);
                if(current < 128) out.append(current);
            }
            return out.toString();
        }
        
        static String[][] mat2seq(String[][] mat) {
            String[][] mat1 = new String[4][];
            for(int i = 0;i<mat1.length;i++)
                mat1[i] = new String[(mat.length/4)*mat[0].length];
            
            for (int i = 0; i < mat.length; i++) {
                System.out.println();
                for (int j = 0; j < mat[i].length; j++) {
                    System.out.print(mat[i][j]+" ");
                    mat1[i%4][(i/4)*mat[0].length+j]=mat[i][j];
                }
            }
            return mat1;
        }
    }

%>


