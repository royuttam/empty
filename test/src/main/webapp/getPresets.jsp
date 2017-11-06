<%@page import="java.util.*,java.io.*,javax.sound.midi.*"%>
<%

response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

String soundfont = request.getParameter("soundfont");
Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));
Soundbank[] soundbanks = ukr.Utils.getAllSoundbanks(prop.getProperty("soundFontRoot")+soundfont);
		for(Soundbank  soundbank: soundbanks) {
			Instrument[] instruments = soundbank.getInstruments();
			for(Instrument instrument: instruments) {
			int bank = instrument.getPatch().getBank();
			int program = instrument.getPatch().getProgram();
			
			if(! instrument.toString().contains("Drumkit") && bank == 0)
	out.println("<option value='"+(program+1)+"'>"+(program+1)+". "+instrument.getName()+"</option>");
	
		}
		}

%>