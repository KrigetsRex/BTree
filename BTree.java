import java.io.Serializable;
import java.util.List;

public class BTree<T extends Comparable<? super T> & Serializable> implements Serializable {
	private class Node {
		private Node parent;
		private List<T> elements;
		private List<Node> children;
	}
}