
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility class for data manipulation
 * 
 * @author jacobphillip
 */
public class BTreeUtil {

    /**
     * 
     * @param inputPath - path to the geneBank File
     * @return String - single string of all the DNA bases
     */
    public static String buildGeneBankString(String inputPath) {
            
        String str = "";
        try{
            File queryFile = new File(inputPath);
            Scanner input = new Scanner(queryFile, "UTF-8");
            
            while (input.hasNextLine()){
                String line = input.nextLine();
                if(line.contains("ORIGIN")){
                    boolean dnaBlock = true;
                    while(dnaBlock == true){
                        String newLine = input.nextLine();
                        if(!newLine.contains("//")){
                            //Strip out all the non-dna characters
                            str += newLine.replaceAll("[^AaGgTtCc]", "");
                        }
                        else{
                            dnaBlock = false;
                        }
                    }     
                }     
            }
        }
        catch(FileNotFoundException e){
             System.out.println("Exception occurred: " + e);  
        }
        
        return str;
    };
    
    
    /**
     * Takes in a single string and returns an ArrayList<String> 
     * of Strings.
     * 
     * @param subLength - length of the substring
     * @param dnaString - the DNA string to be sub-sequenced 
     * @return 
     */
    public static List<String> getSubsequences(int subLength, String dnaString){
        
        List<String> subSequences = new ArrayList<>();
        
        int length = dnaString.length();
        
        for(int i = 0; i < length; i++){
            if(i + subLength <= length){
                subSequences.add(dnaString.substring(i, i + subLength));
            }
        }
        
        return subSequences;
    }
    
    
    /**
     * Parses a query file for all strings and returns them in an arrayList
     * 
     * @param fileName
     * @return 
     */
     public static List<String> getQueryStrings(String fileName){
         
        List<String> queryStrings = new ArrayList<>();
         
        try{
            File queryFile = new File(fileName);
            Scanner input = new Scanner(queryFile, "UTF-8");
            
            while (input.hasNextLine()){ 
                //Shouldn't have to worry about white space, but if we do, 
                //add add this .replaceAll("\\s+","")
                queryStrings.add(input.nextLine());  
            }
        }
        catch(FileNotFoundException e){
             System.out.println("Exception occurred" + e);  
        }
        
        return queryStrings;
     } 
    
    
    
    
    /**
     * Converts a DNA strand into a binary string, and then into a
     * Long
     * @param dna - the String of type [A,T,C,G];
     * @return - new Long value that represents the DNA string
     */
    public static Long convertStringToLong(String dna){
        
        String str = "";
        
        char[] chars = dna.toCharArray();
        
        for(int i = 0; i < chars.length; i++){ 
            char upper = Character.toUpperCase(chars[i]);
            switch(upper){
                case 'A':
                    str += "00";
                    break;
                
                case 'T':
                    str += "11";
                    break;
                    
                case 'C':
                    str += "01";
                    break;
                    
                case 'G':
                    str += "10";
                    break;
            }
        }
        return Long.parseLong(str, 2);
    }
    
    /**
     * Converts a Long to a binary string, and then back to DNA
     * @param key - the Long value used as a key within the Btree
     * @param seqLen
     * @return - original DNA string value
     */
    public static String convertLongToString(Long key, Integer seqLen){

        String boolString =  Long.toBinaryString(key);

        Boolean sameLength = false;
        while(!sameLength){
            int length = boolString.length()/2;
            if(length >= seqLen){
                sameLength = true;
            }
            else{
                boolString = "00" + boolString;
            }
        }
       
        List<String> strArr = getParts(boolString, 2);
        int arrSize = strArr.size();
        String returnString = "";
        for(int i = 0; i < arrSize; i++){ 
            String str = strArr.get(i);
            switch(str){
                case "00":
                    returnString += "A";
                    break;
                
                case "11":
                    returnString += "T";
                    break;
                    
                case "01":
                    returnString += "C";
                    break;
                    
                case "10":
                    returnString += "G";
                    break;
            }
        }
        
        return returnString;
    }
    
    /**
     * Splits a string into a designated number of pieces
     * @param string 
     * @param partitionSize 
     * @return 
     */
    private static List<String> getParts(String string, int partitionSize) {
        List<String> parts = new ArrayList<>();
        int len = string.length();
        for (int i=0; i<len; i+=partitionSize)
        {
            parts.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        return parts;
    }
    
    /**
     * Creates a new string name based on project specifications
     * @param gbkFile
     * @param k
     * @param t
     * @return 
     */
    public static String createBTreeFileName(String gbkFile, Integer k, Integer t){ 
        return gbkFile + ".btree.data." + k + "." + t; 
    }
   
    
}