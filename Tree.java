import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.ArrayList;


/**
 * Tree.java
 * 
 * Class to create a tree which is dually linked between parent and children.
 * 
 * @author Thomas "Andy" Archer
 *
 */
public class Tree {
	//root of the tree
	public Node root;
	
/**
 * Constructor for the tree.
 * 
 * @param root - Node - the desired root of the tree.
 */
	public Tree (Node root){
		this.root = root;
	}

/**
 * Determines if the tree is empty.
 * 
 * @return boolean - returns true if tree has no root. 
 * 					 returns false if the tree has a root.
 */
	public boolean isEmpty(){
		return root == null;
	}
	
/**
 * Provides a string with the level order traversal of the tree.
 * 
 * @param numbNodes - int - number of nodes to traverse.
 * 
 * @return String - list of nodes by level, in order.
 */
	public String levelTraversal(int numbNodes){
		
		//Temporary list for traversal.
		ArrayList<Node> traversal = new ArrayList<Node>();
		//add the root to the list
		traversal.add( root );
		//temporary node for the traversal
		Node temp;
		
		//for loop to traverse all the nodes.
		for (int i = 0; i < numbNodes; i++){
			temp = traversal.get( i );
			int numbTempChildren = temp.getNumbChildren();
			
			for (int j = 0; j < numbTempChildren; j++){
				traversal.add( temp.getChild( j ));
			}
			
			if(traversal.size() == numbNodes){
				break;
			}
		}
		
		//string of the traversal
		String output = new String("");
		
		//add all the nodes to the string
		output += (traversal.remove(0)).getElement();
		
		//create the string with a while loop until the temporary list is empty
		while (!(traversal.isEmpty())){
			output += ", " + (traversal.remove( 0 )).getElement();			
		}
		
		output += "\n";
		
		return output;
	}
	
	/**
	 * Searches the tree to find the node which contains the unique key.
	 * 
	 * 
	 * @param element - char - key to look for in the tree
	 * @return Node - Node - returns the node searched for.
	 * 						 returns null if element is not found in tree.
	 */
	public Node findByElement( char element ){
		Node temp;
		ArrayList<Node> traversal = new ArrayList<Node>();
		traversal.add(root);
		int tempNumbChildren = 0;
		
		while( !(traversal.isEmpty())){
			temp = traversal.remove(0);
			if( Character.toString(temp.getElement()).matches( Character.toString(element))){
				return temp;
			}
			tempNumbChildren = temp.getNumbChildren();
			for( int i = 0; i < tempNumbChildren; i++){
				traversal.add( temp.getChild( i ));
			}
		}
		
		return null;
		
	}

/**
 * Recursively marks all the nodes from the starting node to the root of the tree.  This is 
 * used to determine the relation between two nodes.
 * 
 * @param start - Node - node to start at.
 */
	public void markToRoot( Node start ){
		start.markNode();
		if ( start.getParent() != null){
			markToRoot( start.getParent() );
		}
	}

/**
 * Recursively counts the number of parent nodes until a node is found which is 
 * marked.  This is used to determine the relation between Nodes.
 * 
 * @param start - Node - node to start counting at
 * 
 * @return int - returns the number of parent nodes until a marked node is found.
 */
	public int countMarks( Node start ){
		
		if (start.getMark() == 1){
			return 0;
		}
		
		return (1 + countMarks( start.getParent()));
	}

/**
 * Traverses the tree and makes sure all of the nodes have their marks set to off.
 * 
 * @param numbNodes - int - number of nodes to traverse.
 */
	public void clearMarks( int numbNodes ){
		
		ArrayList<Node> traversal = new ArrayList<Node>();
		traversal.add( root );
		Node temp;
		
		for (int i = 0; i < numbNodes; i++){
			temp = traversal.get( i );
			int numbTempChildren = temp.getNumbChildren();
			
			for (int j = 0; j < numbTempChildren; j++){
				traversal.add( temp.getChild( j ));
			}
			
			if(traversal.size() == numbNodes){
				break;
			}
		}
		
		while (!(traversal.isEmpty())){
			(traversal.remove(0)).clearMark();			
		}
	}
}
