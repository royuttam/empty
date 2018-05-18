package ukr;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public abstract class PercussionInstrument {
    static String name;
    int bank=101, track=1;    //default bank 100, track 0
    //int [] insts = null;
    int[] channels = null;
    List<String> bls = new ArrayList<String>();
    Integer [] rootKeys;
    
    
    Map<String, String[]> taaldb= new HashMap<String, String[]>();
    Map<String, Map> taaldb1 = new HashMap<String, Map>();
    String[][] bols=null;
    //Map<String, Integer> map = new HashMap<String, Integer>();
    
    Integer[][] patch;int scale;
    PercussionInstrument(String name, int scale) {
        this.name=name; this.scale=scale;
        initTaalDb();
    }
    abstract void initTaalDb();
    //--------------------------------------------------------------------
    Map setTaal(String key) throws Exception {
        //System.out.println("\nIn setTaal: setting taal "+key);
        String[] taal=taaldb.get(key);
		System.out.println("In setTaal(): taal length: "+taal.length);
        Object[] bayan={}, dayan={};
        String[] bs = {};
        for (int j=0;j<taal.length;j++) {
            bs=Utils.parse(taal[j]);
            String[][] res= Utils.split(bs,this.bols);
            dayan=Utils.concat(dayan,new Object[]{Utils.serialize(res[0])});
            bayan=Utils.concat(bayan,new Object[]{Utils.serialize(res[1])});
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dayan",dayan);m.put("bayan",bayan);m.put("nob",new Integer(bs.length));
        taaldb1.put(key,m);
        return m;
    }
    //--------------------------------------------------------------------
    Object[] tonmat(String taal, double len, double start, boolean exact, int amplFact) throws Exception {
	    //System.out.println(amplFact);
        Map<String, Object> t = taaldb1.get(taal);
        if(t == null)   t=setTaal(taal);        
        int nob=(Integer)t.get("nob");
        if(Math.abs(len-Math.floor(len+0.5)) < 0.0000000001) len = Math.floor(len+0.5);        
        int count=(int)Math.ceil(len/nob);
        int in=0;
        Object[] t1=(Object[])t.get("dayan");
        Object[] t2=(Object[])t.get("bayan");
        double startd=start,startb=start;Double[][] nmat={};        
        int rows=t1.length;
        if (rows > 1) rows=rows-1;        
        boolean back=false;
        for (int j=1;j<=count;j++) {
            if (j==count) in=t1.length-1; //mukhra
            //------------dayan----------
            Row_normalize[] mat = (Row_normalize[])t1[in];
            for(int i=0;i<mat.length;i++) {
                String bol=mat[i].bol;
                if(!bol.equals("-")) {
                    int index = bls.indexOf(bol);
                    nmat=Utils.concatRow(nmat,new Double[][]{{startd,mat[i].td,new Double(channels[index]),new Double(rootKeys[index]+this.scale), 67.0+10*mat[i].ampl+amplFact, track+0.0}});
                }
                startd = startd+mat[i].td;
                if (exact && startd-start > len) {
                    back=true;break;
                }
            }
            //------------bayan----------
            mat = (Row_normalize[])t2[in];
            for(int i=0;i<mat.length;i++) {
                String bol=mat[i].bol;
                if(!bol.equals("-")) {
                    int index = bls.indexOf(bol);
                    nmat=Utils.concatRow(nmat,new Double[][]{{startb,mat[i].td,new Double(channels[index]),new Double(rootKeys[index]+mat[i].scale), 67.0+10*mat[i].ampl+amplFact, track+0.0}});
                }
                startb = startb+mat[i].td;
                if (exact && startb-start > len) {
                    back=true;break;
                }
            }
            if(back) {
                startd=startd-start;
                return new Object[]{nmat, startd};
            }            
            in=(in+1)%rows;            
        }
        startd=startd-start;
        return new Object[]{nmat, startd};
    }
    //-------------------------------------------------------------------
    public static Map<String, String[]> getTaals1(String name) {	    
        Map<String, String[]> map = new HashMap<String, String[]>();
        try {
            java.util.Scanner scan = new java.util.Scanner(new java.io.InputStreamReader(new java.io.FileInputStream(Utils.webRoot+"conf/"+name+".txt")));
            scan.useDelimiter("\\Z");
            String str=scan.next();
            scan.close();
            String[] taals = str.split("#");
            for(int i=0;i<taals.length;i++) {
                try {
                    String[] strs = taals[i].split("=");
                    if(!strs[1].contains("$")) {
                        String[] segs = strs[1].split(",");
                        String[] tl= null;
                        for(int j=0;j<segs.length;j++) {
							String seg = segs[j].trim();
                            if(!seg.equals("") && !seg.contains("//"))  {
                                tl=Utils.concat(tl,new String[]{seg});
                            }
                        }
                        map.put(strs[0].trim(),tl);
                    }
                    else {
                        String val=strs[1].substring(strs[1].indexOf('$')+1, strs[1].length()).trim();
                        map.put(strs[0].trim(),map.get(val));
                    }
                }catch(Exception e){}
            }
        }catch(Exception e) {e.printStackTrace();}
        return map;
    }        
}