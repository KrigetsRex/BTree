/**
 * ***********************************
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
 *************************************
 */

//java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.List;

public class GeneBankCreateBTree {

    //private variables
    private static byte isCache;
    private static int degree;
    private static String gbkFile;
    private static int seqLen;
    private static int cacheSize;
    private static byte debugLvl;
    private static BTree<Sequence> tree;
    private static BTreeUtil util;
//    private static Cache cache;

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("ERROR: not enough arguments.");
            System.out.println("Usage: GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>");
        }
        else { //try to parse information passed in
            try {
                isCache = Byte.parseByte(args[0]);
                if (isCache == 0) {
                    degree = 103;  //4 4x + 4(x+) = 4096, x = 205, for 2t-1 = 205, t = 103
                } else {
                    degree = Integer.parseInt(args[1]);
                }

                gbkFile = args[2];
                seqLen = Integer.parseInt(args[3]);

                if (args.length > 4) {
                    cacheSize = Integer.parseInt(args[4]);
                }
                if (args.length > 5) {
                    debugLvl = Byte.parseByte(args[5]);
                }



                //if made it this far - create tree and start passing in keys with BTreeUtil
                tree = new BTree(degree);

//                cache = new Cache(cacheSize);
                String str = BTreeUtil.buildGeneBankString(gbkFile);
                List<String> strArray = BTreeUtil.getSubsequences(seqLen, str);

                //convert strings into keys and cache
                for (int i = 0; i < strArray.size(); i++) {
                    Long tempBase = BTreeUtil.convertStringToLong(strArray.get(i));
                    Sequence tempSeq = new Sequence(tempBase);

                    //Ignore cache for right now, it's screwed up
//                    int indx = cache.indexOf(tempSeq);  //returns -1 if not found
//                    if (indx != -1) {
//                        cache.get(indx).incrementFreq();
//                    } else if (cache.size() < cacheSize) {
//                        cache.addObject(tempSeq);
//                    } else {  //seq not yet seen and no room in cache
//                        tree.insert(cache.removeFirst());  //make room by pulling out of cache and inserting into BTree
//                        cache.addObject(tempSeq);  //now is in cache
//                    }
                    // System.out.println(tempBase);
                    tree.insert(tempSeq);
                }

                //now dump all sequences in cache into btree
//                while (!cache.isEmpty()) {
//                    tree.insert(cache.removeFirst());
//                }


                String BTreeFileName = BTreeUtil.createBTreeFileName(gbkFile, seqLen, degree);

                tree.forEach(System.out::println);

//
////
                try {
                    tree.write(new FileOutputStream(BTreeFileName));  //BTreeFileName
//                    BTree.Key<Sequence> result = BTree.search(new ZipFile(BTreeFileName), new Sequence(78));
//                    System.out.println("Found sequence " + result.key + " " + result.count + " times");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }


            }
            catch (NumberFormatException e) {
                System.out.println("Error: 1 or more args is not a number: " + e.toString());
            }
        }
    }

}