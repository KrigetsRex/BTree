/*****************************************
 *  BTree
 *  CS321
 *  Class for container type BTree
 *  Contains the methods pertaining to 
 *  creating and editing a BTree
 ****************************************/


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BTree Class
 */
public class BTree<T extends Comparable<? super T> & Serializable> implements Serializable {
	//Private Variables
	private int t;  //the degree of the BTree
	private int MAXELEMENTS;
	private int MAXCHILDREN;
	private Node root;

	//Public Methods

	/**
	 * Constructor
	 */
	public BTree(int degree){
		this.t = degree;
		this.MAXELEMENTS = (2*t) - 1;
		this.MAXCHILDREN = MAXELEMENTS + 1;
	}

	/**
	 * add key to a node in the tree
	 * @param key: the element to be added to the tree
	 */
	public void addElement(Sequence key){

		//first case - create root  
		if (root == null){
			root = new Node(null, key);
		}

		//special case - add elements to root w/o child
		else if ((root.getChildren().size() == 0) && (root.getElements().size() < MAXELEMENTS)){
			root.addElement(key);
		}

		// all other cases
		else {
			addRecursive(root, key);
		}				 
	}

	//Private Methods
	/**
	 * recursive method to find the leaf in which the key should reside and add it there
	 * if leaf is full then it will promote the appropriate key and add it to the parent 
	 * node via a recursive scall to promote.
	 * @param node: the node in which to add the element
	 * @param key: the element which is to be added to the node
	 */
	private void addRecursive(Node node, Sequence key){
		//end case - cur node is a leaf
		if ((node.getChildren().size() == 0)){
			if (node.getElements().size() < MAXELEMENTS){
			node.addElement(key);
		}
		else{ 
			//get middle element
			//add key
			//remove middle element from node
			//promote middle element to parent node
		}
		}
		//else check key against existing keys and call addRecursive(correct-child-node, key)
	}

	/**
	 * recursive method to promote an element up the tree
	 * @param node: the node in which the promoted key will attempt to occupy
	 * @param key: the key to be promoted
	 */
	private void promoteRecursive(BTreeNode node, Object key){
		//end case
		if node.getReference().length() < MAXELEMENTS{
			node.addReference(key);
		}
		else {
			//get middle key - which will need to be promoted
			//add passed in key to list of elements
			//take all elements < middle-key and make new node - attach this new node to current node.parent,
			//and take all elements > middle-key and make new node - attach this new node to current node.parent,
			//promoteRecursive(node.getParent(), middleKey);
		}
	}


	private class Node implements Serializable {
		private Node parent;
		private ArrayList<Sequence> elements;
		private transient ArrayList<Node> children;
		
		private Node(Node parent, ArrayList<Sequence> elements, ArrayList<Node> children){
			this.parent = parent;
			this.elements = elements;
			this.children = children;
		}
		
		private Node(Node parent, Sequence firstElement){
			this.parent = parent;
			this.elements = new ArrayList<Sequence>();
			elements.add(firstElement);
			
			children = new ArrayList<Node>();
		
		}
		
		private void addElement(Sequence key){
			if (elements.size() == 0){
				elements.add(key);
			} else {
				
				
				if (key.getBases() < elements.get(0).getBases()){  //key goes at front
					elements.add(0, key);
				} else if (key.getBases() == elements.get(0).getBases()){ //dup at front
					elements.get(0).incrementFreq();
				}else if (key.getBases() > elements.get(elements.size()-1).getBases()) { //key goes at back
					elements.add(key);
				} else if(key.getBases() == elements.get(elements.size()-1).getBases()){ //dup at back
					elements.get(elements.size()-1).incrementFreq();
				}else { //key goes in middle somewhere or is duplicate 
					int i = 0;
					boolean placed = false;
					
					while(i < elements.size()-1 && !placed){ // a 0 b 1 c 2 d 3 e 4f
						// key is in-between two values
						if(key.getBases() > elements.get(i).getBases() && key.getBases() < elements.get(i+1).getBases()){
							placed = true;
							elements.add(i+1, key);
						} else if (key.getBases() == elements.get(i+1).getBases()){ //key is a duplicate
							placed = true;
							elements.get(i+1).incrementFreq();
						} else {
							i++;
						}
					
					}
					
					if (i == elements.size()-1){
						System.out.println("Warning, key not slotted: " + key);
					}
				}
			}
		}
		
		private Sequence getMiddleSeq(){
			Sequence retVal = null;
			
			if ((elements.size()%2) == 0){
				System.out.println("Warning: no middle sequence");
				return retVal;
			} else {
				retVal = elements.get(elements.size() / 2);
			}
			
			return retVal;
			
		}
		
		private Node getParent(){
			return parent;
		}
		
		private void setParent(Node parent){
			this.parent = parent;
		}

		public ArrayList<Sequence> getElements() {
			return elements;
		}

		public void setElements(ArrayList<Sequence> elements) {
			this.elements = elements;
		}

		public ArrayList<Node> getChildren() {
			return children;
		}

		public void setChildren(ArrayList<Node> children) {
			this.children = children;
		}
		
		public Node addChildNode(Sequence element){
			Node retVal = new Node(this, element);
			children.add(retVal);
			
			return retVal;
		}
		
		
		
		
		
	}
}