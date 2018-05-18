package ukr;
import java.io.*;
import java.util.*;
import ukr.*;

// a comparator that compares Strings
class ValueComparator implements Comparator<String>{ 
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	public ValueComparator(HashMap<String, Integer> map){
		this.map.putAll(map);
	}

	@Override
	public int compare(String s1, String s2) {
		if(map.get(s1) >= map.get(s2)){
			return -1;
		}else{
			return 1;
		}	
	}
}

public class FindPattern {
	public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
		Comparator<String> comparator = new ValueComparator(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
		result.putAll(map);
		return result;
	}

	public static String[] toNotes(String s) {
		String[] Y=null;		
		s = s.trim().replaceAll("\\s+"," ");
		int pos = 0;
		if(s.startsWith("\"")) pos = 1;		
		java.util.StringTokenizer seg = new java.util.StringTokenizer(s,"\"");
		while (seg.hasMoreTokens()) {			
			String t = seg.nextToken().trim();
			if(pos%2==0) {
				Y=Utils.concat(Y,Utils.get(t));
			}
			else 
			Y=Utils.concat(Y,t);
			
			pos++;
		}
		return Y;
	}
	public static void main(String args[]) throws Exception {
		//String s = "\"N N\" N    - N N \"N D\" N S> \"N S> R>\" S> N S> - N \"N S> R>\"";
		//String[] Y = toNotes(s);
		//		for(int i=0;i<Y.length;i++)
		//	System.out.println(Y[i]);

		
		Utils.init();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<String, String[]> map1 = new HashMap<String, String[]>();
		
		//List<Song> songList=new ArrayList<Song>();
		HashMap<String, List<Song>> allSongs = new HashMap<String, List<Song>>();
		
		/*
		
		String root = "E:/nltr/test";
		//String root = "E:/nltr/out";
		File[] dirs = new File(root).listFiles();
		int count=0;
		for(int in=0;in<dirs.length;in++) {
			File[] songs = new File(dirs[in].toString()).listFiles();
			for(int i=0;i<songs.length;i++) {
				count++;
				if (songs[i].isFile() && songs[i].length() > 16000) {
					try {
						String[] Y=null;
						String[][] mat=Utils.readHtml(songs[i].toString()); 
						String[][] seq=Utils.mat2seq(mat);
						Song song = Utils.seq2t(seq);
						//	      song.printT1();
						song = Utils.transform(song);
						//song.printT1();
						//song.printData();
						int c=0,c1=0;
						for(int z=0;z<song.data.length;z++) {
						//if(!song.data[z].segment.name.equals("repeat"))
						c+=song.data[z].segment.T.length;
						//for(int l=0;l<song.data[z].segment.T.length;l++)
						//	System.out.print(song.data[z].segment.T[l]+" ");
						//System.out.println(song.data[z].segment.T1);
						
						Y = Utils.concat(Y,toNotes(song.data[z].segment.T1));
						//String[] strs = Utils.parse(song.data[z].segment.T1);
						//for(int n=0;n<strs.length;n++)
							//System.out.print(strs[n]+" ");
						}
						
						
						c1+=Y.length;

						Y=null;
						System.out.println("no of beats  = "+c+" "+c1);
						
						String[] notes = noteSeq(song); 
						
						//for(int z=0;z<notes.length;z++)
						// System.out.print(notes[z]+" ");
						process(notes,map, map1);
						String taal = (String)Utils.searchTaal(songs[i].toString())[1];
						//song.meta.taal = taal;
						List<Song> songList = allSongs.get(taal);
						if(songList == null)
							songList = new ArrayList<Song>();
						
						songList.add(song);
						allSongs.put(taal,songList);						
						
						System.out.println("Taal found = "+taal);
						System.out.println("song.meta.pre = "+song.meta.pre);
					}catch(Exception e) {}
				}
			}
		}
		
		
		
		System.out.println("No. of songs processed :"+count);
		PrintWriter pw = new PrintWriter ("file.txt");
		*/
		
		String fname = "songs.txt";
		//ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fname)));
		//oos.writeObject(allSongs);
		
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fname)));
		allSongs = (HashMap<String,List<Song>>)ois.readObject();
		for (Map.Entry<String, List<Song>> entry : allSongs.entrySet()) {
			String key = entry.getKey();
			List<Song> songList = entry.getValue();
			System.out.println(key+" "+songList.size());
		}
		
		String taal = "33";
		int len = 0;
		for(int i=0;i<taal.length();i++) len += (int)taal.charAt(i)-48;
		
		System.out.println(len);

		List<Song> songList = allSongs.get(taal);
		System.out.println(songList.size());
		//List<String[]> notesList = new List<String[]>();
		
		for(Song song:songList) {
			String[] Y=null;
			
			for(int z=0;z<song.data.length;z++) {
				String[] notes = toNotes(song.data[z].segment.T1);
			    if(z==0) {						
				    String[] notes1 = new String[notes.length-(int)song.meta.pre];
					System.arraycopy(notes,(int)song.meta.pre,notes1,0,notes.length-(int)song.meta.pre);
					Y = Utils.concat(Y,notes1);
				}
				else Y = Utils.concat(Y,notes);
			}
			
			//for(int n=0;n<Y.length;n++)
			//	System.out.println(Y[n]);
			Y=null;
		}
		
		
		

		
		/*
		TreeMap<String, Integer> tm = sortMapByValue(map);
		for (Map.Entry<String,Integer> entry : tm.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			pw.println(key+"     "+value);
			String[] ns = map1.get(key);
			//for(int z=0;z<ns.length;z++) pw.println(ns[z]);
			//pw.println();
		}
		pw.close();
		*/
	}
	//---------------------------------------------------------------
	static void process(String[] notes, HashMap<String, Integer> map, HashMap<String, String[]> map1) {
		int sz=8;
		for (int k = 0; k < notes.length-sz; k++) {
			String str="", str1="";
			for(int l = k;l<k+sz-1;l++) {
				str1+=" "+notes[l];
				str+=" "+Math.abs(Utils.toKeyNo.get(notes[l])-Utils.toKeyNo.get(notes[l+1]));
			}
			str1+=" "+notes[k+sz-1];
			Integer c = map.get(str);
			String[] ns = map1.get(str);
			if(c != null) 
			map.put(str,++c);  
			else 
			map.put(str,1);
			
			map1.put(str,Utils.concat(ns,str1));              
		}
	}
	//---------------------------------------------------------------
	static String[] noteSeq(Song song) {
		String notes[] = null;
		for(int i=0;i<song.data.length;i++) {
			if(!song.data[i].segment.name.equals("repeat") ) 
			{
				Row_normalize[] rn = Utils.serialize(Utils.parse(song.data[i].segment.T1));
				for(int l=0;l<rn.length;l++) {
					if(rn[l].td > .1) { //if duration > .1, discard touch note
						int d = (int)Math.ceil(rn[l].td);
						for(int t=0;t<d;t++) 
						notes = Utils.concat(notes,rn[l].bol);
					}
				}
			}
		}
		return notes;
	}
	//---------------------------------------------------------------
}
/*
1. Usually note changes at rhythm boundary
2. Note continues within rhythm boundary
3. 
*/