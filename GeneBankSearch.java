/**********************************
* CS321                           *
* BTree P4                        *
* GeneBankSearch takes in a BTree *
* file and a sequence to search   *
* for within the BTree            *
***********************************/ 

import java.io.File;
import java.io.FileNotFoundException;

public class GeneBankSeach{
	
	//private variables
	private byte isCache;
	private int degree;
	private String BTreeFile;
	private String QueryFile;
	private int cacheSize;
	private byte debugLvl;
	private BTree T;
	private BTreeUtil util;
	private Cache cache;
	
	public static void main(string[] args){
		if (args.length < 3){
			System.out.println("ERROR: not enough arguments.");
			System.out.println("Usage: GeneBankSeach <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>");
		}
		else{ //try to parse information passed in
			try{
				GeneBankSeach(args);
			}
			catch (NumberFormatException e){
				System.out.println("Error: 1 or more args is not a number: " + e.toString());
			}
		}
	}
	
	public GeneBankSeach(String[] args)throws NumberFormatException{
		isCache =  Byte.parseByte(args[0]);  
		BTreeFile = args[1];
		QueryFile = args[2];
		
		if (args.length > 3){
			cacheSize = Integer.parseInt(args[4]);
		}
		if (args.length > 4){
			debugLvl = Byte.parseByte(args[5]);
		}
		
		util = new BTreeUtil();
		cache = new Cache(cacheSize);
		//get Tree from binary file  ??how to do that?
		File file = new File(BTreeFile);
		T = (BTree)file;
	}
	
}