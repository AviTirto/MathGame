import java.util.List;

/**
 * Interface to create an IGraph object that contains typical graph methods, as well as a method to
 * return an MST
 */
public interface IGraph<NodeType, EdgeType extends Number> extends GraphADT<NodeType, EdgeType> {

  /**
   * A method to return a Minimum Spanning Tree of this IGraph
   * 
   * @param start the graph node to start at
   * @return a list of all nodes in the order they are visited
   */
  public List<NodeType> getMST(NodeType start);

}
