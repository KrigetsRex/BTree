/*****************************************
 *  BTree
 *  CS321
 *  Class for container type BTree
 *  Contains the methods pertaining to 
 *  creating and editing a BTree
 ****************************************/


import java.io.PrintWriter;
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
				Sequence middleKey = node.getMiddleSeq();
				node.addElement(key);
				promoteRecursive(node.getParent(), middleKey);
			}
		}
		else{ //propogate key down the tree
			ArrayList<Node> keys = node.getElements();
			ArrayList<Sequence> Children = node.getChildren();  //will be 1 more child than # of keys
			Node newNode = null;
			for (int i = 0; i < keys.size(); i++){
				if (i < keys.size()-1){
					if (key > keys(i) && key < keys(i+1)){
						//must be right of cur position -- do nothing goto check next key
					}
					else{
						newNode = Children(i);  //found it, must be left of cur position
						break;
					}
				}
				else{  //made it to end of list without finding spot, must be far right
					newNode = Children(i+1);
				}
			}
			addRecursive(newNode, key);
		}
	}

	/**
	 * recursive method to promote an element up the tree
	 * @param node: the node in which the promoted key will attempt to occupy
	 * @param key: the key to be promoted
	 */
	private void promoteRecursive(BTreeNode node, Object key){
		//end case
		if (node.getReference().length() < MAXELEMENTS){
			node.addReference(key);
		}
		else {
			//get middle key - which will need to be promoted again
			Sequence middleKey = node.getMiddleSeq();
			
			{  //adding this scope so that recursive calls do not eat up memory
			
				/*add passed in key to list of elements
				  also get which side of the new node is unbalanced*/
				byte side = node.addElement(key);
				
				//take all elements < middle-key and make new node - attach this new node to current node.parent
				Node leftNode, rightNode;
				ArrayList<Sequence> newElements = new ArrayList<Sequence>();
				ArrayList<Sequence> curElements = node.getElements();
				ArrayList<Node> newChildren = new ArrayList<Node>();
				ArrayList<Node> curChildren = node.getChildren();
				
				for (int i = 0; i < MAXELEMENTS / 2 + side; i++){
					newElements.add(curElements(i);
				}
				if (curChildren != null){
					for (int i = 0; i < MAXCHILDREN / 2 + side; i++){
						newChildren.add(curChildren(i);
					}
				}
				leftNode = new Node(node.getParent(), newElements, newChildren);
				
				//and take all elements > middle-key and make new node - attach this new node to current node.parent
				ArrayList<Sequence> newElements = new ArrayList<Sequence>();
				ArrayList<Node> newChildren = new ArrayList<Node>();
				
				for (int i = MAXELEMENTS / 2 + side; i < MAXELEMENTS; i++){
					newElements.add(curElements(i);
				}
				if (curChildren != null){
					for (int i = MAXCHILDREN / 2 + side; i <= MAXCHILDREN; i++){
						newChildren.add(curChildren(i);
					}
				}
				rightNode = new Node(node.getParent(), newElements, newChildren);
				
				//fix children list to reflect loss of this node and the addition of the 2 new ones.
				if (node.getParent() == null){
					root = new Node(null, middleKey, null);
					leftNode.setParent(root);
					rightNode.setParent(root);
					ArrayList<Node> temp = new ArrayList<Node>();
					temp.add(leftNode);
					temp.add(rightNode);
					root.setChildren(temp);
				}
				else{
					ArrayList<Node> siblings = node.getParent().getChildren();
					int index = siblings.indexOf(node);
					siblings.set(index, leftNode);
					siblings.add(index + 1, rightNode);
					node.getParent().setChildren(siblings);	
				}
			}  /*end of local variable scope
			     side, leftNode, rightNode, newElements, curElements, newChildren, curChildren, and siblings should be gone*/
			if (node.getParent() == null){/*done*/} else{
				promoteRecursive(node.getParent(), middleKey);
			}
		}
	}

	
	/*
	 * Inner Node class
	 * container for keys which includes the list of keys, a list of children, and a pointer to its parent
	 */
	private class Node implements Serializable {
		//public variables
		public static final byte LEFT = 1;
		public static final byte RIGHT = 0;
		
		//private variables
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
		
		private byte addElement(Sequence key){
			byte side = LEFT;
			if (elements.size() == 0){
				elements.add(key);
			} else {
				if (key.getBases() < elements.get(0).getBases()){  //key goes at front
					elements.add(0, key);
				} else if (key.getBases() == elements.get(0).getBases()){ //dump at front
					elements.get(0).incrementFreq();
				}else if (key.getBases() > elements.get(elements.size()-1).getBases()) { //key goes at back
					elements.add(key);
					side = RIGHT;
				} else if(key.getBases() == elements.get(elements.size()-1).getBases()){ //dump at back
					elements.get(elements.size()-1).incrementFreq();
					side = RIGHT;
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
					if (i > elements.size() / 2){
						side = RIGHT;
					}
					if (i == elements.size()-1){
						System.out.println("Warning, key not slotted: " + key);
					}
				}
			}
			return side;
		}
		
		private Sequence getMiddleSeq(){
			Sequence retVal = null;
			
			if ((elements.size()%2) == 0){
				System.out.println("Warning: no middle sequence");
				return retVal;
			} else {
				retVal = elements.remove(elements.size() / 2);
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
                
                
                /**
                * Recursively traverses the Btree from the root
                * printing out the contents of the nodes through an in-order 
                * traversal
                * 
                * @param node - pass in a node within the tree (root)
                */
               public void inOrderTraversal(Node node, PrintWriter writer){
                   for(int i = 0; i < node.elements.length; i++){
                       if(null != node.children){
                           inOrderTraversal(node.children.get(i), writer);
                       }
                       writer.println(node.elements.get(i).getFrequency()+ " " + converLongToString(node.elements.get(i).getKey()));
                   }
               }


               /**
                * Uses the inOrderTraversal method to walk through a tree and output
                * the frequency and string value to a new txt file.
                * 
                * @param node - The root of the tree
                */
               public void dumpTree(Node node){
                   try{
                       PrintWriter writer = new PrintWriter("dump.txt", "UTF-8");
                       inOrderTraversal(node, writer);
                       writer.close();
                   }
                   catch(Exception e){
                       System.out.println("There was an issue:" + e);
                   }
               }
	}
}
