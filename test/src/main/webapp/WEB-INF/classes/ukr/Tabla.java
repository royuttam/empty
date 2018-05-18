package ukr;
import ukr.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
public class Tabla extends PercussionInstrument {
	//--------------------------------------------------------------------
    void initTaalDb() {
        this.taaldb=Tabla.getTaals();
    }
	//--------------------------------------------------------------------
    Tabla(int scale) {
        super("tabla",scale);
        this.scale=scale;
        //this.bank=3;    //bank 2
        
        this.bols=new String[][]{{"ge","-","ge"},{"ghe","-","ghe"},{"ghee","-","ghee"},{"ke","-","ke"},{"dhec","-","dhec"},{"na","na","-"},{"tin","tin","-"},{"tun","tun","-"},{"thun","thun","-"},{"te","te","-"},{"re","re","-"},{"tre","tre","-"},{"-", "-", "-"},{"dha", "na", "ge"},{"dhaa", "na", "ghe"},{"dhaaa", "na", "ghee"},{"dhin", "tin", "ghe"},{ "dhe", "tin", "ghee"},{ "natin", "na", "tin"},{ "nate", "na", "te"},{ "geghe", "ge", "ghe"},{ "gete", "ge", "te"},{ "dhun", "ge", "tun"}};
        
        bls = Arrays.asList(new String[]{"ge","ghe","ghee","ke","dhec","na","tin","tun","thun","te","re","tre"});
        rootKeys= new Integer[]{ 5,   17,   29,    36,  37,    46,   53,   60,   67,   74,  81,  88};
        patch=new Integer[][]{{5, track, 127, bank}};
		//patch=new Integer[][]{{1, track, 127, bank}};
        channels  =new int[]{5,5,5,5,5,5,5,5,  5,5,5,5 };
		//channels  =  new int[]{1,1,1,1,1,1,1,1,  1,1,1,1 };
    }
    //--------------------------------------------------------------------
    static Map<String, String[]> getTaals() {
        //Map<String, String[]> taals = new HashMap<String, String[]>();
        Map<String, String[]> taals = new HashMap<String, String[]>(getTaals1("tabla"));
        /*
        taals.put("23|sthayi",new String[]{
            "|ghee na |ge_-3 \"ghee_2 ge_-3\" \"na na\"|",
            "|\"ghee - - tre\" na |ge_-3 \"ghee ge_-3\" \"na na\"|",
            "|ghee \"na na\" |\"ghee_2 ge_-3\" ghee na|"
            //"|ghee \"na na\" |ge \"ghee ghee\" \"tre na\"|",
            //"|ghee na |ge \"tre ghee\" \"na na\"|",
            
            //"|ge \"na na\" |ge ghee na|",
            //"|ge na |ghee ge na|",
        });
        taals.put("23|repeat",new String[]{
            "|\"ghee - tre tre\" \"na na\" |\"ghe_2 ge_-3\" \"ghee ge_-3\" \"na na\"|"
            //"|ghee \"na na\" |ge \"ghee ghee\" \"tre na\"|",
            //"|ghee \"tre na\" |ge \"te re ke te\" \"na na\"|",
            //"|\"ghee ghee\" \"na na\" |ge \"te re ke te\" \"na na\"|",
        });
        taals.put("23|last",taals.get("23|repeat"));
        taals.put("23|antara",taals.get("23|sthayi"));
        
        
         taals.put("2323|sthayi",taals.get("23|sthayi"));
         taals.put("2323|repeat",taals.get("23|repeat"));
         taals.put("2323|last",taals.get("23|last"));
         taals.put("2323|antara",taals.get("23|antara"));
        
        
        taals.put("22|sthayi",new String[]{
            "|ghee \"na na\" |\"- ke\" \"tun na\"",
            "|- \"na na\" |\"- ke\" \"dha na\"|",
            "|- \"na na\" |\"- ke\" \"dha na\"|"
        });
        taals.put("22|repeat",new String[]{"|ghee \"na na\" |\"- ke\" \"na na\"|"});
        taals.put("22|last",new String[]{
            "|dha.2_-3 \"- ghee_3\" na \"te re\"",
            "| na \"- ghee.2_5\" na tun",
            "|dha.2_-3 \"- ghee_3\" na \"te re\""
        });
        taals.put("22|antara",taals.get("22|sthayi"));
        
        taals.put("32|sthayi",new String[]{ "|ge ghee \"na na\"|ghee \"na na\"|"});
        taals.put("32|repeat",new String[]{
            "|ge \"ghee ghe\" \"na na\"|\"ghee ghe\" \"na na\"|",
            //----------mukhra--------
            "|\"na na\" \"te	te\" \"na ke te re\"|\"ke te na ke\" \"te re ke te\""
        });
        taals.put("32|last",taals.get("32|repeat"));
        taals.put("32|antara",taals.get("32|sthayi"));
        
        
        taals.put("322|sthayi",new String[]{
            "|ghee_4   \"tre re\"  \"na na\"| \"ghee ge_-2\" \"na na\"| \"ghee ge_-2\" \"na na\"",
            "|ghee_4   \"tre re\"  \"na na ke na\"| \"ghee ge_-2\" \"na na\"| \"ghee ge_-2\" \"na na\"",
            "|ghee_4   \"tre re\"  na| ghee \"na na\"| ghee \"na na\"",
        });
        
        taals.put("322|repeat",new String[]{
            "|ghee_4   \"tre re\"  \"na na\"| \"ghee ge_-2\" \"na na ke na\"| \"ghee ge_-2\" \"na na ke na\"",
            //----------mukhra--------
            "|ghee \"tre re\" \"na na\"|\"te	te\" \"na ke te re\"|\"ke te na ke\" \"te re ke te\""
        });
        taals.put("322|last",taals.get("322|repeat"));
        taals.put("322|antara",taals.get("322|sthayi"));
        
        taals.put("322v1|sthayi",new String[]{
            "|ghee_4   \"tre re\"  \"na na\"| \"ghee ge_-2\" \"na na\"| \"ghee ge_-2\" \"na na\"",
            "|ghee_4   \"tre re\"  \"na na\"| \"ghee ge_-2\" \"na na\"| \"ghee ge_-2\" \"na na\"",
            "|ghee_4   \"tre re\"  na| ghee \"na na\"| ghee \"na na\"",
        });
        taals.put("322v1|repeat",new String[]{
            "|ghee_4   \"tre re\"  \"na na ke na\"| \"ghee ge_-2\" \"na na\"| \"ghee ge_-2\" \"na na\"",
            //----------mukhra--------
            "|ghee \"tre re\" \"na na\"|\"te	te\" \"na ke te re\"|\"ke te na ke\" \"te re ke te\""
        });
        taals.put("322v1|last",taals.get("322v1|repeat"));
        taals.put("322v1|antara",taals.get("322v1|sthayi"));
       
        taals.put("33v7|sthayi",new String[]{
            "|ghee.2  \"tre re\"  \"na na\" |\"tre re\" \"ghee ge_-3\"  na.3|",
            "|ghee.2  \"tre re\"  \"na na\" |\"tre re\" \"ghee ge_-3\"  \"na na\"|",
            "|ghee.2  \"tre re\"  \"na na\" |\"tre re\" \"ghee ge_-3\"  \"na na\"|"
            //"|ghee.2  \"tre re\"  \"na na\" |\"ghee_5 ge_-3\" \"ghee_5 ge_-3\"  na|",
            //"|ghee.2  \"tre re\"  \"na na\" |\"ghee_5 ge_-3\" \"ghee_5 ge_-3\"  \"na na\"|"
            //"|\"tin+ge_5 tin+ge_4\" \"tin+ge_3 tin+ge_2\"  \"tin+ge_1 tin+ge\" |\"tin+ge_-1 tin+ge_-2\" \"tin+ge_-3 tin+ge_-4\"  na+ge_-5|",
        });        
        taals.put("33v7|repeat",new String[]{
            
            "|dha.2_-3 \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee_3 na\" ge.2",
            //"|dha \"ghee.2 na\" tun|\"na ke\" \"ghee na\" ge.2"
            //--------Mukhra--------
            "|dha.2_-3  \"tre na\"   \"te	te\"	 |\"na	ke te	re\"	 \"ke	te na	ke\"	 \"te	re ke	te\""
        });
        
        taals.put("33v7|antara",new String[]{
            "|ghee+re  tre \"tre ke\"| \"na ke\"  ge_-2 ge_-3|",
            "|ghee+re  tre \"tre ke\"| \"na na\"  ge_-2 ge_-3|",
            "|ghee+re  tre \"tre ke\"| \"na ke\"  ge_-2 ge_-3|",
        });
        
        taals.put("33v7|last",new String[]{
            "|dha.3_-3 \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" tun",
            "|\"na ke\" \"na na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" \"ge.2 - na na\"",
            "|dha.3_-3 \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" tun",
            "|\"na ke\" \"na na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" \"ge.2 - na na\"",
            "|dha.2_-3 \"ghee.2 na\" tun|\"na ke\" \"ghee na\" \"ge.2 - na na\""
        });
        
        taals.put("33v8|sthayi",new String[]{
            //'|dha  dhaaa na| \"tre te\" dhin \"na na\"|',
            //'|dhaaa  tre \"tre ke\"| \"na ke\"  ge ge|',
            //'|\"dhaaa ghee\"  tre \"tre ke\"| \"na ke\"  ge ge|',
            
            "|dhaaa  tre \"tre ke\"| \"na ke\"  ge.2_-3 ge.2_-3|"
            //"|ge_4+re  tre \"tre ke\"| \"na na\"  ghee ge.2_-3|"
            //'|dha  tre \"tre ke\"| \"na na ke na\"  ghee ge|',
        });        
        taals.put("33v8|repeat",new String[]{
            //'|ge \"ghee.2 na\" \"te re\"|\"na ke\" \"ghee na\" ge.2',
            //'|dha \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee na\" ge.2',
            //'|dha \"ghee.2 na\" \"te re\"|\"na ke\" \"ghee na\" ge.2',
            "|ghee_3  \"tre re\"  \"na na\" |\"tre re\" \"ghee ge.2_-4\"  \"na na ke na\"|",
            
            //--------Mukhra--------
            "|ghee  \"tre na\"   \"te	te\"	 |\"na	ke te	re\"	 \"ke	te na	ke\"	 \"te	re ke	te\""
        });        
        taals.put("33v8|last",new String[]{
            "|dha.2_-3 \"ghee.2 na\" tun|\"na ke\" \"ghee_5 na\" \"ge.2 - na na\"",
            "|dha.2_-3 \"ghee.2 na\" \"tun - na na\"|\"na ke\" \"ghee_5 na\" ge.2",
            "|dha.2_-3 \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" \"ge.2 - na na\"",
            "|dha.2_-3 \"ghee.2 na\" \"tun - na na\"|\"na ke\" \"ghee_5 na\" ge.2"
        });        
        taals.put("33v8|antara",new String[]{
            "|\"dha_-2 - - tre\"  \"te re+ghee_5\" \"na ke\"|\"na na\"  ghee.2_5 ge.2_-3|",
            "|dha_-2  \"te re\" \"na ke\"|\"na na ke na\"  \"tre ke\" ge.2_-3|",
            "|dha_-2    \"te ghee_5\" \"na ke\"|\"na na\"  ghee.2_3 ge.2_-3|",
            "|dha_-2  \"te re\" \"na ke\"|\"na na ke na\"  \"tre ke\" ge.2_-3|" ,
            "|dha_-2 \"te ghee_5\" \"na ke\"|\"na na ke na\"  ge.2_-3 ge.2_-3|"
        });
        
        
        taals.put("33v9|sthayi",new String[]{
            "|dhaaa ge_-3 \"na re\"| \"tre te\" ge \"na na\"",
            "|ge_-3 ghee_5 \"na na\"| \"tre te\" tun \"na na\"|",
            
            "|dhaaa ge_-3 \"na na\"| \"tre te\" ge na",
            "|ge_-3 ghee_5 \"na re\"| \"tre te\" tun na|",
            
            "|dhaaa ge_-3 \"na re\"| \"tre te\" ge \"na na\"|"
            //"|dhaaa ge na|  \"tre te\" tun na|",
            
            //"|ge  ghee \"na na\"| \"tre te\" tun \"na na\"|",
        });
        taals.put("33v9|repeat",new String[]{
            "|ge.2_-4 \"ghee na\" \"tre re\"|\"na ke\" \"ghee_5 na\" ge",
            //--------Mukhra--------
            "|ghee  \"tre na\"   \"te	te\"	 |\"na	ke te	re\"	 \"ke	te na	ke\"	 \"te	re ke	te\""
        });        
        taals.put("33v9|antara",taals.get("33v9|sthayi"));
        taals.put("33v9|last",taals.get("33v9|repeat"));
        
        
        taals.put("3333|sthayi",new String[]{
            "|ghee_5+re  tre \"tre ke\"| \"na ke\"  ge.2_-3 ge.2_-3|",
            "|\"na.2+re na\" tre \"tre ke\"| \"na na\"  ghee ge.2_-3|",
            "|dhaaa  tre \"tre ke\"| \"na na ke na\"  ghee ge|"
        });
        taals.put("3333|repeat",new String[]{
            "|\"ghe ge\"  na \"te re ke te\"| \"na ke\"  ge \"na na\"|\"ghe ge\"  na \"te re\"| \"te re ke te\"  ge \"na na\"|"
        });        
        taals.put("3333|last",taals.get("3333|repeat"));        
        taals.put("3333|antara",new String[]{
            "|\"dha_-2 - - tre\"  \"te re\" \"na ke\"| \"na ke\"  ge_-3 \"na na\"| \"na.2 - - tre\"  \"na na\" \"te re\"| \"na ke\"  \"ghee_3 ge_-3\" \"na na\"|"
        });
        
        taals.put("3333v1|sthayi",new String[]{
            "|dha.2_-3  \"tre re\"  \"na na\" |\"tre re\" \"ghee ghe\"  \"na na\"|ghee.2_-3  \"tre re\"  \"na na\" |\"tre re\" tun  \"na na\"|",
            "|dha.2_-3  \"tre re\"  \"na na\" |\"tre re\" \"ghee ghe\"  \"na na\"|ghee.2_-3  \"tre re\"  \"na na\" |\"tre re\" tun  \"na na\"|",
            "|dha.2_-3  \"tre re\"  \"na na\" |\"ghee ge\" \"ghee ge\"  \"na na\"|ghee.2_-3  \"tre re\"  \"na na\" |\"ghee ge\" tun  \"na na\"|"
        });
        taals.put("3333v1|repeat",new String[]{
            "|dha_-3 \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" ge.2",
            "|dha_-3 \"ghee.2 na\" tun|\"na ke\" \"ghee_5 na\" ge.2",
            "|dha_-3 \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" ge.2",
            //---attempted for tihai
            "|\"dha_-3\" \"te re ke te\" \"dha_-3 -\" \"te re ke te\" \"dha_-3 -\" \"te re ke te\" "
        });
        taals.put("3333v1|last",new String[]{
            "|dha_-3 \"ghee.2 na\" \"te re ke te\"|\"na ke\" \"ghee_5 na\" ge.2",
            //"|dha \"ghee.2 na\" tun|\"na ke\" \"ghee na\" ge.2"
        });
        taals.put("3333v1|antara",taals.get("3333v1|sthayi"));
        
        taals.put("4444|sthayi",new String[]{
            //"|na.-1+ge_-3 tin.-5+ghe.-1_-1 tin.-5+ghe.-1_-1 na.-1+ge_-3| na.-3+ge_-3_-1 tin.-5+ghe.-1_-1 tin.-5+ghe.-1 na.-2+ge_-3|na tin.-3 tin.-3 \"na tre.-1\" |\"te.-1 re.-1\" dhin.-2 ghee.-1_2 dha",
            "|\"na.-1+ge.2_-3 - tre.-6\" tin.-5+ge.-2 tin.-5+ge.-2 na.-1+ge_-3| na.-3+ge.2_-3 tin.-5+ge.-2 tin.-5+ge.-2 na.-2+ge_-3|na tin.-3 tin.-3 \"na tre.-1\" |\"te.-1 re.-1\" dhin.-2 tin.-3+ghee.-1_2 dha",
        });        
        taals.put("4444|repeat",new String[]{
            //"|dha \"na na\" dhin dha|dha dhin dhin dha|\"na na\" tin tin \"na tre\" |\"te re\" dhe dhe dha",
            //"|dha \"na na\" dhin dha|dha dhin dhin dha|\"na na\" tin tin \"na tre\" |\"te re\" dhe dhe dha"
            //with mukhra
            //"|dha \"na na\" dhin dha|dha dhin dhin dha|\"na na\" tin tin \"na tre\" |\"te re\" dhe dhe dha|dha dhin dhin dha| ghee dhin dhin dha| \"na na\" tin tin \"na tre\"|\"te	te\"	\"na	ke te	re\"	\"ke	te na	ke\"	\"te	re ke	te\""
            "|na.-1+ge.2_-3 \"na na\" tin.-5+ghe.-1_-1 na.-1+ge_-3|na.-1+ge_-3 tin.-5+ghe.-1_-1 tin.-5+ghe.-1_-1 na.-1+ge_-3|\"na na\" tin tin \"na tre\" |\"te re\" dhe dhe dha",
            "|na.-1+ge.2_-3 tin.-5+ghe.-1_-1 tin.-5+ghe.-1_-1 na.-1+ge_-3|na.-1+ge_-3 tin.-5+ghe.-1_-1 tin.-5+ghe.-1_-1 na.-1+ge_-3|\"na na\" tin tin \"na tre\" |\"te	te\"	\"na	ke te	re\"	\"ke	te na	ke\"	\"te	re ke	te\"|"
            
            //with tihai
            //"|dha \"na na\" dhin dha|dha dhin dhin dha|\"na na\" tin tin \"na tre\" |\"te re\" dhe dhe dha |dha dhin dhin dha| ghee dhin dhin dha| \"na na\" tin tin \"na tre\"|\"te re ke te\" \"dha - te re\"  \"ke te dha -\" \"te re ke te\""
            //["|dha \"na na\" dhin dha|dha dhin dhin dha|\"na na\" tin tin \"na tre\" |\"te re\" dhe dhe dha|dha dhin dhin dha| ghee dhin dhin dha| " mukhra8]
        });        
        taals.put("4444|last",new String[]{
            "|na.-1+ge.2_-3 \"na na\" tin.-5+ghe.-1_-1 na.-1+ge_-3|na.-1+ge_-3 tin.-5+ghe.-1_-1 tin.-5+ghe.-1_-1 na.-1+ge_-3|\"na na\" tin tin \"na tre\" |\"te re\" dhe dhe na.-1+ge_-3",
        });        
        taals.put("4444|antara",taals.get("4444|sthayi"));
        
        taals.put("44v6|sthayi",new String[]{
            "|dha.2_-3 \"tre re\" tre \"na re\" | \"na+tre ke\" \"tre re\" tin+ghee_3 dha ",
            "|dha.2_-3 \"tre re\" tre \"na re\" | \"na+te ke\" \"tre re\" tin+ghee_3 dha ",
            "|dha.2_-3 \"tre re\" tre na | \"na+tre ke\" \"tre re\" tin+ghee_3 dha ",
        });        
        taals.put("44v6|repeat",new String[]{
            "|ghee.2 \"na na\" te na.2 | \"ghee ghee\" \"na na\" ghee.2 \"na na\"|",
            //------------mukhra----------
            "|ghee.2 \"na na\" te na.2 | \"te	te\"	\"na	ke te	re\"	\"ke	te na	ke\"	\"te	re ke	te\""
        });
        taals.put("44v6|last",taals.get("44v6|repeat"));
        taals.put("44v6|antara",new String[]{"|dha.2_-3 \"- ghee_3\" na \"te re\"| na \"- ghee.2_5\" na tun" });
        
        
        taals.put("44v7|sthayi",new String[]{
            "|ge.3_-3 \"- ghee_3\" tre \"na na\" | - \"ghee_3 ge_-3\" ghee.2 na.2|",
            "|ge.3_-3 \"- ghee_3\" tre \"na na\" | - \"ghee_3 ge_-3\" \"ghee_3 ge_-3\" na.2|",
            "|ge.3_-3 \"- ghee_3\" tre \"na na\" | \"ghee_3 ge_-3\" \"ghee_3 ge_-3\" \"ghee_3 ge_-3\" na.2|",
            "|ge.3_-3 \"- ghee_3\" tre \"na na\" | \"tre na\" ghee_3 ghee_3 na.2|",
            "|ge.3_-3 \"- ghee_3\" tre \"na na\" | \"tre na\" ghee_3 ghee_3 na.2|",
        });
        taals.put("44v7|repeat",new String[]{
            
            "|ghee.2 \"na na\" te na.2 | \"ghee ghee\" \"na na\" ghee.2 \"na na\"",
            //------------mukhra----------
            "|ghee.2 \"na na\" te na.2 | \"te	te\"	\"na	ke te	re\"	\"ke	te na	ke\"	\"te	re ke	te\""
        });        
        taals.put("44v7|last",new String[]{"|ghee.2 \"na na\" te na.2 | \"ghee ghee\" \"na na\" ghee.2 \"na na\""});
        taals.put("44v7|antara",taals.get("44v7|sthayi"));
         
        taals.put("44v8|sthayi",new String[]{
            //"|dha.2_-3 \"- ghee_3\" na \"te re\"| na \"- ghee.2_5\" na tun",
            
            "|ghee.2_4 dha.2_-3 \"na na\" \"ke tre\" | na \"tun ke\" \"na na\" ge_-1|"
            
            //"|dha.4_-4 na ghee_4+tin na| na ghee_-3 ghee_4+tin dha.4_-3|\"na na\" tin tin \"na tre\" |\"te re\" ghee+tin ghee_-1 dha_-3",
            //"|dha.4_-4 \"- ghee_4\" na tun|na \"- ghee_6\" na ge_-2 "%\"- ghee_4\" ghee_4+tin dha.4_-3|na - tin \"na tre\" |\"te re\" ghee+tin ghee_-1 dha_-3",
            
            //"|\"ge.2_-4 na\" \"- ghee_3\" \"na -\" \"te re\"| na \"- ghee_5\" na \"tun tun\"",
            //"|dha.2_-4 \"- ghee_5\" \"na na\" \"te re\"| na \"- ghee_5\" \"na na\" tun",
            //"|\"ghee na\" \"- ghee\" \"na -\" ke| na \"- tun\" \"na na\" \"ke na\"",
            //"|dha.2_-4 \"- ghee_5\" \"na na\" \"te re\"| na \"- ghee_5\" \"na na\" \"tun tun\"",
            //"|\"ghee na\" \"- ghee\" \"na -\" \"te re\"| na \"- ghee_5\" na \"tun tun\"",
            
            //               "|dha.2 \"- ghee\" na \"te re te re\"| na \"- ghee\" na \"tun tun\"",
            //"|\"na na\" \"na na\" na \"te re te re\"| na \"- ghee\" na \"tun tun\"",
            //               "|\"ghee na\" \"- ghee\" \"na -\" \"te re te re\"| na \"- ghee\" na \"tun tun\"",
        });        
        taals.put("44v8|repeat",new String[]{
            "|ghee.2 \"na na\" te na.2 | \"ghee ghee\" \"na na\" ghee.2 \"na na\"",
            "|ghee.2 \"na na\" te na.2 | \"te	te\"	\"na	ke te	re\"	\"ke	te na	ke\"	\"te	re ke	te\""
        });        
        taals.put("44v8|antara",new String[]{
            "|dha.2_-4 \"- ghee_3\" na \"tre.-2 re.-2\"| na \"- ghee_6\" na tun",
            "|dha.2_-4 \"- ghee_3\" \"na na\" \"tre.-2 re.-2\"| na \"- ghee.2_5\" na tun",
            "|dha.2_-4 \"- ghee_3\" na \"tre.-2 re.-2\"| \"na na\" \"- ghee.2_5\" na tun",
        });
        
        taals.put("44v8|last",new String[]{
            //"|dha.2 \"- ghee\" na \"te re te re\"| na \"- ghee\" na \"te re te re\"",
            //"|dha.2 \"- ghee\" na \"te re te re\"| na \"- ghee\" na \"tun tun\"",
            
            "|dha.2_-4 \"- ghee_3\" \"na na\" \"te re ke te\"| \"na na\" \"- ghee.2_6\" \"na ke\" ge_-1",
            "|\"ghee_-2 na\" \"- ghee_-2\" \"na -\" \"te re te re\"| na \"- ghee_5\" na \"tun tun\"",
            "|dha.2_-4 \"- ghee_3\" \"na na\" ge_-1| \"na tun\" \"- ghee.2_6\" \"na ke\" ge_-1",
            "|\"ghee_-2 na\" \"- ghee_-2\" \"na -\" \"te re te re\"| na \"- ghee_5\" na \"te re te re\"",
            
            //"|dha.2 \"- ghee\" \"na na\" \"te re\"|\"dha_6 dha_5\" \"dha_4 dha_3\" \"dha_2 dha_1\" \"dha dha_-1\" ",
        });
        */
        return taals;
    }
}