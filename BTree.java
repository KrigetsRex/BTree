
/**
 * BTree Class for CS321
 * 
 * @author Keener
 * @author Luke Grice
 */
import java.util.ArrayList;
import java.util.Collections;

public class BTree {

	Node root;
	int t;   							//minimum degree
	int maxKeys;
	int maxChildren;

	public BTree(int t){
		this.t = t;
		root = null;

		this.maxChildren = 2 * t;
		this.maxKeys =  maxChildren - 1;
		
		
		

	}
	
	/*
	 * Inserts a key into the Btree
	 * 
	 * k - sequence containing a long of binary bases
	 */

	public void insert(Sequence k){
		
		System.out.println("INSERT: " + k.getBases());
		
		
		// special case - empty tree
		if (root == null){			
			root = new Node(true);
			root.keys.add(k);
		}else{
			
		//non-empty trees
			
			//if root is full
			if (root.keys.size() == maxKeys){
				
				//creates a new node to make as root
				Node newRoot = new Node(false);
				
				//adds the current root as a child
				newRoot.children.add(root);      
				
				//splits the full root
				newRoot.splitChild(0, root);
				
				//determines child position for new sequence
				int i = 0;
				if (newRoot.keys.get(0).getBases() < k.getBases()){
					i++;
				}
				
				//adds sequence to specified child
				newRoot.children.get(i).insertNonFull(k);
				
				//re-assigns root
				root = newRoot;
				
				
			} else {
				//if root is not full, determine proper position of sequence
		
				root.insertNonFull(k);
			}
			

		}
		
	}
	
	//currently not working, should traverse the tree starting at the root
	
	public void fullTraverse(){
		root.traverse();
	}
	
	/*
	 * Returns the root node
	 */
	
	public Node getRoot(){
		return root;
	}
	

	/*
	 * Inner class for nodes
	 */

	//class is currently public - making it private made some testing difficult. Not sure if there's a workaround
	public class Node{
		ArrayList<Sequence> keys;
		ArrayList<Node> children;
		boolean isLeaf;

		/*
		 * Constructor
		 */
		
		public Node(boolean isLeaf){
			this.isLeaf = isLeaf;
			keys = new ArrayList<Sequence>();
			children = new ArrayList<Node>();

		}
		
		/*
		 * Method to insert a given key into this node. Should only be called if a node is not full
		 */

		
		public void insertNonFull(Sequence k){
						
			//get the right-most element index
			int i = keys.size() - 1;
			
			//if leaf, determine the correct position of the new key
			if (isLeaf){
				while (i >= 0 && keys.get(i).getBases() > k.getBases()){
					i--;
				}
				//insert key at location
				keys.add(i+1, k);
			} else {
				//if not leaf, find child that will get new key
				
				while (i >= 0 && keys.get(i).getBases() > k.getBases()){
					i--;
				}
				
				//check if that child is full
				if (children.get(i+1).keys.size() == maxKeys){
					
					//if so, split the child
					splitChild(i+1, children.get(i+1));
					
					
					//after split, check which new branch is going to have new key
					if (keys.get(i+1).getBases() < k.getBases()){
						i++;
					}
					

				}
				
				children.get(i+1).insertNonFull(k);
			}
		}
		
		
		/*
		 * Splits a full node, using i as the split index
		 */
		
		public void splitChild(int i, Node fullNode){

			//make a new node to contain some of fullnodes keys
			Node newNode = new Node(fullNode.isLeaf);
			
			//copy last t-1 keys of fullnode into the newnode
			for (int j = 0; j < t -1; j++){
				newNode.keys.add(fullNode.keys.get(j+t));
			}
			
			//copy the last t children of fullnode into newnode
			
			if (!fullNode.isLeaf){
				for (int j = 0; j < t; j++){
					newNode.children.add(fullNode.children.get(j+t));
				}
				
				//remove old nodes that have now been transfered
				
				for (int o = 0; o < t; o++){
					fullNode.children.remove(fullNode.children.size()-1);
				}
				
			}
			
			
			
			//link new child to node
			
			this.children.add(i+1, newNode);
			
			
			//add the middle key of full node into the new node
			this.keys.add(i, fullNode.keys.get(t-1));
			
			// remove old keys that have now been split
			for (int o = fullNode.keys.size()-1; o>= t-1 ; o--){
				fullNode.keys.remove(o);
			}
		}
		
		/*
		 * Should search the btree when called on the node. 
		 * 
		 * Currently untested.
		 */
		
		public Node search(Sequence k){
			
			int i = 0;
			
			while (i < keys.size() && k.getBases() > keys.get(i).getBases()){
				i++;
			}
			
			if (keys.get(i).getBases() == k.getBases()){
				return this;
			}
			
			if (isLeaf){
				return null;
			}
			
			return children.get(i).search(k);
		}
		
		public void traverse(){
			int i;
			
			System.out.println("tr start");
			
			for (i = 0; i < keys.size(); i++){
				if(!isLeaf){
					
					
					children.get(i).traverse();
					
				}
			}
			
			if (!isLeaf){
				children.get(i).traverse();
			}
			
		}
		
		public ArrayList<Sequence> getKeys() {
			return keys;
		}
		
		
		


	}

}
