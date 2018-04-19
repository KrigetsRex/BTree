/*****************************************
 *  BTree
 *  CS321
 *  Class for container type BTree
 *  Contains the methods pertaining to 
 *  creating and editing a BTree
 ****************************************/
 
 
 import java.util.List;
 
 /**
  * BTree Class
  */
  public class BTree{
	  //Private Variables
	  private int t;  //the degree of the BTree
	  private int MAXELEMENTS;
	  private int MAXCHILDREN;
	  private BTreeNode root;
	  
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
	  public void addElement(Object key){
		  
		if (root == null){
			root = new BTreeNode(key);
		}
		else if ((root.getChildren().length() == 0) && (root.getReference().length() < MAXELEMENTS)){
			root.addReference(key);
		}
		else {
			addRecursive(root, key);
		}				 
	  }
	  
	  //Private Methods
	  /**
	   * recursive method to find the leaf in which the key should reside and add it there
	   * if leaf is full then it will promote the appropriate key and add it to the parent 
	   * node via a recursive call to promote.
	   * @param node: the node in which to add the element
	   * @param key: the element which is to be added to the node
	   */
	  private void addRecursive(BTreeNode node, Object key){
		//end case
		if ((node.getChildren().length() == 0){
			if (node.getReference().length() < MAXELEMENTS)){
				node.addReference(key);
			}
			else{ 
				//get middle element
				//add key
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
			  //take all elements < middle-key and make new node - attach this new node to current node.parent,
			  //and take all elements > middle-key and make new node - attach this new node to current node.parent,
			  //promoteRecursive(node.getParent(), key);
		  }
		  
	  }
  }