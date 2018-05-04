import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipFile;

public class SimpleTest {
    public static void main(String[] args) {
        long start;
        start = System.nanoTime();
        System.out.println("Creating B-Tree");
        BTree<Sequence> tree = new BTree<Sequence>(10);
        //TODO fix an issue where BTree does not completely fill non-root nodes (with t = 2, only adds 2 elements, and leaf nodes have 1 element each)
        for(int i = 0; i < 100000; i++) {
            for(int j = 0; j < 1000; j++) {
                tree.insert(new Sequence(i));
            }
        }
        System.out.println("Done in " + ((double)(System.nanoTime() - start)) / 1000000 + "ms");

        try {
            System.out.println("Writing B-Tree");
            start = System.nanoTime();
            tree.write(new FileOutputStream("tree.zip"));
            System.out.println("Done in " + ((double)(System.nanoTime() - start)) / 1000000 + "ms");
            System.out.println("Searching B-Tree");
            start = System.nanoTime();
            BTree.Key<Sequence> result = BTree.search(new ZipFile("tree.zip"), new Sequence(78));
            System.out.println("Done in " + ((double)(System.nanoTime() - start)) / 1000000 + "ms");
            System.out.println("Found sequence " + result.key + " " + result.count + " times");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
