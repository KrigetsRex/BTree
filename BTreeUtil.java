
import java.util.ArrayList;
import java.util.List;


/**
 * Utility class for data manipulation
 * 
 * @author jacobphillip
 */
public class BTreeUtil {
    
    /**
     * Converts a DNA strand into a binary string, and then into a
     * Long
     * @param dna - the String of type [A,T,C,G];
     * @return - new Long value that represents the DNA string
     */
    public static Long converStringToLong(String dna){
        
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
     * @return - original DNA string value
     */
    public static String converLongToString(Long key){
        String boolString = Long.toBinaryString(key);
       
        List<String> strArr = getParts("foobarspam", 2);
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

}