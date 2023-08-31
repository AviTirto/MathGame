// --== CS400 Fall 2022 File Header Information ==--
// Name: Max Levy
// Email: mllevy3@wisc.edu
// Team: AQ
// TA: Minghao
// Lecturer: Gary Dahl
// Notes to Grader: NONE

import org.junit.jupiter.api.Test; // for junit5 tests
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.LinkedList;

/**
 * A class to test the methods defined in CMDijkstrasBackend.java.
 */
public class BackendDeveloperTests {

  /**
   * Test functionality of CMDijkstrasBackend.java's checkSubmission() and getPoints() methods.
   */
  @Test
  public void testCase1() {
    boolean result = true;

    // create CMDijkstrasBackend object
    CMDijkstrasBackend backend = new CMDijkstrasBackend(true);

    // create graph
    // insert vertices A-F
    backend.graph.insertVertex("A");
    backend.graph.insertVertex("B");
    backend.graph.insertVertex("C");
    backend.graph.insertVertex("D");
    backend.graph.insertVertex("E");
    backend.graph.insertVertex("F");
    // insert edges
    backend.graph.insertEdge("A", "B", 6);
    backend.graph.insertEdge("A", "C", 2);
    backend.graph.insertEdge("A", "D", 5);
    backend.graph.insertEdge("B", "E", 1);
    backend.graph.insertEdge("B", "C", 2);
    backend.graph.insertEdge("C", "B", 3);
    backend.graph.insertEdge("C", "F", 1);
    backend.graph.insertEdge("D", "E", 3);
    backend.graph.insertEdge("E", "A", 4);
    backend.graph.insertEdge("F", "A", 1);
    backend.graph.insertEdge("F", "D", 1);
    
    // test path that is incorrect length
    backend.addCurrent("A");
    backend.addCurrent("B");
    backend.addCurrent("E");
    
    if (backend.checkSubmission("D", "B")) { // path should be D -> E -> A -> C -> B
      result = false; // current path is the incorrect length, so method should return false
    }
    
    if (backend.getPoints() != 0) { // no points awarded for incorrect submission
      result = false;
    }
    
    // test path that is correct
    backend.reset();
    backend.addCurrent("D");
    backend.addCurrent("E");
    backend.addCurrent("A");
    backend.addCurrent("C");
    backend.addCurrent("B");
    
    if (!backend.checkSubmission("D", "B")) { // path should be D -> E -> A -> C -> B
      result = false; // current path is correct, so method should return true
    }
    
    if (backend.getPoints() != 1) { // one point awarded for correct submission
      result = false;
    }
    
    // test path that is correct length but incorrect path
    backend.reset();
    backend.addCurrent("C");
    backend.addCurrent("F");
    backend.addCurrent("D");
    backend.addCurrent("E");
    backend.addCurrent("A");
    
    if (backend.checkSubmission("D", "B")) { // path should be D -> E -> A -> C -> B
      result = false; // current path is incorrect, so method should return false
    }
    
    if (backend.getPoints() != 0) { // points reset after incorrect submission
      result = false;
    }

    assertEquals(result, true);
  }

  /**
   * Test functionality of CMDijkstrasBackend.java's newPuzzle() method.
   */
  @Test
  public void testCase2() {
    boolean result = true;

    // create CMDijkstrasBackend object
    CMDijkstrasBackend backend = new CMDijkstrasBackend(true);

    // call newPuzzle() method
    String[] returned = backend.newPuzzle();
    
    // check length of returned array
    if (returned.length != 2) {
      result = false;
    }
    
    String[] letters = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    
    // check if first element in array is valid (A-L)
    boolean match0 = false;
    for (int i = 0; i < letters.length; i++) {
      if (returned[0].equals(letters[i])) {
        match0 = true;
      }
    }
    
    if (!match0) {
      result = false;
    }
    
    // check if second element in array is valid (A-L)
    boolean match1 = false;
    for (int i = 0; i < letters.length; i++) {
      if (returned[0].equals(letters[i])) {
        match1 = true;
      }
    }
    
    if (!match1) {
      result = false;
    }

    assertEquals(result, true);
  }

  /**
   * Test functionality of CMDijkstrasBackend.java's addCurrent() and unclick() methods.
   */
  @Test
  public void testCase3() {
    boolean result = true;

    // create CMDijkstrasBackend object
    CMDijkstrasBackend backend = new CMDijkstrasBackend(true);
    
    // create graph
    // insert vertices A-F
    backend.graph.insertVertex("A");
    backend.graph.insertVertex("B");
    backend.graph.insertVertex("C");
    backend.graph.insertVertex("D");
    backend.graph.insertVertex("E");
    backend.graph.insertVertex("F");
    // insert edges
    backend.graph.insertEdge("A", "B", 6);
    backend.graph.insertEdge("A", "C", 2);
    backend.graph.insertEdge("A", "D", 5);
    backend.graph.insertEdge("B", "E", 1);
    backend.graph.insertEdge("B", "C", 2);
    backend.graph.insertEdge("C", "B", 3);
    backend.graph.insertEdge("C", "F", 1);
    backend.graph.insertEdge("D", "E", 3);
    backend.graph.insertEdge("E", "A", 4);
    backend.graph.insertEdge("F", "A", 1);
    backend.graph.insertEdge("F", "D", 1);
    
    backend.addCurrent("A");
    
    // test adding node that IS NOT adjacent to A
    if (backend.addCurrent("L")) { // method should return false because node is invalid
      result = false;
    }
    
    // test adding node that IS adjacent to A
    if (!backend.addCurrent("B")) { // method should return true because node is valid
      result = false;
    }
    
  // test removing node that WAS NOT the last node added
  if (backend.unclick("A")) { // method should return false because A was not last node added
    result = false;
  }
  
  // test removing node that WAS the last node added
  if (!backend.unclick("B")) { // method should return true because A was last node added
    result = false;
  }
    
    assertEquals(result, true);
  }

  /**
   * Test functionality of CMDijkstrasBackend.java's giveUp() method.
   */
  @Test
  public void testCase4() {
    boolean result = true;

    // create CMDijkstrasBackend object
    CMDijkstrasBackend backend = new CMDijkstrasBackend(true);

    // create graph
    // insert vertices A-F
    backend.graph.insertVertex("A");
    backend.graph.insertVertex("B");
    backend.graph.insertVertex("C");
    backend.graph.insertVertex("D");
    backend.graph.insertVertex("E");
    backend.graph.insertVertex("F");
    // insert edges
    backend.graph.insertEdge("A", "B", 6);
    backend.graph.insertEdge("A", "C", 2);
    backend.graph.insertEdge("A", "D", 5);
    backend.graph.insertEdge("B", "E", 1);
    backend.graph.insertEdge("B", "C", 2);
    backend.graph.insertEdge("C", "B", 3);
    backend.graph.insertEdge("C", "F", 1);
    backend.graph.insertEdge("D", "E", 3);
    backend.graph.insertEdge("E", "A", 4);
    backend.graph.insertEdge("F", "A", 1);
    backend.graph.insertEdge("F", "D", 1);
    
    LinkedList returned = backend.giveUp("D", "B");
    
    // test each element of the returned LinkedList
    if (!returned.get(0).equals("D")) {
      result = false;
    }
    
    if (!returned.get(1).equals("E")) {
      result = false;
    }
    
    if (!returned.get(2).equals("A")) {
      result = false;
    }
    
    if (!returned.get(3).equals("C")) {
      result = false;
    }
    
    if (!returned.get(4).equals("B")) {
      result = false;
    }

    assertEquals(result, true);
  }

  @Test
  public void testCase5() {
    boolean result = true;

    // create CMDijkstrasBackend object
    CMDijkstrasBackend backend = new CMDijkstrasBackend(true);

    String[][] returned = backend.gridMatrixForFD();
    
    // check length of each line of 2D array returned by method
    if (returned[0].length != 7) {
      result = false;
    }
    
    if (returned[1].length != 4) {
      result = false;
    }
    
    if (returned[2].length != 7) {
      result = false;
    }
    
    if (returned[3].length != 4) {
      result = false;
    }
    
    if (returned[4].length != 7) {
      result = false;
    }

    assertEquals(result, true);
  }
}
