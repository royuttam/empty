<%@page import="java.util.*,java.io.*,org.apache.commons.lang3.*,java.nio.charset.*,java.text.Normalizer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Instrumental RanbindraSangeet</title>
<link rel="stylesheet" type="text/css" href="mystyle.css">
<script src="jquery-1.11.3.min.js"></script>
<script src="jsfunc.js"></script>

<script language="JavaScript">
function updateAvailableTaal() {
	param='taal='+$("#taal").val()+'&inst='+$("#inst").val();
	$.post('getAvailableTaals.jsp?'+param, '',   function(data, status){
		result=data.split('#');
		$("#taals").html(result[0]);
		$("#alltaals").html(result[1]);
	});
        if($("#group3").is(':visible')) 
  	       $.post('editTaal.jsp', $("#mainform").serialize(),   function(data, status){
			$("#transcript3").html(data);
		});
}
function updateTranscript(){
	$.post('transcript.jsp', $("#mainform").serialize(),   function(data, status){
		$("#transcript").html(data);
		$( "#file" ).html('');
	});
}
function updateTranscript1(){    
	$("#transcript2").html($("#transcript").html());
	var tb=getTable();
	var row = tb.insertRow(0);
	var columnCount = tb.rows[1].cells.length;
	
	for (var i = 0; i < columnCount; i++) {
		var element_1 = document.createElement("input");
		element_1.type = "checkbox";
		row.insertCell(i).appendChild(element_1);
	}
	tb.rows[0].insertCell(0).appendChild(document.createElement("td"));
	for (var i = 1; i < tb.rows.length; i++) {
		var element_4 = document.createElement("input");
		element_4.type = "checkbox";
		tb.rows[i].insertCell(0).appendChild(element_4);
	}        
}
function updateTaal() {
	$.post('getTaal.jsp', $("#mainform").serialize(),   function(data, status){
		$("#taal").val(data);
		updateAvailableTaal();
	});
}
function updatedir() {
	var url='getSongs.jsp?dir=' + $("#dir").val();
	$.post(url, null,   function(data, status){
		$("#song").html(data);
		updateTaal();
		updateTranscript();
	});
}
function updateSoundFont() {
	v1=$("#preset1").val();
	v2=$("#preset2").val();
	$.post('getPresets.jsp?soundfont='+$("#soundfont").val(), '',   function(data, status){
		$("#preset1").html(data);
		$("#preset1").val(v1);
		$("#preset2").html(data);
		$("#preset2").val(v2);
	});
}
function save() {
	$('#my-textarea').val(        $('#transcript').html()     );
	$.post('save.jsp', $("#mainform").serialize(),   function(data, status){
		alert(data);
	});
}
function uploadTaal() {
	//alert($('#transcript3').text());

	$('#my-textarea').val(        $('#transcript3').text()     );
	$.post('saveTaal.jsp', $("#mainform").serialize(),   function(data, status){
		alert(data);
	});
}

function upload() {
	div = document.createElement('div');
	div.innerHTML=$('#transcript2').html();
	tables = div.getElementsByTagName("table");
	var tb=tables[1];
	
	tb.deleteRow(0);
	for (var x = 0; x < tb.rows.length; x++) 
	tb.rows[x].deleteCell(0);
	
	for(i=0;i<tables.length;i++)
	tables[i].setAttribute("border","0");
	
	$('#my-textarea').val(div.innerHTML);    
	$.post('upload.jsp', $("#mainform").serialize(),   function(data, status){   alert(data);});
}
function reset() {
	//alert('reset');
	//updatedir();
	//updateSoundFont();
	//updateTranscript();
	$("#scale").val(5);
	$("#inst").val('tabla');
	//updateAvailableTaal();
	$("#soundfont").val('ukr.SF2');
	$("#preset1").val(3);
	$("#preset2").val(89);
	$("#bpm").val(180);
	$("#bpmval").val('180');
}
function process(type) {
	$( "#file" ).html('Generating '+type+' file...');
	$( "#loading" ).show();
	$.post('process.jsp?flag='+type, $("#mainform").serialize(),   function(data, status){
		$( "#file" ).html(type+' file is now ready, click'+data+' to listen');
		$( "#loading" ).hide();
	});    
}

$(document).ready(function(){
	$("#dir").change(function(){ updatedir(); });
	$("#song").change(function(){ updateTaal();  updateTranscript();});
	$( "#mp3" ).click(function( event ) {  process('mp3');  });
	$( "#midi" ).click(function( event ) {  process('midi');  });
	$( "#wav" ).click(function( event ) {  process('wav');  });

	$( "#create" ).click(function( event ) {  
		type=$("#flag").val();
		//alert($("#flag").val());

		$( "#file" ).html('Generating '+type+' file...');
		$( "#loading" ).show();
		/*
		$.post('process.jsp', $("#mainform").serialize(),   function(data, status){
			$( "#file" ).html(type+' file is now ready, click'+data+' to listen');
			$( "#loading" ).hide();
		});
		*/
		
		$.ajax({
    type    : "POST",
    url     : 'process.jsp',
    data    : $("#mainform").serialize(),
	timeout : 0,
    success : function(data, status) {
		alert(status);
				$( "#file" ).html(type+' file is now ready, click'+data+' to listen');
				$( "#loading" ).hide();          }	,
    complete: function() {
    
	}
});
		

		

	});
	
	$( "#play" ).click(function( event ) {
		$.post('process.jsp?flag=play', $("#mainform").serialize(),   function(data, status){      });
	});
	$( "#test" ).click(function( event ) {
		$.post('test.jsp', $("#mainform").serialize(),   function(data, status){    });
                //$.post('process.jsp?flag=playInst', $("#mainform").serialize(),   function(data, status){      });
	});

	$( "#edittrans" ).click(function( event ) {
		$content = $("#group2");
		$content.slideToggle(0, function () {
			$(this).val(function () { return $content.is(":visible") ? "+" : "-";});
			$("#group").slideToggle(0,null);
		});
	});

	$( "#edittrans1" ).click(function( event ) {
		$content = $("#group3");
		$content.slideToggle(0, function () {
			$(this).val(function () { return $content.is(":visible") ? "+" : "-";});
			$("#group").slideToggle(0,null);
		});
		// $("#transcript3").html("fsdfsdfsdgdsgsdg----");
		$.post('editTaal.jsp', $("#mainform").serialize(),   function(data, status){
			//alert(data);
			//$( "#file" ).html(type+' file is now ready, click'+data+' to listen');
			//$( "#loading" ).hide();
			$("#transcript3").html(data);
		});



	});

	$("#taal").change(function(){      updateAvailableTaal();          });
	$("#inst").change(function(){      updateAvailableTaal();          });
	$("#soundfont").change(function(){ updateSoundFont();              });
	$("#bpm").mousemove(function(){   $("#bpmval").val($(this).val()); });    
	$("#bpm").change(function(){      $("#bpmval").val($(this).val()); });
	$("#reset").click(function(){ reset(); });    
	$("#save").click(function(){      save();  });
	$("#bpmval").change(function(){   $("#bpm").val($(this).val());    });    
	
	$("#download").mousedown(function(){   
	  url='transcript.jsp?dir='+$("#dir").val()+'&song='+$("#song").val();
	  //alert();
	  $(this).attr('href',url);    
	});    
	
	/*
	$(document).on("click", "#transcript table tr", function(e) {
		//alert($(this).text());
		$(this).addClass('selected').siblings().removeClass('selected');       
		var value=$(this).html();
		//alert(value);
	});
	*/  
	/*
	$("#table1 tr").click(function(){
		//alert('a');
$(this).addClass('selected').siblings().removeClass('selected');    
var value=$(this).find('td:first').html();
alert(value);    
});

$('.ok').on('click', function(e){
	alert($("#table tr.selected td:first").html());
});
*/

	updatedir();
	reset();
	$("#transcript2").html($("#skeleton").html());
	
});
</script>
</head>
<style>
style { display: none; }
</style>
<body>
<style contenteditable>
body { background: #fef; }
</style>

<H1 align="center">Instrumental Music Synthesis</H1>
<form method="post" action="index.jsp">
<table align="center">
<tr>
<td><a href="http://www-uroy.rhcloud.com">Home</a>| Download  <a href='Swarabitan.ttf'>Swaralipi Font</a> </td>
<td>user: <input type="text" name="user" size="5"></td>
<td>Password: <input type="password" name="passwd" size="5"></td>
<td><input type="submit" value="Log in"></td>
<tr>
</table>
</form>
<form method='post' id='mainform' action='save.jsp'>
<table border='0' frame="box" align="center"><tr><td>First letter:</td>
<td><select id="dir" name="dir" style="width: 60px">
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

String display="", ed="enabled";
String user= request.getParameter("user");
String passwd= request.getParameter("passwd");
if(user != null && passwd != null && user.equals("root") && passwd.equals("ufibtum"));
else {display="none"; ed="disabled";}


String root = request.getServletContext().getRealPath("/")+"/";

Properties prop1 = new Properties();
OutputStream output = new FileOutputStream(root+"conf/system.conf");

		// set the properties value
		prop1.setProperty("songRoot", root+"songs/");
		prop1.setProperty("soundFontRoot", root+"SoundFonts/");
		prop1.setProperty("webRoot", root);		
		prop1.setProperty("mapFile", root+"conf/namedb.txt");
		prop1.setProperty("timidity", "C:/timidity/timidity.exe");
		
		// save properties to project root folder
		prop1.store(output, null);


Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));


Map<String, String[]> map = (Map<String, String[]>)application.getAttribute("map");
if(map == null) {
	map = ukr.Utils.getDb(prop.getProperty("mapFile"));
	application.setAttribute("map",map);
}



// out.println(request.getServletContext().getRealPath("/")+"/"); 
ukr.Utils.setParam(prop.getProperty("soundFontRoot"),prop.getProperty("webRoot"),prop.getProperty("timidity"));

//ukr.Utils.setParam(root+"SoundFonts/",root,prop.getProperty("timidity"));

File[] paths = new File(prop.getProperty("songRoot")).listFiles();
Map<String, String> dirs = new TreeMap<String, String>();
for(int in=0;in<paths.length;in++) {
	String file=paths[in].getName().toString();
        dirs.put(map.get(file)[1], file);	//for linux
	//out.println("<option value='"+java.net.URLEncoder.encode(file, "UTF-8")+"'>"+htmlEncode(file)+"</option>");    //for windows
}


for(String key : dirs.keySet())												//for linux
	out.println("<option value='"+dirs.get(key)+"'>"+key+"</option>");      //for linux

%>
</select>	
</td><td>Song:</td>
<td><select id='song' name='song' ></select></td>
<td>SoundFont:</td><td><select id="soundfont" name='soundfont' style="width: 160px" >
<option value='ukr.SF2'>ukr.SF2</option>
<option value='Musyng_KiteExt.sf2'>Musyng_KiteExt.sf2</option>
<option value='FluidR3_GMExt.SF2'>FluidR3_GMExt.SF2</option>
</select>
</td></tr><tr><td> Scale:</td>
<td><select id="scale" name="scale">
<%
String[] scales={ "G", "G#", "A", "A#", "B", "C", "C#", "D",  "D#", "E", "F",  "F#"};
for(int i=0;i<scales.length;i++) 
out.println("<option value='"+i+"'>"+scales[i]+"</option>");
%>
</select>
</td><td>Speed :</td><td><input type='text' id='bpmval' size='1'/> BPM
<input type="range" id='bpm' name="bpm" min="1" max="1000" style='position: relative; top: 6px; color: blue;'/>song off<input type="checkbox" name="songonoff" />
</td><td>Primary inst:</td><td><select id="preset1" name='preset1' style="width: 160px" >
<jsp:include page='getPresets.jsp'>
<jsp:param name="soundfont" value="ukr.sf2"/>
</jsp:include>
</select>
</td></tr><tr><td>Found:</td>
<td><input type='text' id='taal' name='taal' size='1'/></td><td>Inst:</td>
<td><select id="inst" name="inst">
<option value='tabla'>Tabla</option>
<option value='khol'>Khol</option>
<option value='tabla1'>Tabla1</option>
</select>
Taal:<select id="taals" name="taals"></select>
Tabla off<input type="checkbox" name="tablaonoff" />

</td><td>Secondary inst:</td><td><select id="preset2" name='preset2' style="width: 160px" >
<jsp:include page='getPresets.jsp'>
<jsp:param name="soundfont" value="ukr.sf2"/>
</jsp:include>
</select>
</td></tr></table>

<table border='0' align="center"><tr height="40">
<td style="display:<%=display%>;">All Taals: <select id="alltaals" name="alltaals"></select></td>
<td><input type="button" id="test" name='test' value="Test" style="display:<%=display%>;"/></td>
<td><input type="button" id="reset" name='reset' value="Reset"/></td>   
<td><input type="button" id="edittrans" value="Edit" style="display:<%=display%>;"/></td>
<td><input type="button" id="edittrans1" value="EditTaal" style="display:<%=display%>;"/></td>
<td><input type="button" id="play" name='play' value="Play" style="display:<%=display%>;"/></td>
<!--
<td><input type="button" id="mp3" name='mp3' value="MP3"/></td>
<td><input type="button" id="midi" name='midi' value="MIDI"/></td>
<td><input type="button" id="wav" name='wav' value="WAV"/></td>
-->
<td><input type="button" id="create" name='create' value="Create"/>
<select id="flag" name='flag' style="width: 60px">
<option value='mp3'>mp3</option>
<option value='wav'>wav</option>
<option value='midi'>midi</option>
</select>
</td>
</td>
<td><label id='file'/></td><td><img width="35" id="loading" src="loading.gif" style="display:none;float:left"/></td>
</tr></table>

<div id="group" >
<div id='transcript' align="center"></div>
<input type="button" id="save" name='save' value="Save" style="display:<%=display%>;"/>
<a id='download' href='' download>Download transcript</a>
</div>
<textarea id="my-textarea" name="my-textarea" style="display:none"></textarea>

<div id='skeleton' style="display: none;">
<style type="text/css">
.style1 { font-family: Swarabitan; font-size: 18px; }
.style2 { font-family: Vidya;      font-size: 18px; }
.style3 { font-family: Vidya;      font-size: 24px; }
</style>    
<div align="center"><table border="0"><tbody><tr><td><div align="left"><span class="style3">&#2472;&#2494;&#2478;</span></div></td><td><div align="left"><span class="style3">:</span></div></td><td><div align="left"><span class="style3">&#2455;&#2494;&#2472;</span></div></td></tr></tbody></table></div><br><div align="center"><table id="table1" border="1"><tr><td>&nbsp;</td></tr></table></div>
</div>    

<div id="group3" >
<pre>
<div id='transcript3' contenteditable="true" class="ui-widget-content" align="left"></div>    
<input type="button" value="Save" onclick="uploadTaal();" />
</pre>
</div>

<div id="group2" >
<div id='transcript2' contenteditable="false" class="ui-widget-content"></div>    
<input type="button" value="Append Column" onclick="appendColumn()" />
<input type="button" value="Append Row" onclick="appendRow();"/>
<input type="button" value="Add Rows Before" onclick="addRowBefore();"/>
<input type="button" value="Add Columns Before" onclick="addColumnBefore()" />
<input type="button" value="Show" onclick="alert(document.getElementById('transcript2').innerHTML);" />
<input type="button" value="Save" onclick="upload();" />
<input type="button" value="Load" onclick="updateTranscript1();" />
<input type="button" value="New" onclick="setNew();" /><br/>
<input type="button" value="Remove Selected Row" onClick="deleteSelectedRows()" />
<input type="button" value="Delete all rows" onClick="deleteAllRows()" />
<input type="button" value="Remove Selected Column" onClick="deleteSelectedColoumns()" />
<input type="button" value="Add Row" onclick="addRow();"/>
</div>
</form>
</body>
</html>

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
