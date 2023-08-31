// --== CS400 Fall 2022 File Header Information ==--
// Name: Kelly Weng
// Email: kweng3@wisc.edu
// Team: AQ
// TA: Minghao
// Lecturer: Gary
// Notes to Grader: NONE


import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * Instantiating this interface can load the nodes and edges from a dot file
 */
public interface IMathLoader {

  /**
   * This method loads a linked list of vertices from a dot file
   * @return a linked list of vertices
   * @throws FileNotFoundException
   */
  LinkedList<String> loadVertices() throws FileNotFoundException;

  /**
   * This method loads a 2D array of numbers representing the edges of Node in the order
   * of the sequence in the linked list
   * @return a 2D number array representing the edges
   * @throws FileNotFoundException
   */
  Number[][] loadEdges() throws FileNotFoundException;

  /**
   * returns a 2D array with the grid representation of adjacency matrix
   *
   * @return 2D array with the grid matrix for frontend applications
   * @throws FileNotFoundException
   */
  public String[][] gridMatrix() throws FileNotFoundException;

}
