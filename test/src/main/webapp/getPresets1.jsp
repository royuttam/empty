<%@page import="java.util.Properties,java.io.File,javax.sound.midi.Instrument"%>
<%@page import="javax.sound.midi.MidiSystem,javax.sound.midi.Soundbank"%>
<%

response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", -1);

String soundfont = request.getParameter("soundfont");
Properties prop = new Properties();
prop.load(getServletContext().getResourceAsStream("/conf/system.conf"));

Soundbank soundbank = MidiSystem.getSoundbank(new File(prop.getProperty("soundFontRoot")+soundfont));
Instrument[] instruments = soundbank.getInstruments();
	for(Instrument instrument: instruments) {
		int bank = instrument.getPatch().getBank();
		int program = instrument.getPatch().getProgram();
		
		if(! instrument.toString().contains("Drumkit") && bank == 0)
		out.println("<option value='"+(program+1)+"'>"+(program+1)+". "+instrument.getName()+"</option>");
		
	}
%>