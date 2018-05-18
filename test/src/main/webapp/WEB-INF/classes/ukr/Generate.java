package ukr;
import java.util.*;
class Data {
    String  T1;
    int currentNote, sign;
    Data(String T1, int currentNote, int sign) {
      this.T1 = T1;
      this.currentNote = currentNote;
      this.sign=sign;
    }
  }

public class Generate {
  
    static java.util.Map<Integer, String> toKeyName= new java.util.HashMap<Integer, String>();   
  public static void main(String args[]) throws Exception{
    Utils.init();

    for(int i=0;i<Utils.keyNames.length;i++) toKeyName.put(Utils.keyNos[i],Utils.keyNames[i]);

    int[] rs = {4, 4};
    int rs_length = 0;
    for(int part = 0; part < rs.length; part++) rs_length+=rs[part];
    
    int lag = (int)(rs_length*Math.random()), pos = lag, sign=1; 
    //pos=lag=1;
    int startNote = 58 + (int)(12*Math.random()), currentNote = startNote;
    if(startNote > 63 ) sign = -1;
    System.out.println(rs_length+", lag = "+lag+", pos = "+pos+" "+startNote+" "+isStart(rs, lag));
    
    String T1 = "";
    if(lag != 0) {
      System.out.print(toKeyName.get(startNote)+" ");
      T1 += " " +toKeyName.get(startNote);     
    }
      Data Data = generateNotes(rs, startNote, pos, sign);      
      T1+=Data.T1;
      startNote = Data.currentNote;
      sign = Data.sign;
      pos = 0;

    for(int i=0;i<1;i++) {
      Data = generateNotes(rs, startNote, pos, sign);      
      T1+=Data.T1;
      startNote = Data.currentNote;
      sign = Data.sign;
    }

    Song song = new Song();
    song.appendSegment(new String[]{"S"},"no");
    song.data[0].segment.T1 = T1.trim();
    //song.data[0].segment.T1="S - R R P - R S - R g R g S R S <N - S -";
    song.printT1();
    song.meta.bpm=150;
    song.meta.taal="33v7";
    song.meta.scale=2;
    song.meta.inst1=3;
    song.meta.inst2=89;  
    String midifile=Utils.song2midi("abc",song,"tabla",true,false);
    Utils.playMidi(midifile, "D:/Software/SoundFonts/ukr.SF2");
    
  }
  static Data generateNotes(int[] rs, int currentNote, int pos, int sign) {
    int rs_length = 0, prob = 2;
    for(int part = 0; part < rs.length; part++) rs_length+=rs[part];
    String T1 = "";
      while(pos < rs_length) {
        if(prob*Math.random() <1 ) { sign = sign*-1; }
        if(currentNote >=77) {sign = -1;System.out.print("down");}
        if(currentNote <=53) {sign = 1;System.out.print("up");}
        int amt = (int)(3*Math.random()+1);
        if(isStart(rs, pos)) {
          System.out.print("\t");
          if(10*Math.random() > 0 ) currentNote += amt*sign; 
        }
        else {
          if(3*Math.random() < 1 ) currentNote += amt*sign; 
        }
        //System.out.print(" "+sign);
        System.out.print(toKeyName.get(currentNote)+" ");
        T1+=" "+toKeyName.get(currentNote);
        pos++;
      }
    return new Data(T1, currentNote, sign);
  }
//--------------------------------------------------------------
  static boolean isStart(int[] rs, int pos) {
    int i = 0, nb = 0;
    while(nb < pos && i < rs.length)  nb+=rs[i++];     
    if (nb == pos) return true;
    else return false;
  }
//--------------------------------------------------------------   
}