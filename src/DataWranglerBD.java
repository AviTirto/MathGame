// --== CS400 Fall 2022 File Header Information ==--
// Name: Max Levy
// Email: mllevy3@wisc.edu
// Team: AQ
// TA: Minghao
// Lecturer: Gary Dahl
// Notes to Grader: NONE

import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * A PLACEHOLDER class that implements the methods defined in IMathLoader.java.
 */
public class DataWranglerBD implements IMathLoader {

  /**
   * This method loads a linked list of vertices from a dot file
   * @return a linked list of vertices
   * @throws FileNotFoundException
   */
  @Override
  public LinkedList<String> loadVertices() throws FileNotFoundException {
    LinkedList<String> toReturn = new LinkedList<String>();
    // random list of letters A-L
    toReturn.add("A");
    toReturn.add("B");
    toReturn.add("A");
    toReturn.add("E");
    toReturn.add("B");
    toReturn.add("F");
    toReturn.add("B");
    toReturn.add("C");
    toReturn.add("C");
    toReturn.add("D");
    toReturn.add("C");
    toReturn.add("I");
    toReturn.add("G");
    toReturn.add("G");
    toReturn.add("H");
    toReturn.add("J");
    toReturn.add("K");
    toReturn.add("K");
    toReturn.add("J");
    toReturn.add("L");
    return toReturn;
  }

  /**
   * This method loads a 2D array of numbers representing the edges of Node in the order
   * of the sequence in the linked list
   * @return a 2D number array representing the edges
   * @throws FileNotFoundException
   */
  @Override
  public Number[][] loadEdges() throws FileNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * returns a 2D array with the grid representation of adjacency matrix
   *
   * @return 2D array with the grid matrix for frontend applications
   * @throws FileNotFoundException
   */
  @Override
  public String[][] gridMatrix() throws FileNotFoundException {
    String[][] toReturn = new String[5][7];
    toReturn[0][0] = "A";
    toReturn[0][1] = "3";
    toReturn[0][2] = "B";
    toReturn[0][3] = "4";
    toReturn[0][4] = "C";
    toReturn[0][5] = "2";
    toReturn[0][6] = "D";
    toReturn[1][0] = "2";
    toReturn[1][1] = null;
    toReturn[1][2] = "6";
    toReturn[1][3] = null;
    toReturn[1][4] = "4";
    toReturn[1][5] = null;
    toReturn[1][6] = "7";
    toReturn[2][0] = "E";
    toReturn[2][1] = "7";
    toReturn[2][2] = "F";
    toReturn[2][3] = "1";
    toReturn[2][4] = "G";
    toReturn[2][5] = "4";
    toReturn[2][6] = "H";
    toReturn[3][0] = "5";
    toReturn[3][1] = null;
    toReturn[3][2] = "2";
    toReturn[3][3] = null;
    toReturn[3][4] = "9";
    toReturn[3][5] = null;
    toReturn[3][6] = "1";
    toReturn[4][0] = "I";
    toReturn[4][1] = "5";
    toReturn[4][2] = "J";
    toReturn[4][3] = "2";
    toReturn[4][4] = "K";
    toReturn[4][5] = "9";
    toReturn[4][6] = "L";
    
    /*
     * The above creates the following 2D array:
     * 
     * [A] [3] [B] [4] [C] [2] [D]
     * [2] [n] [6] [n] [4] [n] [7]
     * [E] [7] [F] [1] [G] [4] [H]
     * [5] [n] [2] [n] [9] [n] [1]
     * [I] [5] [J] [2] [K] [9] [L]
     * 
     * where [n] represents a null value
     */

    return toReturn;
  }

}
