package ukr;
public class  test{
    public static void main(String args[])  {
        try {
		//String[][] m1 = new String[][] {{"a","b"},{"c","d"}}, m2 = new String[][]{{"e","f"},{"g","h"}}, m3=null;
		//Utils.printMatrix(Utils.concatRow(m3,m3));
		//Utils.printMatrix(Utils.concatCol(m3,m3));
		
		
		//Object[] values = Utils.taals("khol","3333");
		//System.out.println(values[0]+"\t"+values[1]);
		//Utils.printArray((String[])values[0]);
		//Utils.playInst("tabla",   "44v8|last",       2,       180,     1000,    "ukr.SF2");
		
            PercussionInstrument inst = new Tabla1(2);
            //inst.getTaals1();
            
            Object[] pair= inst.tonmat("33v7|sthayi",100,0,false,0);
            
            Double[][] nmat={};
            Double[][] mat = (Double[][])pair[0];
            double len = (Double)pair[1];
            
            nmat=Utils.concatRow(nmat,mat);            
            //Utils.printMatrix(nmat);
            
            Utils.writemidi_java(nmat, "out.mid" ,300, 180,3,4, inst.patch);
            Utils.playMidi("out.mid", "D:/Software/SoundFonts/Tabla1.sf2");
            
        }catch(Exception e) {e.printStackTrace();}
    }
    
    
}
