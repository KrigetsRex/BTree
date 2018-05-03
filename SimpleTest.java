import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipFile;

public class SimpleTest {
    public static void main(String[] args) {
        BTree<Sequence> tree = new BTree<Sequence>(2);
        //TODO fix an issue where BTree does not completely fill non-root nodes (with t = 2, only adds 2 elements, and leaf nodes have 1 element each)
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 3; j++) {
                tree.insert(new Sequence(i));
            }
        }

        try {
            tree.write(new FileOutputStream("tree.zip"));
            BTree.Key<Sequence> result = BTree.search(new ZipFile("tree.zip"), new Sequence(78));
            System.out.println("Found sequence " + result.key + " " + result.count + " times");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
