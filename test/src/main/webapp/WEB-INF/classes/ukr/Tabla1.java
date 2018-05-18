package ukr;
import ukr.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
public class Tabla1 extends PercussionInstrument {
	//--------------------------------------------------------------------
    void initTaalDb() {
        this.taaldb=getTaals();
    }
	//--------------------------------------------------------------------
    Tabla1(int scale) {
        super("tabla1",scale);
		System.out.println("In Tabla1: scale= "+scale);
        this.scale=scale-1;
        //this.bank=101;  //bank 100
        
        String[] dayan = {"de","din","na","ne","open-na","re","sharp-na","sharp-tin",    "tu","softer-sur","strong-te","sur","te","thap","ti","tin"};
        rootKeys = new Integer[]{7,   23,   39,  55,  71,       87,  103,       119,             7,   23,          39,         55,   71,  87,    103, 119};
        
        String[] bayan = {"closed-ge","finger-ke","co-ge","co-ge1","oc-ge","ke","loud-ge","loud-ke",    "open-ge","open-ke","ge","sliding-ge"};
        rootKeys = Utils.concat(rootKeys,new Integer[]{7, 23, 39, 55, 71, 87, 103, 119,     7, 23, 39, 55});
        
        channels  =new int[]{5,5,5,5,5,5,5,5,  6,6,6,6,6,6,6,6,  7,7,7,7,7,7,7,7, 8,8,8,8 };
		//channels  =new int[]{1,1,1,1,1,1,1,1,  2,2,2,2,2,2,2,2,  3,3,3,3,3,3,3,3, 4,4,4,4 };
        
        this.bols=null;
        for(int i=0;i<dayan.length;i++) this.bols = Utils.concatRow(this.bols, new String[][]{{dayan[i], dayan[i], "-"}});
        for(int i=0;i<bayan.length;i++) this.bols = Utils.concatRow(this.bols, new String[][]{{bayan[i], "-",bayan[i]}});
        bols = Utils.concatRow(bols, new String[][] {{"-", "-", "-"}});
        
        bls.addAll(Arrays.asList(dayan));
        bls.addAll(Arrays.asList(bayan));
        
        patch = new Integer[4][];
        for(int i=0;i<patch.length;i++) patch[i]  = new Integer[]{i+1 +4, track, i+1, bank};
    }
    //--------------------------------------------------------------------
    public static Map<String, String[]> getTaals() {        
        Map<String, String[]> taals = new HashMap<String, String[]>(getTaals1("tabla1"));        
        return taals;        
    }       
	//--------------------------------------------------------------------
}