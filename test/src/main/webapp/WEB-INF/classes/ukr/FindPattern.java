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

  public static void main(String args[]) throws Exception {
    Utils.init();
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    HashMap<String, String[]> map1 = new HashMap<String, String[]>();

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
              String[][] mat=Utils.readHtml(songs[i].toString()); 
              String[][] seq=Utils.mat2seq(mat);
	      Song song = Utils.seq2t(seq);
//	      song.printT1();
	      song = Utils.transform(song);
	      //song.printT1();
	      //song.printData();
		  int c=0;
		  for(int z=0;z<song.data.length;z++)
			  //if(!song.data[z].segment.name.equals("repeat"))
			  c+=song.data[z].segment.T.length;
		  System.out.println("count = "+c);
		  
              String[] notes = noteSeq(song); 
			  //for(int z=0;z<notes.length;z++)
				// System.out.print(notes[z]+" ");
              process(notes,map, map1);
            }catch(Exception e) {}
          }
      }
    }
    System.out.println("No. of songs processed :"+count);
    PrintWriter pw = new PrintWriter ("file.txt");

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
      if(!song.data[i].segment.name.equals("repeat") ) {
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