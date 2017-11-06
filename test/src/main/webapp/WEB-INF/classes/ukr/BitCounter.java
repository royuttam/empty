package ukr;
class BitCounter {
	public static void main(String args[]) throws Exception{
		
		String[][] mat=Utils.readHtml("E:/nltr/test/song/Olibar.html"); 
              String[][] seq=Utils.mat2seq(mat);
	      Song song = Utils.seq2t(seq);
//	      song.printT1();
	      song = Utils.transform(song);
	      //song.printT1();
	      //song.printData();
		  int c=0;
		  for(int z=0;z<song.data.length;z++)
			  if(!song.data[z].segment.name.equals("repeat"))
			  c+=song.data[z].segment.T.length;
		  System.out.println("count = "+c+", lag = "+song.meta.pre);
	}
}