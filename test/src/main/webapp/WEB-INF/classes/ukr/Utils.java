package ukr;
import edu.columbia.ee.csmit.MidiKaraoke.write.SimpleMidiWriter;
import it.sauronsoftware.jave.*;
import java.util.*;
import java.io.*;
import org.apache.commons.lang3.*;
import org.w3c.dom.*;
import javax.sound.midi.Soundbank;
import javax.sound.midi.MidiSystem;
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
		meta = new Meta(bpm,taal, inst1, inst2, scale);
	}
	public Meta meta;
}
//----------------------
class MidiInfo {
	Song song;
	Double[][] nmat; Integer[][] patch;
	MidiInfo(Song song, Double[][] nmat, Integer[][] patch) {
		this.song=song;this.nmat=nmat;this.patch=patch;
	}
}
class Info_t2nmat {
	Double[][] nmat;double offset, offset1;
	Info_t2nmat(Double[][] nmat,double offset, double offset1) {
		this.nmat = nmat;this.offset=offset;this.offset1=offset1;
	}
}
class Row_serialize {
	String b;double len, d;
	Row_serialize(String b, double len, double d) {
		this.b = b;this.len=len;this.d=d;
	}
}
class Row_normalize {
	String bol;double td, ampl; int scale;
	Row_normalize(String bol,double td, double ampl, int scale) {
		this.bol = bol;this.td=td;this.ampl=ampl;this.scale=scale;
	}
}
class Song {
	class Meta {
		public double pre = 0;
		public int bpm, scale;
		public String taal;
		public int inst1, inst2;
	}
	class Segment {
		String[] T = null;
		String T1, name = "noName";
		double duration;
		public Segment(String[] T, String name) {this.T=T;this.name=name;}
	}
	class Data {
		public Segment segment;
		public Data(Segment s) {this.segment=s;};
	}
	public Meta meta = new Meta();
	public Data[] data = null;
	public void appendSegment(String[] T, String name) {
		if(data!=null){
			Data[] temp = new Data[data.length+1];
			for (int i=0;i<data.length;i++)
			temp[i] = data[i];
			temp[temp.length-1]=new Data(new Segment(T,name));
			data=temp;temp=null;
		}
		else {
			data = new Data[1];
			data[0]=new Data(new Segment(T,name));
		}
	}
	void printData() {
		for(int i=0;i<data.length;i++) {
			System.out.println(data[i].segment.name);
			Utils.printArray(data[i].segment.T);
			System.out.println("\n");
		}
	}
	void printT1() {
		for(int i=0;i<data.length;i++) {
			System.out.println(data[i].segment.name);
			System.out.println(data[i].segment.T1);
			System.out.println("\n");
		}
	}
}
//----------------------------
public class Utils {
	static String[] sections = {"sthayi","antara","sanchari","abhoga","abhoga","abhoga","abhoga","abhoga","abhoga","abhoga"};
	
	static String[]   keyNames ={"<r","<R","<g","<G","<M","<m","<P","<d","<D","<n","<N","S", "r","R","g", "G","M", "m", "P", "d",  "D", "n", "N", "S>","r>","R>","g>","G>","M>","m>","P>","d>","D>","n>","N>"};
	static int[]      keyNos =  { 47,  48,  49,  50,  51,  52,  53,  54,  55,  56,  57,  58,  59, 60, 61,  62 ,63 , 64,  65,  66,   67,  68,  69,  70,  71 , 72,  73,  74 , 75 , 76,  77,  78 , 79 , 80,  81 };
	static String[]   keys =    {"B", "C", "C#","D", "D#","E", "F", "F#","G", "G#","A", "A#","B","C","C#","D","D#","E", "F", "F#", "G", "G#","A", "A#", "B","C", "C#","D", "D#","E", "F", "F#","G", "G#","A",  "A#"};
	//toKeyNo = containers.Map(Utils.keyNames,Utils.keyNos);
	static java.util.Map<String, Integer> toKeyNo= new java.util.HashMap<String, Integer>();   
	
	static String soundFontRoot="D:/Software/SoundFonts/";
	//static String soundFontRoot="/opt/apache-tomcat-7.0.59/webapps/compose2/";
	//static String soundFontRoot="/var/lib/openshift/56c73c5a2d5271142600017d/jbossews/webapps/";

	static String webRoot="E:/ajp/apache-tomcat-7.0.59/webapps/t2s/";
	//static String webRoot="/opt/apache-tomcat-7.0.59/webapps/t2s/";
	//static String webRoot="/var/lib/openshift/56c73c5a2d5271142600017d/jbossews/webapps/";	

	static String timidity = "C:/timidity/timidity.exe";
	//static String timidity = "/usr/local/bin/timidity";
	//static String timidity = "/var/lib/openshift/56c73c5a2d5271142600017d/app-root/runtime/dependencies/jbossews/bin/timidity";
	
	public static void init(){
		for(int i=0;i<keyNames.length;i++) toKeyNo.put(keyNames[i],keyNos[i]);
	}
	public static void setParam(String sf, String wr, String t) {
		soundFontRoot=sf;webRoot=wr;timidity=t;
	}
	public static void main(String args[]) throws Exception {
		//AmarPrenerManus.html
		//AgniBinaBajaoTumi.html
		//AliBarBarFireJay.html
		//AnekKothaBole.html
		//AiTobeSohoChari.html
		//AgeChlAgeChlBhai.html
		//AjDhanerKssheteRoudrchhayay.html
		//        process("/opt/apache-tomcat-7.0.59/webapps/compose2/songs/final/AiTobeSohoChari.html", 2, "khol", 3, 89, 180, "4444", "ukr.SF2", false,        false, "mp3");
		//String file = ukr.Utils.process("E:/nltr/out2/ready/AjDhanerKssheteRoudrchhayay.html", 2, "tabla", 3, 89, 180, "44v6", "ukr.SF2", false,        false, "play");
		init();
		setParam("D:/Software/SoundFont/","E:/ajp/apache-tomcat-8.0.15/webapps/t2snew/","C:/timidity/timidity.exe");
		//ukr.Utils.playInst("tabla",   "33v10|sthayi",    2,       180,     20,    "ukr.SF2");
		//String file = ukr.Utils.process("E:/nltr/out2/final/AgniBinaBajaoTumi.html.html", 2, "tabla", 3, 89, 140, "322v1", "ukr.SF2", false,        false, "play");  
		//String file = ukr.Utils.process("E:/nltr/out/ready/AgniBina1.html", 1, "tabla", 3, 89, 140, "322v1", "ukr.SF2", false,        false, "wav");  
		//String file = ukr.Utils.process("E:/nltr/out2/final/AmarPrenerManus.html.html", 2, "tabla", 3, 89, 180, "33v7", "ukr.SF2", false,        false, "play");  
		
		String file = ukr.Utils.process("E:/nltr/out2/A/AgeChlAgeChlBhai.html", 2, "tabla1", 3, 89, 180, "33v7", "ukr.SF2", false,        false, "mp3");
		String wav = "E:/ajp/apache-tomcat-8.0.15/webapps/t2snew/wav/A-dharElBle.wav", midi= "E:/ajp/apache-tomcat-8.0.15/webapps/t2snew/midi/A-dharElBle.mid";
		//midi2wav(midi,wav,"D:/Software/SoundFont/ukr.SF2");
		//wav2mp3("out.wav", "out.mp3");
		//wav2mp3("AgeChlAgeChlBhai.mid.wav", "out.mp3");
		
		
		//String file = ukr.Utils.process("E:/nltr/out2/A/AgeChlAgeChlBhai.html", 2, "tabla", 3, 89, 180, "33v8", "ukr.SF2", false,        false, "play");
		//new Tabla1(0);
		
		//Object[] res = searchTaal("/opt/apache-tomcat-7.0.59/webapps/compose2/songs/final/AiTobeSohoChari.html");
		//System.out.println(res[1]);
		
		//System.out.println(args[0].matches(args[1]));
		/*
		Object[] values = ukr.Utils.taals("tabla","33");        process
		StringTokenizer st=new StringTokenizer((String)values[1],"|");
String str="";
while(st.hasMoreTokens()) {
	String tk=st.nextToken();
	str=str+"<option value='"+tk+"'>"+tk+"</option>";
}

str=str+"#";
String[] tokens = (String[])values[0];
for(int i=0;i<tokens.length;i++){    
	String tk=tokens[i];
	str=str+"<option value='"+tk+"'>"+tk+"</option>";
}
System.out.println(str);
*/
		
		
		
		/*
		PercussionInstrument inst=new Tabla(0);
		//String[][] res = split(new String[]{"ghee.2"},inst.bols);
		//printArray(res[0]);
		//printArray(res[1]);        
		Object[] res = inst.tonmat("33v7|sthayi",66,0,false);
		Object[][] r=(Object[][])res[0];
		for(int i=0;i<r.length;i++) {
			for(int j=0;j<r[i].length;j++)
				System.out.print(r[i][j]+"\t");
				System.out.println();
		}
		*/ 
		
		
		//printArray(parts(args[0]));
		//System.out.println(bengToEng("abcd"));
		
		//printArray(parse("sa ga \"re ga ma\" \"ga re\"|dha dhin \"te te\""));
		//printArray(get("  sa re ga   "));
		
		//serialize(parse("sa ga - - \"re ga ma\" \"ga re\"|dha_2 dhin.2_3 \"te te -\""));
		//getInfo("na.2_3");
		
		//System.out.println(getStr(args[0],'[',']'));
		
		//String[] str={"sa", "sa", "l"};
		//printArray(removeInvalidNotes(str));
		//System.out.println(removeInvalidChar("l"));
		
		//String[]  tokens = tokenize(args[0]);
		//printArray(tokens);
		
		/* String[][] first = {{"sa"},{"ra"},{"ma"}};
		String[][] second = {{"pa","da"},{"na","ga"},{"sa", "ma"}};
		String[][] result = concat(first,second);
		printMatrix(result);*/
	}    
	//---function "searchTaal------------
	public static Object[] searchTaal(String path) throws Exception {
		String[][] mat=readHtml(path);
		return Utils.findTaal(mat);
	}
	//---function findTaal------------
	static Object[] findTaal(String[][] mat) {        
		int row=mat.length;
		String d="";int l=0;
		String[] delims={"L","LL","ll","llll","llL","Lll","l[]l","ll[]ll","L[]L"};                        
		for (int r=1;r<row;r+=4) {
			List<String> arow = new ArrayList<String>();
			Collections.addAll(arow, mat[r]);
			for (int i=0;i<delims.length;i++) {
				for(int j=0;j<arow.size();j++)
				if(arow.get(j).equals(delims[i]))
				arow.set(j,"l");
			}			
			List<Integer> f=new ArrayList<Integer>();
			for(int i=0;i<arow.size();i++) if(arow.get(i).equals("l")) {f.add(i);}
			for(int i=0;i<f.size()-1;i++) {                    
				List<String> seg = arow.subList(f.get(i), f.get(i+1)+1);
				int len=Collections.frequency(seg, "A");
				if (len > 0) {                        
					len=f.get(i+1)-f.get(i)-1-len;                        
					for(int in=0;in<seg.size();in++)
					if(seg.get(in).equals("A"))
					seg.set(in,"l");
					List<Integer> f1=new ArrayList<Integer>();
					for(int k=0;k<seg.size();k++) if(seg.get(k).equals("l")) {f1.add(k);/*System.out.println(k);*/}
					String div="";
					for(int j=0;j<f1.size()-1;j++)
					div=div+(f1.get(j+1)-f1.get(j)-1);
					if(len > l) {
						l=len;d=div;
					}
				}                   
			}                
		}
		return new String[]{""+l,d};
	}
	//---function "addInst------------
	static MidiInfo addInst(Song song, String instName) throws Exception {		
		double pre=song.meta.pre;
		int scale=song.meta.scale;
		if (scale >= 0)  scale=scale%12;
		else scale=scale%(-12);
		
		if (scale > 3)  scale=scale-7; 
		else if (scale < -3) scale=scale+7;
		//System.out.println("scale: "+scale);
		PercussionInstrument inst=null;
		switch (instName) {
		case "tabla":
			inst=new Tabla(scale);
			System.out.println("Tabla initialized");
			break;
		case "khol":
			inst=new Khol(scale);
			System.out.println("Khol initialized");
			break;
		case "tabla1":
			inst=new Tabla1(scale);
			System.out.println("Tabla1 initialized");
			break;
		}
		Double[][] nmat=null;  double excess=0;
		
		int in=song.data.length-1;
		while (in > 1 && song.data[in-1].segment.name.equals(sections[0])) in=in-1; 
		
		for (int i=0;i<song.data.length;i++) {			
			double sz=song.data[i].segment.duration;
			String sec=song.data[i].segment.name;
			//System.out.println("In addInst: sec= "+sec);
			
			if (sec.equals("sanchari") || sec.equals("abhoga")) sec="antara";
			if (i>=in && i > 1) sec="last";
			
			int amplFact = 0;
			if (sec.equals("repeat") || sec.equals("last")) amplFact=20;
			
			
			String tl=song.meta.taal + "|" + sec;
			
			if (i==0) sz=sz-pre;
			
			boolean exact = false;
			if (i==song.data.length-1) exact=true;
			
			Object[] pair = inst.tonmat(tl,sz-excess,pre,exact, amplFact);
			Double[][] mat = (Double[][])pair[0];
			double len = (Double)pair[1];
			pre=pre+len;
			nmat=concatRow(nmat,mat);
			excess=len-(sz-excess);                             
		}   
		Object[][] patch=inst.patch;
		return new MidiInfo(song,nmat,inst.patch);
	}
	//----------------------------------------------
	public static String process(String path, int scale, String inst,int preset1, int preset2,int bpm, String taal, String soundfont,boolean tablaonoff, boolean songonoff,String flag) {
	    //System.out.println("Scale = "+scale);
		String file="error";
		try {
			init();
			System.out.println("==="+path +" "+ scale+" "+inst+" "+preset1 +" "+preset2+" "+bpm+" "+taal+" "+soundfont+" "+ tablaonoff+" "+songonoff +" "+flag);
			switch(flag) {
			case "playInst":
				break;
			default:
				String midifile=Utils.createMidi1(path, scale, inst,preset1,preset2,bpm,taal,tablaonoff,songonoff);
				String fl = midifile.substring(midifile.lastIndexOf("/")+1,midifile.length()).split("\\.")[0];
				
				switch (flag) {
				case "play":
					System.out.println("Playing midi "+midifile);
					playMidi(midifile, soundFontRoot+soundfont);
					file=midifile;
					break;
				case "wav":
				    String wavfile = webRoot+"wav/"+fl+".wav";
					System.out.print("Converting " +midifile +" to "+wavfile+ " ....");
					midi2wav(midifile, wavfile, soundFontRoot+soundfont);
					System.out.println("Done");
					file="wav/"+fl+".wav";
					break;
				case "mp3":
				    String mp3file = webRoot+"mp3/"+fl+".mp3";
					System.out.print("Converting " +midifile +" to "+mp3file+ " ....");
					midi2mp3(midifile,mp3file, soundFontRoot+soundfont);
					System.out.println("Done");
					file="mp3/"+fl+".mp3";
					break;
					
				case "midi":
					System.out.println("Converting midi to wav");
					file="midi/"+fl+".mid";
					break;							
				}
			}
		}catch(Exception e) {e.printStackTrace(); return e.toString();}
		return file;
	}
	//----------------------------------------------
	static void midi2mp3(String midifile, String mp3file, String soundfont) throws Exception {
		String temp = "out.wav";
		midi2wav(midifile,"out.wav",soundfont);
		wav2mp3("out.wav",mp3file);
		//Process p = Runtime.getRuntime().exec("cmd /c del "+temp);
		//Process p = Runtime.getRuntime().exec("rm -rf "+temp);
	}
	//----------------------------------------------
	static void midi2wav(String midifile, String wavfile, String soundfont) throws Exception {
		/*
		java.io.PrintWriter FID  = new java.io.PrintWriter(new java.io.FileWriter("./myfile.cfg"));
		FID.println("soundfont "+soundfont);
		FID.close();
		String command = timidity+" -a -p 256\\(a\\) -U -A600 -EFchorus=2 -EFdelay=r -s44100 -c ./myfile.cfg   -o " +wavfile +" -OwS "+ midifile;        
		
		System.out.println(command);
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		*/	
		//System.out.println("in Utils::midi2wav()"+soundfont);
		Midi2WavRender.render(soundfont, midifile, wavfile);	
	}
	//----------------------------------------------
	static void wav2mp3(String wavfile, String mp3file) throws Exception {
		//jump3r-1.0.3.jar is needed
		/*
		String[] mp3Args = {"--preset","standard",
            "-q","0",
            "-m","s",
            wavfile,
            mp3file};
		mp3.Main m = new mp3.Main();
        m.run(mp3Args);		
		*/
		
		//jave-1.0.2.jar is needed
		
		File source = new File(wavfile);
		File target = new File(mp3file);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs);
		
	}
	//----------------------------------------------
	static void playMidi(String midifile,String soundfont) throws Exception  {
		String midiConf = webRoot+"myfile.cfg";
		java.io.PrintWriter FID  = new java.io.PrintWriter(new java.io.FileWriter(midiConf));
		FID.println("soundfont "+soundfont);
		FID.close();

		String command = timidity+" -int -a -p 256\\(a\\) -U -A600 -Ww -EFchorus=2 -EFdelay=b,30 -s44100 -c "+midiConf+" "+midifile;
		System.out.println("cmd /c start "+command);
		Process p = Runtime.getRuntime().exec("cmd /c start "+command);
		p.waitFor();
	}
	//----------------------------------------------
	static String createMidi1(String path, int scale, String inst,int preset1,  int preset2,int bpm,String taal,boolean tablaonoff, boolean songonoff) throws Exception {
		String str1 = path.substring(path.lastIndexOf("/")+1,path.length()).split("\\.")[0];
		String[][] mat=Utils.readHtml(path);
		Info info = new Info(bpm, taal, preset1, preset2,scale);
		String midifile=Utils.createMidi(bengToEng(str1),mat, inst, info, tablaonoff,songonoff);        
		return midifile;
	}
	//----------------------------------------------
	static String createMidi(String fl,String[][] mat, String instname, Info info, boolean tablaonoff, boolean songonoff) throws Exception {
		String[][] seq=Utils.mat2seq(mat);
		Song song = Utils.seq2t(seq);
		song.meta.bpm=info.meta.bpm;
		song.meta.taal=info.meta.taal;
		song.meta.scale=info.meta.scale;
		song.meta.inst1=info.meta.inst1;
		song.meta.inst2=info.meta.inst2;
		song = transform(song);
		String midifile=song2midi(fl,song,instname,tablaonoff,songonoff);
		return midifile;
	}
	//----------------------------------------------
	static String[][] mat2seq(String[][] mat) {
		String[][] mat1 = null;
		for (int i = 0; i < mat.length; i+=4) {
			for (int j = 0; j < mat[i].length; j++) {
				if(!mat[i+1][j].equals("")) {
					String[][] str = {{mat[i][j]},{mat[i+1][j]},{mat[i+2][j]},{mat[i+3][j]}};
					mat1=concatCol(mat1,str);
				}
			}
		}
		return mat1;
	}
	//----------------------------------------------
	static Song seq2t(String[][] s) {
		System.out.println("Start seq2t.m.........., processing "+s.length+"x"+s[0].length+" array");
		String[] tn=null, tv=null,repeatseg=null;String state="start",sectionName,note;
		int c=0;boolean box=false;
		int last=0,nstanza=0;
		int itnNo=0; boolean success=true;
		Song song = new Song();
		int start=-1,in=-1,gotoloc=-1,loc=-1,lag;
		while (c<s[0].length)    {
			itnNo=itnNo+1;if (itnNo > 10000) {success=false;break;}
			String str=s[1][c];
			str=Utils.removeUnknownChar(str);
			
			switch (state) {
			case "start":
				switch (str) {
				case "L": case"ll":
					state="asthayi";  start=c;
					System.out.println("Start of stanza:: "+nstanza);
					song.meta.pre=calculatePre(tn);
					tv=removeInvalidNotes(tn);					
					if (tv != null && tv.length > 0){
						song.appendSegment(tv,sections[nstanza]);
						tn=null;
					}					
					//song.meta.pre=8;
					break;
					
				default:
					tn=concat(tn, str);
				}
				break;
			case "start1":
				switch(str) {
				case "L":case "ll":
					System.out.println("Start of stanza- " +nstanza);
					state="asthayi";
					box=false;
				default:
					
					if(str.indexOf('{') >=0) {
						state="{"; c=c-1;in=c;
					}else
					tn=concat(tn, str);
					
				}
				break;
			case "asthayi":
				
				switch (str) {
				case "L": case "ll":case "l[]l":
					state="asthayirepeat";
					gotoloc=c;         //save location to be come back to
					c=start;        //repeat from first stanza
					
					System.out.println("End of stanza: "+nstanza);  //to  be removed
					
					repeatseg=null;   //reset repeat seq
					box = str.indexOf("[]") >=0;//~isempty(strfind(str,'[]'));
					
					if (last==1) sectionName=Utils.sections[0];
					else sectionName=Utils.sections[nstanza];
					tv=removeInvalidNotes(tn);
					song.appendSegment(tv,sectionName);
					tn=null;nstanza=nstanza+1;
					break;
					
				case "LL":case "llll": case "Lll": case "llL": case "L[]L": case "ll[]ll": case "L[]ll": case "ll[]L":
					System.out.println("End of stanza:: " +nstanza);
					tv=removeInvalidNotes(tn);
					song.appendSegment(tv,Utils.sections[nstanza]);
					tn=null;
					box=str.indexOf("[]") >=0;
					
					if (s[0][c-1].equals("|")) {
						state="stop";
						System.out.println("last--------");
					}
					else {
						last=1; c=start;
						if (nstanza==0) state="asthayirepeat";
						nstanza=nstanza+1;
						System.out.println("Start of stanza:: "+ nstanza);
					}
					break;
					
				default:
					if (str.indexOf("{") >=0) {//~isempty(strfind(str,'{'))
						state="{"; c=c-1;in=c;
					}
					else {
						if (box==false) tn=concat(tn, str);
						else tn=concat(tn, val(s[0][c],str));
					}
				}
				break;
				
			case "asthayirepeat":
				String up=s[0][c];
				if(up.indexOf('|') >=0 || str.equals("ll") || str.equals("L") || str.equals("l[]l")) {
					if (last!=1) {state="start1"; loc=c+1;c=gotoloc;
						tn=concat(tn,str); repeatseg=concat(repeatseg,str);
					} else {
						state="stop";
						tn=concat(tn,str); repeatseg=concat(repeatseg,str);
						System.out.println("stop");
					}
					tv=removeInvalidNotes(tn);
					song.appendSegment(tv,Utils.sections[0]);
					tn=null;
					
					if (last != 1) {
						lag=0;
						int tmp=loc; note=s[1][tmp];
						while(note.indexOf('l') <0 && note.indexOf('L') <0 && note.indexOf("ll") <0) {
							if (note.indexOf('(') >=0) {
								while(note.indexOf(')') <0) {
									note=s[1][tmp];
									tmp=tmp+1;
								}
								tmp=tmp+1;note=s[1][tmp];
							}
							if(!removeInvalidChar(note).equals("")) {
								lag=lag+1;
							}
							tmp=tmp+1; note=s[1][tmp];
						}
						tmp=gotoloc;int cnt=0;
						while (cnt < lag){
							note=s[1][tmp];
							if(!removeInvalidChar(note).equals("")) {
								repeatseg = concat(note,repeatseg);
								cnt=cnt+1;
							}
							tmp=tmp-1;
						}
						tv=removeInvalidNotes(repeatseg);
						song.appendSegment(tv,"repeat");
						repeatseg=null;
					}
				}else {
					//%open it for AdinAjiKonGhareGo
					//%skip (....) section
					//%close it for SeDinDujneDulechhinuBne
					String tmp=str;
					if(tmp.indexOf('(') >=0) {
						while(tmp.indexOf(')') <0) {
							c=c+1;
							tmp=s[1][c];
						}
						c=c+1;
						str=s[1][c];
					}
					if (box==false) {
						tn=concat(tn,str); repeatseg=concat(repeatseg,str);
					}else {
						tn=concat(tn, val(s[0][c],str));
						repeatseg=concat(repeatseg, val(s[0][c],str));
					}
				}
				break;
			case "{":
				if(str.indexOf('(') >=0) {
					state="{(";c=c-1;
				}else if (str.indexOf('}') >=0) {
					state="{}";c=c-1;
				}else
				if (box==false) tn=concat(tn, str);
				else tn=concat(tn, val(s[0][c],str));
				
				break;
			case "{}":
				if (box==false) tn=concat(tn,str);
				else tn=concat(tn, val(s[0][c],str));
				c=in; state="{}{";
				break;
				
			case "{}{":
				if(str.indexOf('}') >=0)  state="asthayi";
				tn=concat(tn, val(s[0][c],str));
				break;
				
			case "{(":
				if(str.indexOf(')') >=0) {
					state="{()";c=c-1;
				}else if (box==false) tn=concat(tn, str);
				else tn=concat(tn, val(s[0][c],str));
				break;
				
			case "{()":
				if(str.indexOf('}') >=0) {
					state="{()}"; c=c-1;
				}else   {if (box==false) tn=concat(tn, str);
					else tn=concat(tn, val(s[0][c],str));
				}
				break;
				
			case "{()}":
				if (box==false) tn=concat(tn, str);
				else tn=concat(tn, val(s[0][c],str));
				state="{()}{";c=in;
				break;
				
			case "{()}{":
				if(str.indexOf('(') >=0){
					state="{()}{(";c=c-1;
				}else
				tn=concat(tn, val(s[0][c],str));
				break;
				
			case "{()}{(":
				if(str.indexOf(')') >=0) {
					state="{()}{()";c=c-1;
				}
				break;
			case "{()}{()":
				if(str.indexOf('}') >=0) {
					state="asthayi";
					if(str.indexOf(')') <0)
					tn=concat(tn, str);
				}else  if(str.indexOf(')') <0)
				tn=concat(tn, val(s[0][c],str));
				
				break;
			}
			c=c+1;
		}
		//song.printData();
		if(!success) System.out.println("In seq2t: Failed to read successfully");
		return song;
	}
	//----------------------------------------------
	static String song2midi(String fl,Song song,String instname,boolean off,boolean songonoff) throws Exception{
		int arr[] = {song.meta.inst1, song.meta.inst2};
		MidiInfo mi=t2midi(song, arr);
		if (songonoff)
		mi.nmat=null;		
		if (!off) {			
			MidiInfo info =addInst(song,instname);
			//info.nmat=modify(mi.nmat, info.nmat);
			mi.nmat=concatRow(mi.nmat,info.nmat);
			mi.patch=concatRow(mi.patch,info.patch);
		}
		mi.nmat=attenuate(mi.nmat);
		String midifile=Utils.webRoot +"midi/"+ fl +".mid";
		writemidi_java(mi.nmat, midifile ,300, song.meta.bpm,3,4, mi.patch);
		/*
		System.out.println("in song2midi()");
		for(int row=0;row<mi.patch.length;row++) {
			for(int col=0;col<mi.patch[row].length;col++)
				System.out.print(mi.patch[row][col]+" ");
			System.out.println();
		}
			*/
		
		return midifile;
	}
	//----------------------------------------------
	static Double[][] modify(Double[][] smat, Double[][] tmat) {
	  System.out.println(smat.length+"\t"+tmat.length);
	  int j=0;
	  for(int i=0;i<tmat.length;i++) {
	    if(tmat[i][2] >= 5 && tmat[i][2] <= 5) {
		  while(j < smat.length && smat[j][0] <= tmat[i][0]) j++;
		  if(Math.abs(tmat[i][0]-smat[j-1][0]) < 0.001 && smat[j-1][2] == 1) {
		  double scale = (smat[j-1][3]-63)%12;
		  //if (scale < 7 && scale > -6) {
		  System.out.println(tmat[i][0]+"\t"+smat[j-1][0]+"\t"+tmat[i][3]+"\t"+smat[j-1][3]+"\t"+(smat[j-1][3]-63)%12);
		    tmat[i][3] = tmat[i][3]+scale;
		  //}
		  
		  }
		  }
	  }
	  return tmat;
	}
	//----------------------------------------------
	static MidiInfo t2midi(Song song, int[] inst) {	
		Double[][] nmat=null; Integer[][] patch=null;
		double time=0, time1=song.meta.pre;
		for (int i=0;i<song.data.length;i++) {
			double pre;
			if (i==0) pre=song.meta.pre;else pre=0;
			int type;
			if(Arrays.asList(Utils.sections).contains(song.data[i].segment.name))type=0; else type=1;
			Info_t2nmat v =t2nmat(song.data[i].segment.T1, time,  type+1, song.meta.scale, time1, pre);
			song.data[i].segment.duration=v.offset;
			time=time+v.offset;
			time1=time1+v.offset1;
			nmat = concatRow(nmat,v.nmat);
			patch=concatRow(patch, new Integer[][] {{type+1, 1, inst[type], 1}});
		}
		System.out.println("in t2midi "+nmat.length);
		patch=concatRow(patch,new Integer[][] {{3,1,50,1},{4,1,6,1}});
		//------Add chick chick------------------------------
		String tb=song.meta.taal.split("v")[0];		
		double t=song.meta.pre;int k=0;
		while (t+2 <= time) {
			nmat=concatRow(nmat, new Double[][] {{t+1, 1.0, 10.0, 70.0, 70.0, 1.0}});		//70 chick
			/*
			nmat=concatRow(nmat, new Double[][] {{t, 1.0, 10.0, 36.0, 127.0, 1.0}});		//36 drum
			nmat=concatRow(nmat, new Double[][] {{t+1, 1.0, 10.0, 81.0, 80.0, 1.0}});		//39 
			nmat=concatRow(nmat, new Double[][] {{t+2, 1.0, 10.0, 70.0, 80.0, 1.0}});		//39 
			*/
			t=t+tb.charAt(k)-48;
			k=(k+1)%tb.length();
		}
		//------------------------------------
		return new MidiInfo(song, nmat,patch);
	}
	//----------------------------------------------
	static Info_t2nmat t2nmat(String T, double startTime, int chan, int scale, double start1, double pre) {
	    //System.out.println("In t2nmat:\n"+T);
		Row_normalize[] Y=Utils.serialize(parse(T));
		double offset=0;		
		Double[][] nmat = null;
		for (int i=0;i<Y.length;i++) {
			String keyName=Y[i].bol;
			if (!keyName.equals("-")) {
				int keyNo=toKeyNo.get(keyName)+scale;
				nmat=concatRow(nmat, new Double[][] {{startTime+offset, Y[i].td, new Double(chan), new Double(keyNo), 127.0, 1.0}});
			}
			offset=offset+Y[i].td;
		}
		//Utils.printMatrix(nmat);
		//--------------------------
		String[][] Y1=t2nmat1(Y,pre);
		double offset1=0;
		for (int i=0;i<Y1.length;i++) {
			String keyName=(String)Y1[i][0];
			if (!keyName.equals("-")) {
				int keyNo=toKeyNo.get(keyName)+scale;
				nmat=concatRow(nmat, new Double[][] {{offset1+start1, Double.parseDouble(Y1[i][1]), 3.0, keyNo+12.0, 40.0, 1.0}});
			}
			offset1=offset1+Double.parseDouble(Y1[i][1]);
		}
		//--------------------------
		return new Info_t2nmat(nmat,offset,offset1);
	}
	//----------------------------------------------
	static String[][] t2nmat1(Row_normalize[] Y,double lag) {
		int i=0;double d=0;
		while (d < lag) {
			d=d+Y[i].td;
			i=i+1;
		}
		String[][] Z = {};
		while (i < Y.length){
			String key=Y[i].bol;
			double dur=Y[i].td;
			i=i+1;
			while (i < Y.length && key.equals(Y[i].bol)) 
			dur=dur+Y[i++].td;			
			Z=concatRow(Z,new String[][] {{key, String.valueOf(dur)}});
		}
		return Z;
	}
	//----------------------------------------------	
	static String[] parse( String bols ) {
		//System.out.println("In parse()\t"+bols);
		java.util.StringTokenizer seg = new java.util.StringTokenizer(bols.trim().replaceAll("\\s+"," "),"|");
		String[] Y=null;
		while (seg.hasMoreTokens()) {
			bols = seg.nextToken();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(int i = 0; i < bols.length(); i++)
			if(bols.charAt(i) == '"') list.add(i);
			
			int[] v = new int[list.size()];
			int j=0;
			for(Integer in:list) {
				v[j++]=in;
			}
			int l=v.length;
			if(l>=1) {
				Y=concat(Y,get(bols.substring(0,v[0])));
				for(int i=0;i<l;i+=2) {
					Y=concat(Y,bols.substring(v[i]+1,v[i+1]));
					if (i+2 < l)
					Y=concat(Y, get(bols.substring(v[i+1]+1,v[i+2])));
					
				}
				Y=concat(Y,get(bols.substring(v[l-1]+1,bols.length())));
			}
			else
			Y=concat(Y,Utils.get(bols));
		}
		return Y;
	}
	//----------------------------------------------
	static Row_normalize[] serialize(String[] seq) {
		Row_serialize[] Y = {};
		int in=0;
		for (int i=0;i<seq.length;i++) {
			String[] bol=seq[i].trim().split(" ");
			int nn=0;
			double td=0;
			for (int k=0;k<bol.length;k++) {
				double d=0;
				String b=bol[k];
				int from=b.indexOf('[');
				if(from != -1) {
					int to=b.indexOf(']');
					d=Double.parseDouble(b.substring(from+1,to));
					b=b.substring(0,from);
					td=td+d;
				}
				else nn=nn+1;
				Y=concat(Y,new Row_serialize[] {new Row_serialize(b, bol.length, d)});
			}
			for (int k=0;k<bol.length;k++) {
				if(bol[k].indexOf('[') == -1) {
					Y[in+k].d=(1-td)/nn;
				}
			}
			in=in+bol.length;
		}
		return normalize(Y);
	}
	//----------------------------------------------
	static Row_normalize[] normalize(Row_serialize[] mat)     {
		int count=0;
		Row_normalize[] Y={};
		while (count < mat.length) {
			String bol=mat[count].b;
			double td=mat[count].d;
			Object[] arr=getInfo(bol);
			count=count+1;
			while (count < mat.length &&  mat[count].b.equals("-")) {
				td=td + mat[count].d;
				count=count+1;
			}
			Y=concat(Y,new Row_normalize[] {new Row_normalize((String)arr[0], td, (Double)arr[1], (Integer)arr[2])});
		}
		return Y;
	}
	
	
	//----------------------------------------------
	static  Object[] getInfo(String bol) {
		double ampl=1; int scale=0;
		String[] b1 = bol.split("_");
		if(b1.length > 1) scale=Integer.parseInt(b1[1]);
		b1=b1[0].split("\\.");
		bol=b1[0];
		if (b1.length > 1) ampl=Integer.parseInt(b1[1]);
		return new Object[] {bol, new Double(ampl), new Integer(scale)};
	}
	//----------------------------------------------		
	static Double[][] attenuate(Double[][] nmat) {
	  System.out.println(nmat[nmat.length-1][0]);
	  double sec=15;
	  Double max = nmat[nmat.length-1][0];
	  for(int i=0;i<nmat.length;i++)
	    if(nmat[i][0] > max-sec) {
		   double vel  = nmat[i][4] *(max - nmat[i][0])/sec;
		   if (vel <=0) vel=1;
		   nmat[i][4] = vel;
		}
	return nmat;
	}
	//----------------------------------------------	
	public static Object[] taals(String instName,String tl) {
		tl=tl.trim();
		//System.out.println(instName+"\t"+tl);
		Map<String, String[]> taals=null;
		switch (instName) {
		case "tabla":
			taals=Tabla.getTaals();
			
			break;
		case "khol":
			taals=Khol.getTaals();
			//System.out.println(taals);
			break;
		case "tabla1":
			taals=Tabla1.getTaals();break;
		}
		//System.out.println(instName+"\t"+tl+"\t"+taals);
		List<String> ks = new ArrayList<String>(taals.keySet());		
		Set<String> ks1    = new HashSet<String>();            
		List<String> ks2 = new ArrayList<String>();		
		List<String> tls = new ArrayList<String>();
		for(String key: ks) {
			ks1.add(key.substring(0,key.indexOf('|')));             
			
			if (key.matches("^"+tl+"(v.*|\\|.*)")) {
			//System.out.println(key+"\t"+key.matches("^"+tl+"(v.*|\\|.*)"));
			ks2.add(key);
			}
		}

		String taal=tl.split("v")[0];
		for(String key: ks1)
		if (key.matches("^"+taal +"(v.*|$)")) 
		tls.add(key);

		int index=1;
		if (tls.isEmpty()) {tls.add("Not found");index=1; }
		else {
			index = tls.indexOf(tl);
			if(index <0) index=1;
		}
		String tls1="";
		for(String key: tls) tls1=tls1+key+"|";
		tls1=tls1.substring(0,tls1.length()-1);
		return new Object[]{ks2.toArray(new String[tls.size()]), tls1};
	}
	//----------------------------------------------
	static void printMatrix(Object[][] arr) {
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++)
			System.out.print(arr[i][j]+"\t");
			System.out.println();
		}
	}
	//----------------------------------------------
	static void writemidi_java(Double[][] nm, String filename, int tpq, int tempo, int tsig1, int tsig2, Object[][] patches) throws Exception{
		double microseconds_per_minute = Math.pow(10,6)*60;
		int microseconds_per_quarter_note = (int)(microseconds_per_minute/tempo);
		
		tsig2 = (int)((Math.log(tsig2) / Math.log(2)));
		int[] tsig = {tsig1, tsig2, 32, 8,};
		
		double[] onsets = new double[nm.length];
		for(int i=0;i<nm.length;i++) onsets[i] = nm[i][0];

		double[] duration = new double[nm.length];
		for(int i=0;i<nm.length;i++) duration[i] = nm[i][1];
		
		int[] channel = new int[nm.length];
		for(int i=0;i<nm.length;i++) channel[i] = nm[i][2].intValue()-1;
		
		int[] pitch = new int[nm.length];
		for(int i=0;i<nm.length;i++) pitch[i] = nm[i][3].intValue();
		
		int[] velocity = new int[nm.length];
		for(int i=0;i<nm.length;i++) velocity[i] = nm[i][4].intValue();
		
		int[] tracks = new int[nm.length];
		int[][] pm=new int[patches.length][patches[0].length];
		for(int i=0;i<pm.length;i++) 
		for(int j=0;j<pm[i].length;j++) 
		pm[i][j] = (Integer)patches[i][j];

		int[][] patches1 = pm;
		
		if (nm[0].length == 6)
		//write it if there is...
		for(int i=0;i<nm.length;i++) tracks[i] = ((Double)nm[i][5]).intValue()-1;

		else {
			// otherwise put everything on track zero
			for(int i=0;i<nm.length;i++) tracks[i] = 0;
			//and specify that all the patches are for track zero
			patches1=new int[pm.length][pm[0].length+1];
			for(int i=0;i<pm.length;i++) patches1[i][0] = pm[i][0];
			
			for(int i=0;i<pm.length;i++) patches1[i][1] = 1;
			for(int i=0;i<pm.length;i++)
			for(int j=1;j<pm[i].length;j++)
			patches1[i][1+j] = pm[i][j];
		}
		pm=patches1;
		//see if there is bank information....
		if(pm[0].length < 4) {
			patches1=new int[pm.length][pm[0].length+1];
			for(int i=0;i<pm.length;i++)
			for(int j=0;j<pm[i].length;j++)
			patches1[i][j] = pm[i][j];
			//otherwise put everything on bank zero
			for(int i=0;i<patches1.length;i++) patches1[i][patches[0].length-1] = 1;
		}
		pm=patches1;
		for(int i=0;i<pm.length;i++) 
		for(int j=0;j<pm[i].length;j++) 
		pm[i][j] = pm[i][j]-1;

		SimpleMidiWriter.write(filename,onsets,duration, channel, pitch, velocity, tracks,microseconds_per_quarter_note,  tpq, tsig,pm);
	}
	//----------------------------------------------
	static Song transform(Song song) {
		for(int n=0;n<song.data.length;n++) {
		song.data[n].segment.T1=convert(song.data[n].segment.T);
		//Utils.printArray(song.data[n].segment.T);
		//System.out.println(song.data[n].segment.T1);
		}
		return song;
	}
	//----------------------------------------------
	static String convert(String[] tv) {
		String[] keySet=  {"-","vh","rh","th","gh","mh","kh","ph","dh","qh","uh","nh","s","v","r","t","g","m","k","p","d","q","u","n","sf","vf","rf","tf","gf","mf","kf","pf","df","qf","uf","nf"};
		String[] valueSet={"-","<r","<R","<g","<G","<M","<m","<P","<d","<D","<n","<N","S","r","R","g","G","M","m","P","d","D","n","N","S>","r>","R>","g>","G>","M>","m>","P>","d>","D>","n>","N>"};
		java.util.Map map= new java.util.HashMap();
		for(int i=0;i<keySet.length;i++) map.put(keySet[i],valueSet[i]);		
		double td=0.1;
		String X="SVRTGMKPDQUN";
		String trans="";
		for (int i=0;i<tv.length;i++) {
			String[] tk=tokenize(tv[i]);
			int nn=0;
			for (int k=0;k<tk.length-1;k++)
			if(X.indexOf(tk[k]) <0)  nn=nn+1;			
			String ch;
			if (tk.length > 2) ch="\""; else ch="";
			String beat=ch;
			int l=tk.length-1;
			double bd=beatDuration(tk[tk.length-1])-(l-nn)*td;
			for(int j=0;j<l;j++) {
				String note=tk[j];
				if(X.indexOf(note) <0) {
					if (bd != 1)
					beat =beat +map.get(note.toLowerCase()) +"[" +(bd/nn) +"]  ";
					else
					beat =beat +map.get(note.toLowerCase())+" ";
				}else
				beat =beat +map.get(note.toLowerCase()) +"[" +td+ "] ";
			}			
			trans=trans +" "+ beat.trim()+ ch;
		}
		return trans.trim();
	}
	//----------------------------------------------	
	static String removeInvalidChar(String s) {
		String x="svrtgmkpdqunSVRTGMKPDQUNhfHF-ai";
		String Y="";
		//s=s.replaceAll("\\s+","");
		for (int i=0;i<s.length();i++)
		if (x.indexOf(s.charAt(i)) != -1)
		Y=Y+s.charAt(i);
		return Y;
	}
	//----------------------------------------------
	static String[] removeInvalidNotes(String[] tn) {
		String[] t=null;
		if(tn != null)
		for (int i=0;i<tn.length;i++) {
			String str = removeInvalidChar(tn[i]);
			if(!str.equals("")) t=concat(t,str);
		}
		return t;
	}
	//----------------------------------------------
	static String removeUnknownChar(String s) {
		String x="svrtgmkpdqunSVRTGMKPDQUNhfHF-aiA|lL[]{}()";
		String Y="";
		//s=s.replaceAll("\\s+","");
		for (int i=0;i<s.length();i++)
		if (x.indexOf(s.charAt(i)) != -1)
		Y=Y+s.charAt(i);
		return Y;
	}
	//----------------------------------------------
	static String getStr(String str, char open, char close) {
		String Y;
		if(str.indexOf(open) >=0 && str.indexOf(close) <0)
		Y=str.substring(str.indexOf(open)+1,str.length());
		else if(str.indexOf(open) >=0 && str.indexOf(close) >=0)
		Y=str.substring(str.indexOf(open)+1,str.indexOf(close));
		else if(str.indexOf(open) <0 && str.indexOf(close) >=0)
		Y=str.substring(0,str.indexOf(close));
		else
		Y=str;
		return Y;
	}
	//----------------------------------------------
	static String val(String up,String str) {
		String s = removeInvalidChar(getStr(up, '[',']'));
		String[] tokens = tokenize(s);
		if(tokens != null) return up;
		else return str;
	}
	//----------------------------------------------
	static String[] concat(String[] array, String a) {
		return concat(array,new String[] {a});
	}
	//--------------------------------------------------------------------	
	static String[] concat(String a, String[] array) {
		String[] s = {a};
		return concat(s,array);
	}
	//----------------------------------------------
	static<T> T[] concat(T[]... arrays) {
		int length = 0;
		for (T[] array : arrays) {
			if(array !=null)
			length += array.length;
		}
		T[] result = (T[])java.lang.reflect.Array.newInstance(arrays.getClass().getComponentType().getComponentType(), length);
		int pos = 0;
		for (T[] array : arrays) {
			if(array !=null)
			for (T element : array) {
				result[pos] = element;
				pos++;
			}
		}
		return result;
	}
	//----------------------------------------------
	static<T> T[][] concatRow(T[][]... matrices) {
		int rows = 0,cols=0;
		T[][] e =null;
		for (T[][] matrix : matrices) {
			if(matrix !=null && matrix.length != 0) {
				e = matrix;
				rows += matrix.length;
				cols=matrix[0].length;
			}
		}
		if(rows == 0 && cols == 0) return null;
		else {
			T[][] result = (T[][])java.lang.reflect.Array.newInstance(e[0][0].getClass(), rows, cols);
			int row = 0;
			for (T[][] matrix : matrices) {
				if(matrix !=null && matrix.length != 0) {
					int r = 0;
					for (T[] element : matrix) {
						int col=0;
						for (T ele : element) 
							result[row+r][col++] = ele;						
						r++;
					}
					row=row+matrix.length;
				}
			}
			return result;
		}
	}
	//--------------------------------------------------------------------                
	static<T> T[][] concatCol(T[][]... matrices) {
		int cols = 0,rows=0;
		T[][] e =null;
		for (T[][] matrix : matrices) {
			if(matrix !=null && matrix.length != 0) {
				e = matrix;
				cols += matrix[0].length;
				rows=matrix.length;
			}
		}
		if(rows == 0 && cols == 0) return null;
		else {
			T[][] result = (T[][])java.lang.reflect.Array.newInstance(e[0][0].getClass(), rows, cols);			
			int c=0;
			for (T[][] matrix : matrices) {
				if(matrix !=null)                {
					int r=0;
					for (T[] row : matrix) {
						int c1=c;
						for (T element : row)
							result[r][c1++] = element;
						r++;
					}
					c=c+matrix[0].length;
				}
			}
			return result;
		}		
	}
	//--------------------------------------------------------------------                	
	static String[] tokenize(String s) {
		String x="svrtgmkpdqun", X="SVRTGMKPDQUN",d="ai";
		if(s==null) s="";
		int len=s.length(),c=0, state=0; String[] tokens=null; char token=' ';
		String time="";boolean hyphen=false,accepted;
		if (s.equals("a"))   s="-"+s;
		while (c<len){
			char ch=s.charAt(c);
			switch (state) {
			case 0:
				token=' ';
				if(x.indexOf(ch) >=0) {
					token=ch;state=1;hyphen=false;
				}
				else if(X.indexOf(ch) >=0) {
					token=ch;state=1;
				}
				else if(d.indexOf(ch) >=0) time=time+ch;
				else if (ch == '-') hyphen=true;
				else state = -1;
				break;
			case 1:
				if("HF".indexOf(ch) >=0 || "hf".indexOf(ch) >=0)  {
					state=2;
					tokens=concat(tokens, ""+token+ch); token=' ';
				}
				else if(x.indexOf(ch) >=0) {
					
					tokens=concat(tokens, ""+token);
					token=ch;hyphen=false;
				}
				else if(X.indexOf(ch) >=0) {
					tokens=concat(tokens, ""+token);
					token=ch;
				}
				else if(d.indexOf(ch) >=0) {
					time=time+ch;
				}
				else if(ch == '-') hyphen=true;
				else state = -1;
				break;
			case 2:
				if("HF".indexOf(ch) >=0 || "hf".indexOf(ch) >=0) ;
				else if(x.indexOf(ch) >=0) {
					state=1; token=ch; hyphen=false;
				}
				else if(X.indexOf(ch) >=0) {state=1; token=ch;}
				else if(d.indexOf(ch) >=0) time=time+ch;
				else if(ch == '-') hyphen=true;
				else state=-1;
				break;
			}
			c=c+1;
		}
		
		if (token != ' ')
		tokens=concat(tokens, ""+token);
		
		if (hyphen ==true) tokens=concat("-", tokens);
		
		if (state ==-1 || s.equals("")) accepted=false;
		else accepted=true;
		
		if (accepted) {
			if (time.equals("")) time="a";
			tokens=concat(tokens,time);
		}
		
		if(tokens!=null)
		len=tokens.length;
		else len=0;
		
		if (len==2) {
			token=tokens[1].charAt(0);
			if(X.indexOf(token) >=0){
				String tmp=tokens[2];
				tokens[2]="-";
				tokens=concat(tokens,tmp);
			}
		}
		return toEqualCase(tokens,X);
	}
	//----------------------------------------------
	static void printArray(String[] a) {
		if(a!=null) 
		for(int i=0;i<a.length;i++)
		System.out.print(a[i]+"\t");
		else
		System.out.print("null array");
	}
	//----------------------------------------------
	static String[] toEqualCase(String[] tokens, String X) {
		String[] Y=null;
		if (tokens != null) {
			for (int i=0;i<tokens.length;i++) {
				String tk=tokens[i];
				if(tk.length() ==2){
					if(X.indexOf(tk.charAt(0)) >=0)
					tk=tk.toUpperCase();
					else
					tk=tk.toLowerCase();
				}
				Y=concat(Y,tk);
			}
		}
		return Y;
	}
	//----------------------------------------------
	static double calculatePre(String[] tn){
		double Y=0;
		tn=removeInvalidNotes(tn);
		if(tn != null)
		for (int i=0;i<tn.length;i++) {
			String[] t=tokenize(tn[i]);
			Y=Y+beatDuration(t[t.length-1]);
		}
		return Y;
	}
	//----------------------------------------------
	static double beatDuration(String dstr) {
		double Y=0;
		for (int i=0;i<dstr.length();i++)
		if(dstr.charAt(i) == 'a') Y=Y+1;
		else if(dstr.charAt(i) == 'i') Y=Y+0.5;
		return Y;
	}
	//----------------------------------------------	
	static String[][] readHtml(String path) throws java.io.IOException{
		java.util.Scanner scan = new java.util.Scanner(new java.io.InputStreamReader(new java.io.FileInputStream(path), "UTF-8"));
		scan.useDelimiter("\\Z");
		String str=scan.next();
		scan.close();
		return Utils.htmlstr2mat(Utils.removeNonASCII(str));
	}
	//----------------------------------------------
	static String[][] htmlstr2mat(String str) {
		int t1=str.lastIndexOf("<table");
		int t2=str.lastIndexOf("</table>")+7;
		String s = str.substring(t1,t2+1);
		//s=s.replaceAll("'","\"");
		s=s.replaceAll("<table [^>]*>","<table>");
		s=s.replaceAll("<td [^>]*>","<td>");
		s=s.replaceAll("<span [^>]*>","<span>");
		s=s.replaceAll("<div [^>]*>","<div>");
		s=s.replaceAll("&nbsp;","");
		
		t1 = (s.length() - s.replaceAll("<tr", "").length())/3;
		t2 = (s.length() - s.replaceAll("</tr", "").length())/4;
		
		if(t1 != t2) {
			s=s.replaceAll("</tr>","</tr><tr>");
			int l=s.lastIndexOf("<tr>");
			s=s.substring(0,l)+s.substring(l+4,s.length());
		}
		else
		s=s.replaceAll("<br>","<br/>");
		
		t1 = (s.length() - s.replaceAll("<tr", "").length())/3;
		t2 = (s.length() - s.replaceAll("</tr", "").length())/4;
		
		//System.out.println(t1+" "+t2);
		
		s=s.replaceAll("\\s*","");
		s=s.trim();
		s="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+s;
		String[][] mat=null;
		try {
			javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.StringBufferInputStream(s));
			Document xmldoc = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.StringBufferInputStream(s));
			NodeList rows = xmldoc.getElementsByTagName("tr");
			mat = new String[rows.getLength()][];
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
		}catch(Exception e) {}
		return mat;
	}
	//----------------------------------------------
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
	//----------------------------------------------	
	static void printMatrix(String[][] matrix) {
		System.out.println("\n"+matrix.length+" "+matrix[0].length);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
			System.out.print(matrix[i][j]+" ");
			System.out.println();
		}
	}
	//----------------------------------------------	
	static String[] get(String bols) {
		String s=bols.trim();String[] Y=null;
		if (!s.equals(""))  Y = s.split("\\ ",-1);
		return Y;
	}
	//----------------------------------------------
	public static String  bengToEng(String arr)  {
		String str="985|o|986|a|987|i|988|ii|989|u|98A|uu|98B|rri|98F|e|990|oi|993|oo|994|ou|995|k|996|kh|997|g|998|gh|999|ng|99A|ch|99B|chh|99C|j|99D|jh|99E|ny|99F|t|9A0|th|9A1|d|9A2|dh|9A3|n|9A4|t|9A5|th|9A6|d|9A7|dh|9A8|n|9AA|p|9AB|f|9AC|b|9AD|bh|9AE|m|9AF|j|9B0|r|9B2|l|9B6|sh|9B7|ssh|9B8|s|9B9|h|9DC|r|9DD|rh|9DF|y|9CE|t|982|ng|983|-|981|-|9BE|a|9BF|i|9C0|ii|9C1|u|9C2|uu|9C3|rri|9C7|e|9C8|oi|9CB|oo|9CC|ou|9F7|.|9E7|1|9E8|2|9E9|3|9EA|4|9EB|5|9EC|6|9ED|7|9EE|8|9EF|9|9E6|0|980||";
		String[] strs=str.trim().split("\\|");
		Map<String, String> map= new HashMap<String, String>();
		for(int i=0;i<strs.length-1;i+=2)
		map.put(strs[i].trim(),strs[i+1].trim());
		String res="";boolean blank=true;
		for (int i=0;i<arr.length();i++) {
			if ((int)arr.charAt(i) ==32) blank=true;
			else {
				String h = Integer.toHexString(arr.charAt(i));
				String s = map.get(h.toUpperCase());
				if(s!=null) {
					if (blank==true) {
						StringBuffer sb = new StringBuffer(s.toLowerCase());
						sb.setCharAt(0,Character.toUpperCase(sb.charAt(0)));
						res = res+sb;
						blank = false;
					}
					else res = res+s;
				}
			}
		}
		if(res.equals("")) res=arr;
		return res;
	}
	//----------------------------------------------
	static String[] parts(String bol) {
		String first="";
		int i=0;
		while(i<bol.length() && bol.charAt(i) != '.' && bol.charAt(i) != '_') {
			first=first+bol.charAt(i);
			i=i+1;
		}
		String rest=bol.substring(i,bol.length());
		return new String[]{first, rest};
	}
	//----------------------------------------------
	static String[][] split(String[] bs,String[][] bols) throws Exception{
		int l=bs.length;
		String[] dayan={},bayan={};
		for (int i=0;i<l;i++) {			
			String[] b=bs[i].split(" ");
			int len=b.length;
			String bn="",dn="";
			for (int k=0;k<len;k++) {
				String abol=b[k], da="", ba="";
				String[] b1=abol.split("\\+");
				if (b1.length > 1) {
					da=b1[0];ba=b1[1];
				}
				else {
					String[] s=parts(abol);
					abol=s[0];
					boolean found=false;
					for (int j=0;j<bols.length;j++) {
						if (bols[j][0].equals(abol)) {
							da=bols[j][1]; ba=bols[j][2];
							if (!da.equals("-")) da=da+s[1];
							if (!ba.equals("-")) ba=ba+s[1];
							found=true;
							break;
						}
					}                    
					if (!found) throw new Exception("Bol "+abol+" not found");
				}
				String c;
				if (k == 0)  c="";
				else c=" ";
				dn=dn+c+da;
				bn=bn+c+ba;
			}
			dayan=concat(dayan, dn);
			bayan=concat(bayan, bn);
		}
			//Utils.printArray(dayan);
			//System.out.println();
			//Utils.printArray(bayan);

		return new String[][]{dayan, bayan};
	}
	//----------------------------------------------
	public static String getSongs(String dir)  throws Exception {
		File[] paths = new File(dir).listFiles();
		String res="";      
		for(int in=0;in<paths.length;in++) {
			//if (paths[in].isFile() && paths[in].length() > 16000) {
			String file=paths[in].getName().toString();
			String f = htmlEncode(file.substring(0, file.indexOf('.')));
			String f3 = java.net.URLEncoder.encode(file, "UTF-8");

			res=res+"<option value=\""+f3+"\">"+f+"</option>";
			//}
		}
		return res;		
	}
	//----------------------------------------------
	public static String htmlEncode(final String string) {
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
	//----------------------------------------------
	public static Map<String, String[]> getDb(String file) throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));       
		String line;
		while((line = br.readLine()) != null) {
			String strs[] = line.split("\\|");
			map.put(strs[0], new String[]{strs[1],strs[2]});
		}
		return map;
	}
	//----------------------------------------------
	public static void playInst(String instname,String taal,int scale,int bpm, int duration,String soundfont) throws Exception {
		PercussionInstrument inst = null;
		switch(instname) {
		case "tabla":
			inst = new Tabla(scale);
			break;
		case "khol":
			inst = new Khol(scale);
			break;
		case "tabla1":
			inst = new Tabla1(scale);
			break;
		}
		Object[] pair= inst.tonmat(taal,duration,0,false,20);
		Double[][] nmat = (Double[][])pair[0];
		Utils.writemidi_java(nmat, "out.mid" ,300, bpm,3,4, inst.patch);
		Utils.playMidi("out.mid", soundFontRoot+soundfont);
	}
	//----------------------------------------------
	public static Soundbank[] getAllSoundbanks(String soundbank_file) throws Exception {
		//System.out.println("In getAllSoundbanks(): soundbank_file = "+soundbank_file);
		String str = new File(soundbank_file).getName();
		//System.out.println(str);
		String name = str.substring(0,str.lastIndexOf("."));
		//System.out.println(name);
		System.out.println("In Utils:getAllSoundbanks(): soundbank directory: "+new File(soundbank_file).getParentFile().getAbsolutePath());
		
		
		File dir = new File(soundbank_file).getParentFile();
		File[] paths = dir.listFiles(new java.io.FilenameFilter() {
			public boolean accept(File dir, String str) {
				//System.out.println("lowercase = "+str.toLowerCase());
				return str.toLowerCase().contains(name.toLowerCase());
			}
		});
		
		System.out.println("No of. soundbanks found: "+paths.length);
		
		Soundbank[] soundbanks = new Soundbank[paths.length];
		for(int in=0;in<paths.length;in++)  {
			System.out.println(paths[in].getAbsolutePath()+" "+paths[in].length());
			soundbanks[in] = MidiSystem.getSoundbank(paths[in]);
		}
		return soundbanks;
	}
}



