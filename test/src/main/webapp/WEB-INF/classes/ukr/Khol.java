package ukr;
import ukr.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
public class Khol extends PercussionInstrument {
	//--------------------------------------------------------------------
    void initTaalDb() {
        this.taaldb=Khol.getTaals();
    }
	//--------------------------------------------------------------------    
    Khol(int scale) {
        super("khol",scale);
        this.scale=scale;        
        //this.bank=3;    //bank 2
        
        this.bols=new String[][]{{"gin","-","gin"},{"gha","-","gha"},{"ka","-","ka"},{"ta","ta","-"},{"ti","ti","-"},{"te","te","-"},{"ra","ra","-"},{"da","da","-"},{"-","-","-"},{"dha","ta","gha"},{"dhin","ti","gin"},{"jha","ta","gha"},{"ka_ta","ta","ka"},{"kat","ti","ka"},{"Ta","ta","ka"}};
        
        bls = Arrays.asList(new String[] {"gin","gha","ka","ta","ti","te","ra","da"});
        rootKeys= new Integer[] { 5,  17,   36,  46,  53,  60,  67,  74};
        patch=new Integer[][]{{5, track, 128, bank}};
        channels  =new int[]{5,5,5,5,5,5,5,5 };
    }
	//--------------------------------------------------------------------
    static Map<String, String[]> getTaals() {
		Map<String, String[]> taals = new HashMap<String, String[]>(getTaals1("khol"));
		//System.out.println("In getTaals() of Khol "+taals);
        return taals;
    }
	//--------------------------------------------------------------------    
}