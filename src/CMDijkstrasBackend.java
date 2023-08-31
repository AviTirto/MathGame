// --== CS400 Fall 2022 File Header Information ==--
// Name: Max Levy
// Email: mllevy3@wisc.edu
// Team: AQ
// TA: Minghao
// Lecturer: Gary Dahl
// Notes to Grader: NONE

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * The class CMDijkstrasBackend defines a CMDijkstrasBackend object and all of the methods
 * associated with it. This class is utilized by frontend to add functionality to the frontend GUI.
 * 
 * Implements ICMDijkstrasBackend.java
 */
public class CMDijkstrasBackend implements ICMDijkstrasBackend {
  
  // instance variables
  protected LinkedList<String> current;
  protected Graph<String, Integer> graph;
  private IMathLoader mathLoader;
  private int points;
  
  /**
   * A constructor defining the CMDijkstrasBackend object. This constructor DOES NOT use
   * placeholder classes.
   */
  public CMDijkstrasBackend() {
    this.current = new LinkedList<String>();
    this.graph = new Graph<>(); // after integration, change to AE class name
    this.mathLoader = new MathLoader(); // after integration, change to DW class name
    LinkedList<String> vertices = new LinkedList();
    Number[][] edges;
    this.points = 0;
    
    // create graph
    try {
      vertices = this.mathLoader.loadVertices(); // returns ArrayList of vertices from DW
      edges = this.mathLoader.loadEdges(); // returns 2D String array of edges from DW
      
      for (int i = 0; i < vertices.size(); i++) { // add all vertices to graph
        this.graph.insertVertex(vertices.get(i));
      }
      
      // find edges and add to graph
      for (int a = 0; a < edges.length; a++) { // find edge weight associated with path between two vertices
        for (int b = 0; b < edges[a].length; b++) {
          if (!edges[a][b].equals((Number)Integer.MAX_VALUE)){
            this.graph.insertEdge(vertices.get(a), vertices.get(b), (Integer)edges[a][b]); // add to graph in both directions
            this.graph.insertEdge(vertices.get(b), vertices.get(a), (Integer)edges[a][b]);
          }
        }
      }
    } catch(FileNotFoundException e) {
      
    }
  }
  
  /**
   * A constructor defining the CMDijkstrasBackend object. This constructor DOES use
   * placeholder classes.
   * 
   * @param placeHolders a boolean
   */
  public CMDijkstrasBackend(boolean placeHolders) {
    this.current = new LinkedList<String>();
    this.graph = new Graph<>();
    this.mathLoader = new DataWranglerBD();
    this.points = 0;
  }

  /**
   * A method to check the user's submission against the actual shortest
   * path between the start and end nodes.
   * 
   * @param start - a string that corresponds with starting node
   * @param end - a string that corresponds with ending node
   * @return true if and only if the user's submission is correct; return false otherwise
   */
  @Override
  public boolean checkSubmission(String start, String end) {
    String[] actualArr = pathArr(start, end); // call helper method
    
    if (this.current.size() != actualArr.length) { // can't be the same if different lengths
      this.points -= 1;
      return false;
    }
      
    for (int i = 0; i < this.current.size(); i++) { // compare submission to actual solution
      if (!this.current.get(i).equals(actualArr[i])) {
        this.points -= 1;
        return false;
      }
    }
    
    this.points += 1;
    return true; // submission must be the correct solution
  }

  /**
   * A getter method that returns the user's points.
   * 
   * @return an int representing the user's points
   */
  @Override
  public int getPoints() {
    return this.points;
  }

  /**
   * A method to create a new puzzle with our nodes. The size of the returned
   * array is 2 with the first element indicating the starting node and second the ending node.
   * 
   * @return a string array that maps the new puzzle
   */
  @Override
  public String[] newPuzzle() {
    String[] toReturn = new String[2];
    LinkedList<String> allVertices;
    LinkedList<String> uniqueVertices = new LinkedList<String>();
    
    try {
      allVertices = this.mathLoader.loadVertices();
      for (int i = 0; i < allVertices.size(); i++) { // remove repeat vertices
        if (!uniqueVertices.contains(allVertices.get(i))) {
          uniqueVertices.add(allVertices.get(i));
        }
      }
      
    } catch (FileNotFoundException e) {
      // should never happen
    }
    
    int rand = (int)(Math.random() * uniqueVertices.size()); // random int in range [0, size)
    toReturn[0] = uniqueVertices.get(rand); // random starting node
    rand = (int)(Math.random() * uniqueVertices.size()); // random int in range [0, size)
    toReturn[1] = uniqueVertices.get(rand); // random ending node
    return toReturn;
  }

  /**
   * A method to add the user's clicked tile on the frontend to the linkedList
   * named Current.
   * 
   * @param next - a String representing the next tile clicked by the user
   * @return true if and only if the node to be added is connected to the last node 
   *         added, false otherwise
   */
  @Override
  public boolean addCurrent(String next) {
    if (this.current.isEmpty()) { // nothing for node to be connected to yet
      this.current.add(next);
      return true;
    }

    // verify that the tile clicked is next to the tile that was clicked before it
    String prev = this.current.get(this.current.size() - 1);
    try {
      pathArr(prev, next); // call helper method
      if (isAdjacent(prev, next)) { // check if prev and next are next to each other
        this.current.add(next);
        return true;
      }
      else {
        return false; // if there are nodes in between prev and next
      }
    } catch(Exception e) {
      return false; // Dijkstra should not cause any exceptions
    }
  }

  /**  @Override
   * A method to remove the user's clicked tile on the frontend from the linkedList
   * named Current.
   * 
   * @param next - a String representing the next tile clicked by the user
   * @return true if and only if the node to be removed was the last node added, false otherwise
   */
  @Override
  public boolean unclick(String next) {
    if (next.equals(this.current.get(this.current.size() - 1))) { // if removing last node added
      this.current.remove(this.current.size() - 1);
      return true; // the node to remove was the last node added
    }
    return false; // the node to remove was not the last node added
  }

  /**
   * A method to return a 2D array of the shortest path to all of the nodes
   * from the start node. Contains the end tile in first column and the path to it in
   * the second one.
   * 
   * @param start - a String that represent the starting node
   * @return a 2D array with shortest paths to all nodes
   */
  @Override
  public String[][] cheatSheet(String start) {
    LinkedList<String> allVertices;
    LinkedList<String> uniqueVertices = new LinkedList<String>();
    
    try {
      allVertices = this.mathLoader.loadVertices();
      for (int i = 0; i < allVertices.size(); i++) { // remove repeat vertices
        if (!uniqueVertices.contains(allVertices.get(i))) {
          uniqueVertices.add(allVertices.get(i));
        }
      }
      
    } catch (FileNotFoundException e) {
      // should never happen
    }
    
    String[][] toReturn = new String[uniqueVertices.size()][2];
    
    System.out.println();
    
    for (int i = 0; i < uniqueVertices.size(); i++) {
      String[] pathArr = pathArr(start, uniqueVertices.get(i));
      String pathStr = pathStr(pathArr);
      
      toReturn[i][0] = uniqueVertices.get(i);
      toReturn[i][1] = pathStr;
      
      System.out.println("Path to " + toReturn[i][0] + ": " + toReturn[i][1]);
    }
    
    return toReturn;
  }

  /**
   * A method to reset the current LinkedList containing the user's clicked
   * tiles (Nodes) representing a path.
   */
  @Override
  public void reset() {
    for (int i = this.current.size() - 1; i >= 0; i--) { 
      this.current.remove(i); // remove all values stored in current
    }
  }

  /**
   * A method to check the user's input path and point out the first
   * difference between it and the actual shortest path.
   *
   * @param start - a String representing the starting node
   * @param end - a String representing the ending node
   * @return a String representing the first difference found
   */
  @Override
  public String hint(String start, String end) {
    String[] actualArr = pathArr(start, end); // call helper method
    
    if (this.current.size() == actualArr.length) { // submission is already correct
    	int index = 0;
    	for(String s : current) {
    		if(s.equals(actualArr[index])) {
    			index++;
    		}else {
    			break;
    		}
    	}
    	if(current.size() == index) {
    		return "Submission is correct";
    	}
    }
    else if (this.current.size() == 0) {
    	return "The first node is " + actualArr[0];
    }
    else if (this.current.size() <= actualArr.length) {  
      int i = 0;
      for (i = 0; i < this.current.size(); i++) { // compare submission to actual solution
        if (!this.current.get(i).equals(actualArr[i])) {
          return "The first incorrect node is " + current.get(i);
        }
      }
      return "The next node is " + actualArr[i];
    }
    
    else if (this.current.size() > actualArr.length) {
      int i = 0;
      for (i = 0; i < actualArr.length; i++) { // compare submission to actual solution
        if (!this.current.get(i).equals(actualArr[i])) {
          return "The first incorrect node is " + current.get(i);
        }
      }
      return "The first incorrect node is " + current.get(i);
    }
    
    return ""; // never reached
  }

  /**
   * A method that will return the actual shortest path if the user gives up on
   * completing the game.
   * 
   * @param start - a String representing the starting node
   * @param end - a String representing the ending node
   * @return a LinkedList containing all of the nodes in the shortest path between the
   *         starting and ending nodes
   */
  @Override
  public LinkedList<String> giveUp(String start, String end) {
    String[] actualArr = pathArr(start, end); // call helper method
    LinkedList<String> actualLL = new LinkedList<String>();
    
    for (int i = 0; i < actualArr.length; i++) { // add every item in path to linked list
      actualLL.add(actualArr[i]);
    }

    return actualLL;
  }

  /**
   * A method that will receive a 2D String array from DW's gridMatrix() method and remove
   * all of the null values to make the 2D array usable for FD.
   * 
   * @return a 2D String array depicting a grid representation of the adjacency matrix
   *         without any null values.
   */  
  @Override
  public String[][] gridMatrixForFD() {
    String[][] forFD; // 2D String array to return
    String[][] fromDW; // 2D String array received from data wrangler
    String[] arrToAdd; // String array to track items to add to each row of forFD
    int numNulls; // int to track number of null values per row of fromDW
    try {
      fromDW = this.mathLoader.gridMatrix(); // get 2D String array from data wrangler
      forFD = new String[fromDW.length][]; // amount of columns per row will vary
      
      for (int a = 0; a < fromDW.length; a++) {
        numNulls = 0; // reset numNulls for each row of fromDW
        
        for (int b = 0; b < fromDW[a].length; b++) {

          if (fromDW[a][b] == null) { // check if each item in each row of fromDW is null
            numNulls += 1;
          }
        }
        
        arrToAdd = new String[fromDW[a].length - numNulls]; // number of columns per row in forFD
        int index = 0;
        for (int c = 0; c < fromDW[a].length; c++) {
        	
          if (fromDW[a][c] != null) { // add non-null values from fromDW to arrToAdd
        	  
            arrToAdd[index] = fromDW[a][c];
            index++;
          }
          
          //System.out.print(arrToAdd[index]+ " ");
          
          
        }
        
       
        forFD[a] = arrToAdd; // set array stored at forFD[a] to arrToAdd
        
        }
      
      for (String[] el: forFD) {
    	  System.out.println();
    	  for (String s: el) {
          System.out.print(s + " ");
    	  }
      }
    
      return forFD;
      
    } catch (FileNotFoundException e) {
      // should never happen
    }
    
    return null; // will never be reached
  }
  
  /**
   * A private helper method to convert the path between two nodes returned by the Dijkstra into a 
   * String array. Each element in the array contains the next node in the path.
   * 
   * @param start - the node to start at
   * @param end - the node to end at
   * @return pathArr - the String array representation of the path
   */
  private String[] pathArr(String start, String end) {
    String path = this.graph.shortestPath(start, end).toString(); // actual shortest path
    path = path.substring(1, path.length() - 1); // remove [ ] from around String
    String[] pathArr = path.split(", "); // makes a String array, each element represents a node
    
    return pathArr;
  }  
  
  /**
   * A private helper method to convert a String array returned by the pathArr() method into
   * a String representation of the path with nodes separated by commas.
   * 
   * @param pathArr - the String array to be converted into a String
   * @return toReturn - the String representation of a String array
   */
  private String pathStr(String[] pathArr) {
    String toReturn = "";
    for (int i = 0; i < pathArr.length; i++) {
      toReturn += pathArr[i] + ", ";
    }
    toReturn = toReturn.substring(0, toReturn.length() - 2); // remove ", " from end of String
    
    return toReturn;
  }
  
  /**
   * A private helper method to determine if two nodes in the game are adjacent to each other.
   */
  private boolean isAdjacent(String node1, String node2) {
    try {
    	System.out.println(graph.containsEdge(node1, node2));
      if (this.graph.containsEdge(node1, node2)) {
        return true;
      }
      return false;
    } catch (NullPointerException e) {
      return false;
    }
  }

  /**
   * A method that receives an MST and converts each edge included into a more readable String.
   * 
   * @param start - the starting node of the MST
   * @return an array of Strings with an edge specified at each element
   */
  public String[] getMST(String start) {
    List<String> returned = this.graph.getMST(start); // get all edges from AE
    String[] toReturn = new String[returned.size() / 2]; // array of edges to return
    
    int ind = 0;
    for (int i = 0; i < returned.size() - 1; i += 2) {
      String edgeStr = returned.get(i) + " -- " + returned.get(i + 1); // format edges into Strings
      toReturn[ind] = edgeStr; // add Strings to array to return
      ind += 1;
    }
      
    return toReturn;
  }
  
}
