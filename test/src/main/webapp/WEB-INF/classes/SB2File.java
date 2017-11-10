import javax.sound.midi.*;
import java.io.*;
import java.util.*;
class SB2File {
	public static void main(String args[]) throws Exception {
		Soundbank[] soundbanks = ukr.Utils.getAllSoundbanks(args[0]);
		for(Soundbank  soundbank: soundbanks) {
			Instrument[] instruments = soundbank.getInstruments();
			//PrintWriter pw = new PrintWriter(new FileOutputStream("out.txt"));
			for(Instrument instrument: instruments) {
				int bank = instrument.getPatch().getBank();
				int program = instrument.getPatch().getProgram();

				if(! instrument.toString().contains("Drumkit") && bank == 0)
				//System.out.println(program+" "+instrument.getName());		

				System.out.println("<option value='"+(program+1)+"'>"+(program+1)+". "+instrument.getName()+"</option>");

				//pw.println("|"+program+"|"+instrument.getName());		
				//System.out.println(instrument.getPatch().getProgram()+" "+instrument.getName());	
			}
			//pw.close();

		}
		/*
		Scanner scan = new Scanner(new FileInputStream("out.txt"));

		scan.useDelimiter("\\Z");
StringTokenizer st = new StringTokenizer(scan.next(),"|");
scan.close();int i=1;
while(st.hasMoreTokens()) {
	int no = Integer.parsest.nextToken();
	System.out.println("<option value='"+str+"'>"+str+". "+st.nextToken()+"</option>"); 
}
	*/

	}
}