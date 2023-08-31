// --== CS400 Fall 2022 File Header Information ==--
// Name: Max Levy
// Email: mllevy3@wisc.edu
// Team: AQ
// TA: Minghao
// Lecturer: Gary Dahl
// Notes to Grader: NONE


import java.util.LinkedList;

public interface ICMDijkstrasBackend {

    //int points;  -- The user's points
    //LinkedList<String> current; -- This LinkedList holds the user's suggested path
    
    /**
     * A method to check the user's submission against the actual shortest
     * path between the start and end nodes.
     * 
     * @param start - a string that corresponds with starting node
     * @param end - a string that corresponds with ending node
     * @return true if and only if the user's submission is correct; return false otherwise
     */
    public boolean checkSubmission(String start, String end);

    /**
     * A getter method that returns the user's points.
     * 
     * @return an int representing the user's points
     */
    public int getPoints();

    /**
     * A method to create a new puzzle with our nodes. The size of the returned
     * array is 2 with the first element indicating the starting node and second the ending node.
     * 
     * @return a string array that maps the new puzzle
     */
    public String[] newPuzzle();

    /**
     * A method to return a 2D array of the shortest path to all of the nodes
     * from the start node. Contains the end tile in first column and the path to it in
     * the second one.
     *
     * @param start - a String that represent the starting node
     * @return a 2D array with shortest paths to all nodes
     */
    public String[][] cheatSheet(String start);
    
    /**
     * A method to add the user's clicked tile on the frontend to the linkeAdList
     * named Current.
     * 
     * @param next - a String representing the next tile clicked by the user
     */
    public boolean addCurrent(String next);

    /**
     * A method to reset the current LinkedList containing the user's clicked
     * tiles (Nodes)- representing a path.
     */
    public void reset();

    /**
     * A method that will return the actual shortest path if the user gives up on
     * completing the game.
     * 
     * @param start - a String representing the starting node
     * @param end - a String representing the ending node
     * @return a LinkedList containing all of the nodes in the shortest path between the
     *         starting and ending nodes
     */
    public LinkedList<String> giveUp(String start, String end);
    
    /**
     * A method to check the user's input path and point out the first
     * difference between it and the actual shortest path.
     *
     * @param start - a String representing the starting node
     * @param end - a String representing the ending node
     * @return - A String representing the first difference found
     */
    public String hint(String start, String end);
    public boolean unclick(String vertex);
    public String[][] gridMatrixForFD();
}
