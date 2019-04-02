
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Node.java Class
 * 
 * Creates the nodes for the tree class.
 * 
 * @author Thomas "Andy" Archer
 *
 */

public class Node {
		//Node to the parent.  If root, will be Null.
		Node parent;
		//An ArrayList of any children this node may have.
		ArrayList<Node> children;
		//Used to determine relation.
		int mark;
		//number of children this Node has
		int numbChildren;
		//The element of the Node
		char element;
		
/**
 * Constructor for the Node.
 * 		
 * @param element - char - element of the Node
 */
		public Node (char element){

			this.element = element;
			this.parent = null;
			this.mark = 0;
			this.children = new ArrayList<Node>();
		}
		
/**
 * Method to mark the node.  Turns on the mark.  Used to determine relation.
 */
		public void markNode(){
			this.mark = 1;
		}
/**
 * Method to show if the node is marked or not.
 * 		
 * @return - int - 1 if marked, 0 if not marked.
 */
		public int getMark(){
			return mark;
		}

/**
 * Method to clear the mark, if the node has one.
 */
		public void clearMark(){
			this.mark = 0;
		}

/**
 * Gets the element in the Node.
 * 
 * @return char - character of this node
 */
		public char getElement(){
			return element;
		}

/**
 * Sets the parent of this node.  Makes a dually linked tree.
 * 
 * @param parent Node - parent of this node.
 */
		public void setParent( Node parent ){
			this.parent = parent;
		}

/**
 * Gets the parent of this node.
 * 
 * @return Node - parent of this node.
 */
		public Node getParent(){
			return this.parent;
		}
	
/**
 * Gets the number of children this node has.
 * 
 * @return int - number of children of this node.
 */
		public int getNumbChildren(){
			return children.size();
		}

/**
 * Gets the child of this Node at a given index.
 * 
 * @param child int - index of which child to return. 0-n going from left to right.
 * 
 * @return Node - node of the requested child.  Returns null if not a valid index or there
 * 				  are no children.
 */
		public Node getChild( int child ){
			//check for valid index
			if (child >= children.size()){
				return null;
			}
			//check for valid index
			if (child < 0){
				return null;
			}
			return children.get(child);
		}

/**
 * Adds a child to the given node.
 * 
 * @param child - Node - node of the child to add to this node
 */
		public void addChild( Node child ){
			children.add(child);
		}
	
/**
 * Is this the root of the tree? 
 * @return boolean - returns true if the parent is null.
 * 					 returns false if there is a valid parent node.
 */
		public boolean isRoot(){
			return parent == null;
		}
		
	}
