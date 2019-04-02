
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.lang.Math.*;

/**
 * proj2.java
 * 
 * Class which contains the main line.  User is prompted for an input file name
 * and an output file name.  These read txt files formatted in the way perscribed.
 * 
 * @author Thomas "Andy" Archer
 *
 */

public class proj2 {
	
	//max tree size
	public static int MAX_TREE_SIZE = 253;
	
	//the tree to be made
	Tree tree;
	
	//pretraversal
	public static char[] pretrav = new char[MAX_TREE_SIZE];
	//posttraversal
	public static char[] posttrav = new char[MAX_TREE_SIZE];
	//size of the tree as determined by reading the input files.
	public static int treesize;
	
	//List of the relation queries.
	public static ArrayList<String> quest;
	
/**
 * Project 2!  Builds a tree as determined by input files, and answers questions about
 * the relationships between nodes.
 * 
 * @param input Scanner - file to be read as input.
 * @param outputFileName - String - name of the output file
 */
	public proj2( Scanner input, String outputFileName ){
		
		//input file
		Scanner in = input;
		//output file
		PrintWriter out = null;
		
		//put the output in Output.txt
		try {
			out = new PrintWriter("Output.txt");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		String line = new String();
		
		//creates the list of relationship queries.
		this.quest = new ArrayList<String>();
		
		//while loop for reading the input file one line at a time.
		while (in.hasNextLine()){
			line = in.nextLine();
			
			input( line );
		}
		
		//recursively build the tree
		this.tree = new Tree( buildTree(treesize, 0, 0) );
		//solve all the relationship questions
		out.print( solveQuest() );
		//print out the level
		out.print( tree.levelTraversal( treesize ));
			
		//close the files
		in.close();
		out.close();
		
	}
	
/**
 * Solves all the relationship questions as determined by the input file.
 * 
 * @return String - returns a String containing the answers to all the relationship
 * 					questions
 */
	public String solveQuest(){
		String fam_c_a = new String();
		String fam_c_b = new String();
		String solution = new String("");
		
		Node fam_n_a;
		Node fam_n_b;
		
		int a_to_b;
		int b_to_a;
		
		while( !(quest.isEmpty())){
			a_to_b = 0;
			b_to_a = 0;
			fam_c_a = quest.remove(0);
			fam_c_b = quest.remove(0);
			fam_n_a = tree.findByElement( fam_c_a.charAt(0) );
			fam_n_b = tree.findByElement( fam_c_b.charAt(0) );
			
			tree.markToRoot( fam_n_a );
			b_to_a = tree.countMarks( fam_n_b );
			tree.clearMarks( treesize );
			tree.markToRoot( fam_n_b );
			a_to_b = tree.countMarks( fam_n_a );
			tree.clearMarks( treesize );
			
			//insert if tree for relations
			if ( a_to_b == 0){
				if (b_to_a == 0){
					solution += fam_c_a + " is " + fam_c_b + ".\n";
				}
				if (b_to_a == 1){
					solution += fam_c_a + " is " + fam_c_b + "'s parent.\n";
				}
				if (b_to_a == 2){
					solution += fam_c_a + " is " + fam_c_b + "'s grandparent.\n";
				}
				if (b_to_a == 3){
					solution += fam_c_a + " is " + fam_c_b + "'s great-grandparent.\n";
				}
				if (b_to_a > 3){
					solution += fam_c_a + " is " + fam_c_b + "'s ";
					for(int i = 0; i < (b_to_a - 2); i++){
						solution += "great ";
					}
					solution += "grand-parent.\n";
				}
			}
			
			if( a_to_b == 1){
				if( b_to_a == 0){
					solution += fam_c_a + " is " + fam_c_b + "'s child.\n";
				}
				if( b_to_a == 1){
					solution += fam_c_a + " is " + fam_c_b + "'s sibling.\n";
				}
				if( b_to_a == 2){
					solution += fam_c_a + " is " + fam_c_b + "'s aunt/uncle.\n";
				}
				if( b_to_a > 2){
					solution += fam_c_a + " is " + fam_c_b + "'s";
					for( int j = 0; j < (b_to_a - 2); j++){
						solution += " great";
					}
					solution += "aunt/uncle.\n";
				}
			}
			
			if ( a_to_b == 2){
				if ( b_to_a == 0){
					solution += fam_c_a + " is " + fam_c_b + "'s grandchild.\n";
				}
				if ( b_to_a == 1){
					solution += fam_c_a + " is " + fam_c_b + "'s niece/nephew.\n";
				}
			}
			
			if ( a_to_b > 3 && b_to_a == 0){
				solution += fam_c_a + " is " + fam_c_b + "'s";
				for( int k = 0; k < (a_to_b - 2); k++ ){
					solution += " great";
				}
				solution += "-grandchild.\n";
			}
			
			if ( a_to_b > 2 && b_to_a == 1){
				solution += fam_c_a + " is " + fam_c_b + "'s";
				for( int l = 0; l < a_to_b - 2; l++){
					solution += " great";
				}
				solution += " niece/nephew.\n";
				}
			
			if ( a_to_b >= 2 && b_to_a >= 2){
				solution += fam_c_a + " is " + fam_c_b + "'s ";
				int cousin = Math.min( a_to_b , b_to_a) - 1;
				int removed = Math.abs( a_to_b - b_to_a);
				solution += "" + cousin + "th cousin " + removed + " times removed.\n";
			}
		}
		
		return solution;
	}

/**
 * Processes the input to sort the pretraversal, posttraversal, and relationship 
 * questins
 * 
 * @param input - String - line of the input file to be read and processed.
 */
	public void input ( String input ){
		char a;
		int size = 0;
		//process the pretraversal
		if (input.length() > 0 && input.charAt(0) == '<'){
			while (input.length() > 0){
				a = input.charAt(0);
				if ( Character.toString( a ).matches("[.]")){
					input = input.substring(1, input.length());
					break;
				}
				if ( !( Character.toString( a ).matches("[<>, ]") ) ){
					this.pretrav[ size ] = a;
					size++;
				}
				input = input.substring(1, input.length());
			}
		}
		//process the posttraversal
		if (input.length() > 0 && input.charAt(0) == '>'){
			while (input.length() > 0){
				a = input.charAt(0);
				if ( Character.toString( a ).matches("[.]")){
					input = input.substring(1, input.length());
					break;
				}
				if ( !( Character.toString( a ).matches("[<>, ]") ) ){
					this.posttrav[ size ] = a;
					size++;
				}
				input = input.substring(1, input.length());
			}
			this.treesize = size;
		}
		//process the relationship queries.
		if (input.length() > 0 && Character.toString(input.charAt(0)).matches("[?]")){
			while (input.length() > 0){
				a = input.charAt(0);
				if ( Character.toString( a ).matches("[.]")){
					input = input.substring(1, input.length());
					break;
				}
				if ( !( Character.toString( a ).matches("[<>, ?]") ) ){
					quest.add(Character.toString(input.charAt(0)));
				}
				
				input = input.substring(1, input.length());
			}
		}
	}

/**
 * Starts the level order traversal.  This actually passes the work to the method
 * in the Tree class.
 * 
 * @return String - returns string of the level order traversal.
 */
	public String levelTraversal(){
		return tree.levelTraversal( treesize );
	}
/**
 * Recursively builds the tree with the input traversals and the size of the tree
 * to be built.  Returns the node which is the root of the tree / subtree processed.
 * 	
 * @param size - int - size of the tree to be processed
 * @param prestart - int - index of where the process starts in the pretraversal order
 * @param poststart - int - index of where the process starts in the posttraversal order
 * 
 * @return - Node - returns the root of the tree/sub tree
 */
	public Node buildTree( int size, int prestart, int poststart){
		//base case.  If a leaf, make a node and return it
		if (size == 1){
			return new Node( pretrav[prestart]);
		}
		//otherwise process recursively
		Node temp = new Node( pretrav[prestart]);
		int limit = poststart + size;
		while ((size - 1) > 0){
			int count = 1;
			prestart++;
			char temp_pre = pretrav[prestart];
			for (int i = poststart; i < limit; i++){
				if (Character.toString( temp_pre ).matches( Character.toString( posttrav[i]))){
					break;
				}
				count++;
			}
			temp.addChild(buildTree( count, prestart, poststart));
			poststart+= count;
			prestart += (count - 1) ;
			size -= count;
		}
		int child_count = temp.getNumbChildren();
		for (int j = 0; j < child_count; j++ ){
			(temp.getChild( j )).setParent( temp );
		}
		
		return temp;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
//		new proj2( new Scanner( new File( args[0] ) ) );
		Scanner user_input = new Scanner( System.in );
		String inputFileName;
		System.out.println("Please Enter Input File Name:");
		inputFileName = user_input.nextLine().trim();
		
		Scanner input = new Scanner( new File( inputFileName) );
		
		String outputFileName;
		System.out.println("Please Enter Output File Name:");
		outputFileName = user_input.nextLine().trim();
		
		new proj2( input, outputFileName );
	}
	
}
