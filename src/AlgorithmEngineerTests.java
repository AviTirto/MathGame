// --== CS400 Fall 2022 File Header Information ==--
// Name: <Neil Patel>
// Email: <njpatel7@wisc.edu>
// Team: <AQ>
// TA: <MINGHAO YAN>
// Lecturer: <Gary Dahl>
// Notes to Grader: <N/A>
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm.
 */
public class AlgorithmEngineerTests {

    private Graph<String,Integer> graph;
    
    /**
     * Instantiate graph.
     */
    @BeforeEach
    public void createGraph() {
        graph = new Graph<>();
        // insert vertices A-F
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        // insert edges
        graph.insertEdge("A","B",6);
        graph.insertEdge("A","C",2);
        graph.insertEdge("A","D",5);
        graph.insertEdge("B","E",1);
        graph.insertEdge("B","C",2);
        graph.insertEdge("C","B",3);
        graph.insertEdge("C","F",1);
        graph.insertEdge("D","E",3);
        graph.insertEdge("E","A",4);
        graph.insertEdge("F","A",1);
        graph.insertEdge("F","D",1);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to F.
     */
    @Test
    public void testPathCostAtoF() {
        assertTrue(graph.getPathCost("A", "F") == 3);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to D.
     */
    @Test
    public void testPathCostAtoD() {
        assertTrue(graph.getPathCost("A", "D") == 4);
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * A to D.
     */
    @Test
    public void testPathAtoD() {
        assertTrue(graph.shortestPath("A", "D").toString().equals(
            "[A, C, F, D]"
        ));
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * A to F.
     */
    @Test
    public void testPathAtoF() {
        assertTrue(graph.shortestPath("A", "F").toString().equals(
            "[A, C, F]"
        ));
    }
    
    /**
     * Checks the distance/total weight cost from the vertex A to D.
     */
    @Test
    public void testPathCostAtoE() {
      assertTrue(graph.getPathCost("A", "E") == 6);
    }
    
    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * A to E.
     */
    @Test
    public void testPathAtoE() {
      assertTrue(graph.shortestPath("A", "E").toString().equals(
          "[A, C, B, E]"));
    }
    /**
     * Checks the distance/total weight cost from the vertex B to F.
     */
    @Test
    public void testPathCostBtoF() {
      assertTrue(graph.getPathCost("B", "F") == 3);
    }
    
    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * B to F.
     */
    @Test
    public void testPathBtoF() {
      assertTrue(graph.shortestPath("B", "F").toString().equals(
          "[B, C, F]"));
    }
    
    /**
     * Checks the distance/total weight cost from the vertex E to D.
     */
    @Test
    public void testPathCostEtoD() {
      assertTrue(graph.getPathCost("E", "D") == 8);
    }
    
    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * E to D.
     */
    @Test
    public void testPathEtoD() {
      assertTrue(graph.shortestPath("E", "D").toString().equals(
          "[E, A, C, F, D]"));
    }
    
    /**
     * Tests the get minimum spanning tree method in the graph.
     * this method returns nodes in a list, the list is such that every two nodes form a 
     * source and target. EX. list[0] = source, list[1] = target, list[2] = source, list[3] = target...
     */
    @Test
    public void testMST() {
      
      Graph<String,Integer> graph;

      graph = new Graph<>();
      // insert vertices A-F
      graph.insertVertex("A");
      graph.insertVertex("B");
      graph.insertVertex("C");
      graph.insertVertex("D");
      graph.insertVertex("E");
      graph.insertVertex("F");
      graph.insertVertex("G");
      graph.insertVertex("H");
      // insert edges
      graph.insertEdge("H","A",1);
      graph.insertEdge("A","H",1);
      graph.insertEdge("A","C",6);
      graph.insertEdge("C","A",6);
      graph.insertEdge("A","F",7);
      graph.insertEdge("F","A",7);
      graph.insertEdge("C","B",3);
      graph.insertEdge("B","C",3);
      graph.insertEdge("C","G",5);
      graph.insertEdge("G","C",5);
      graph.insertEdge("B","D",2);
      graph.insertEdge("D","B",2);
      graph.insertEdge("E","D",2);
      graph.insertEdge("D","E",2);
      graph.insertEdge("D","F",3);
      graph.insertEdge("F","D",3);
      graph.insertEdge("E","F",1);
      graph.insertEdge("F","E",1);
      graph.insertEdge("E","G",4);
      graph.insertEdge("G","E",4);
      
      List<String> mst= graph.getMST("HI");
     
      assertTrue(mst.get(0).equals("A"));
      assertTrue(mst.get(1).equals("H"));
      assertTrue(mst.get(2).equals("F"));
      assertTrue(mst.get(3).equals("E"));
      assertTrue(mst.get(4).equals("D"));
      assertTrue(mst.get(5).equals("B"));
      assertTrue(mst.get(6).equals("E"));
      assertTrue(mst.get(7).equals("D"));
      assertTrue(mst.get(8).equals("C"));
      assertTrue(mst.get(9).equals("B"));
      assertTrue(mst.get(10).equals("G"));
      assertTrue(mst.get(11).equals("E"));
      assertTrue(mst.get(12).equals("A"));
      assertTrue(mst.get(13).equals("C"));



      
    }
    
    @Test
    public void getPathCtoE() {

      assertTrue(graph.shortestPath("C", "E").toString().equals("[C, B, E]"));
      
    }
    
}
