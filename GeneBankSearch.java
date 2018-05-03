/**********************************
* CS321                           *
* BTree P4                        *
* GeneBankSearch takes in a BTree *
* file and a sequence to search   *
* for within the BTree            *
***********************************/ 

import java.io.File;
import java.io.FileNotFoundException;

public class GeneBankSearch{
	
	//private variables
	private static byte isCache;
	private static int degree;
	private static String BTreeFile;
	private static String QueryFile;
	private static int cacheSize;
	private static byte debugLvl;
	private static BTree T;
	private static BTreeUtil util;
	private static Cache cache;
	
	public static void main(String[] args){
		if (args.length < 3){		//check for too many or not enough arguments
			System.out.println("ERROR: not enough arguments.");
			System.out.println("Usage: GeneBankSeach <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>");
		} else if (args.length > 5){
			System.out.println("ERROR: too many arguments.");
			System.out.println("Usage: GeneBankSeach <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>");
		}
		else{ //try to parse information passed in
			try{
				GeneBankSearch(args);
			}
			catch (NumberFormatException e){
				System.out.println("Error: 1 or more args is not a number: " + e.toString());
			}
		}
	}
	
	

	public static void GeneBankSearch(String[] args) throws NumberFormatException{
		
		//"Usage: GeneBankSeach <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>"
		
		isCache =  Byte.parseByte(args[0]);  
		BTreeFile = args[1];
		QueryFile = args[2];
		
		if (args.length > 3){
			cacheSize = Integer.parseInt(args[3]);
		}
		if (args.length > 4){
			debugLvl = Byte.parseByte(args[4]);
		}
		

		cache = new Cache<Sequence>(cacheSize);
		
		//get Tree from binary file  ??how to do that?
		//The B-Tree should be stored as a binary data file on the disk (and not as a text file). If
		//the name of the GeneBank file is xyz.gbk, the sequence length is k, the BTree degree
		//is t, then the name of the btree file should be xyz.gbk.btree.data.k.t.
		
		String[] BTreeSpecs = args[1].split("\\."); 
		
		int seqLength = Integer.parseInt(BTreeSpecs[4]);
		int degree =  Integer.parseInt(BTreeSpecs[5]);
		
		//TODO: Is this a "text file" and not a "binary data file"????
		File file = new File(BTreeFile);
		
		//TODO: Do we use a zip file here to hold the binary data? 
		
		T = (BTree)file;
	}
	
}