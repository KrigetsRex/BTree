/*************************************
* CS321 BTree project                *
* driver class for creating a BTree  *
* creates a BTree from a subsequence *
* of DNA given by the input file     *
* these sequences are coded into a   *
* binary representation and combined *
* into a 64-bit long                 *
*                                    *
* DNA-binary coding:                 *
*    Adenine  00                     *
*    Thymine 11                      *
*    Cytosine 01                     *
*    Guanine  10                     *
**************************************/

//java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.NumberFormatException;


public class GeneBankCreateBTree{
	
	//private variables
	private byte isCache;
	private int degree;
	private String file;
	private int seqLen;
	private int cacheSize;
	private byte debugLvl;
	private BTree T;
	private BTreeUtil util;
	private Cache cache;
	
	public static void main(string[] args){
		if (args.length < 4){
			System.out.println("ERROR: not enough arguments.");
			System.out.println("Usage: GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>");
		}
		else{ //try to parse information passed in
			try{
				GeneBankCreateBTree(args);
			}
			catch (NumberFormatException e){
				System.out.println("Error: 1 or more args is not a number: " + e.toString());
			}
		}
	}
	
	public GeneBankCreateBTree(String[] args)throws NumberFormatException{
		isCache =  Byte.parseByte(args[0]);  
		if (isCache == 0){
			degree = 103;  //4 4x + 4(x+) = 4096, x = 205, for 2t-1 = 205, t = 103			
		}
		else{
			degree = Integer.parseInt(args[1]);
		}
		
		file = args[2];
		seqLen = Integer.parseInt(args[3]);
		
		if (args.length > 4){
			cacheSize = Integer.parseInt(args[4]);
		}
		if (args.length > 5){
			debugLvl = Byte.parseByte(args[5]);
		}
		
		//if made it this far - create tree and start passing in keys with BTreeUtil
		Tree = new BTree(degree);
		util = new BTreeUtil();
		cache = new Cache(cacheSize);
		String str = util.buildGeneBankString(file);
		String strArray() = util.getSubsequences(seqLen, str);
		
		//convert strings into keys and cache 
		for(int i = 0; i < strArray.size(); i++){
			Long tempBase = util.converStringToLong(strArray(i));
			Sequence tempSeq = new Sequence(tempBase);
			int indx = cache.indexOf(tempSeq);  //returns -1 if not found
			if (indx != -1){
				cache.get(indx).incrementFreq();
			}
			else if (cache.size() < cacheSize){
				cache.addObject(tempSeq);
			}
			else{  //seq not yet seen and no room in cache
				Tree.insert(cache.removeFirst());  //make room by pulling out of cache and inserting into BTree
				cache.addObject(tempSeq);  //now is in cache
			}
		}
		
		//now dump all sequences in cache into btree
		while(!cache.isEmpty()){
			Tree.insert(cache.removeFirst());
		}
        
		//dump tree to binary file
	}
	
}