// --== CS400 Fall 2022 File Header Information ==--
// Name: <Neil Patel>
// Email: <njpatel7@wisc.edu>
// Team: <AQ>
// TA: <MINGHAO YAN>
// Lecturer: <Gary Dahl>
// Notes to Grader: <N/A>

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.NoSuchElementException;

public class Graph<NodeType, EdgeType extends Number> implements IGraph<NodeType, EdgeType> {

  /**
   * Vertex objects group a data field with an adjacency list of weighted directed edges that lead
   * away from them.
   */
  protected class Vertex {
    public NodeType data; // vertex label or application specific data
    public LinkedList<Edge> edgesLeaving;

    public Vertex(NodeType data) {
      this.data = data;
      this.edgesLeaving = new LinkedList<>();
    }
  }

  /**
   * Edge objects are stored within their source vertex, and group together their target destination
   * vertex, along with an integer weight.
   */
  protected class Edge {
    public Vertex target;
    public EdgeType weight;
    public NodeType source;

    public Edge(Vertex target, EdgeType weight) {
      this.target = target;
      this.weight = weight;
    }
  }

  protected Hashtable<NodeType, Vertex> vertices; // holds graph verticies, key=data
  protected int numVertices;
  public Graph() {
    vertices = new Hashtable<>();
  }


  /**
   * Insert a new vertex into the graph.
   * 
   * @param data the data item stored in the new vertex
   * @return true if the data can be inserted as a new vertex, false if it is already in the graph
   * @throws NullPointerException if data is null
   */
  public boolean insertVertex(NodeType data) {
    if (data == null)
      throw new NullPointerException("Cannot add null vertex");
    if (vertices.containsKey(data))
      return false; // duplicate values are not allowed
    vertices.put(data, new Vertex(data));
    return true;
  }

  /**
   * Remove a vertex from the graph. Also removes all edges adjacent to the vertex from the graph
   * (all edges that have the vertex as a source or a destination vertex).
   * 
   * @param data the data item stored in the vertex to remove
   * @return true if a vertex with *data* has been removed, false if it was not in the graph
   * @throws NullPointerException if data is null
   */
  public boolean removeVertex(NodeType data) {
    if (data == null)
      throw new NullPointerException("Cannot remove null vertex");
    Vertex removeVertex = vertices.get(data);
    if (removeVertex == null)
      return false; // vertex not found within graph
    // search all vertices for edges targeting removeVertex
    for (Vertex v : vertices.values()) {
      Edge removeEdge = null;
      for (Edge e : v.edgesLeaving)
        if (e.target == removeVertex)
          removeEdge = e;
      // and remove any such edges that are found
      if (removeEdge != null)
        v.edgesLeaving.remove(removeEdge);
    }
    // finally remove the vertex and all edges contained within it
    return vertices.remove(data) != null;
  }

  /**
   * Insert a new directed edge with a positive edge weight into the graph.
   * 
   * @param source the data item contained in the source vertex for the edge
   * @param target the data item contained in the target vertex for the edge
   * @param weight the weight for the edge (has to be a positive integer)
   * @return true if the edge could be inserted or its weight updated, false if the edge with the
   *         same weight was already in the graph
   * @throws IllegalArgumentException if either source or target or both are not in the graph, or if
   *                                  its weight is < 0
   * @throws NullPointerException     if either source or target or both are null
   */
  public boolean insertEdge(NodeType source, NodeType target, EdgeType weight) {
    if (source == null || target == null)
      throw new NullPointerException("Cannot add edge with null source or target");
    Vertex sourceVertex = this.vertices.get(source);
    Vertex targetVertex = this.vertices.get(target);
    if (sourceVertex == null || targetVertex == null)
      throw new IllegalArgumentException("Cannot add edge with vertices that do not exist");
    if (weight.doubleValue() < 0)
      throw new IllegalArgumentException("Cannot add edge with negative weight");
    // handle cases where edge already exists between these verticies
    for (Edge e : sourceVertex.edgesLeaving)
      if (e.target == targetVertex) {
        if (e.weight.doubleValue() == weight.doubleValue())
          return false; // edge already exists
        else
          e.weight = weight; // otherwise update weight of existing edge
        return true;
      }

    // otherwise add new edge to sourceVertex
    Edge edgeToAdd = new Edge(targetVertex, weight);
    edgeToAdd.source = source;
    sourceVertex.edgesLeaving.add(edgeToAdd);
    return true;
  }

  /**
   * Remove an edge from the graph.
   * 
   * @param source the data item contained in the source vertex for the edge
   * @param target the data item contained in the target vertex for the edge
   * @return true if the edge could be removed, false if it was not in the graph
   * @throws IllegalArgumentException if either source or target or both are not in the graph
   * @throws NullPointerException     if either source or target or both are null
   */
  public boolean removeEdge(NodeType source, NodeType target) {
    if (source == null || target == null)
      throw new NullPointerException("Cannot remove edge with null source or target");
    Vertex sourceVertex = this.vertices.get(source);
    Vertex targetVertex = this.vertices.get(target);
    if (sourceVertex == null || targetVertex == null)
      throw new IllegalArgumentException("Cannot remove edge with vertices that do not exist");
    // find edge to remove
    Edge removeEdge = null;
    for (Edge e : sourceVertex.edgesLeaving)
      if (e.target == targetVertex)
        removeEdge = e;
    if (removeEdge != null) { // remove edge that is successfully found
      sourceVertex.edgesLeaving.remove(removeEdge);
      return true;
    }
    return false; // otherwise return false to indicate failure to find
  }

  /**
   * Check if the graph contains a vertex with data item *data*.
   * 
   * @param data the data item to check for
   * @return true if data item is stored in a vertex of the graph, false otherwise
   * @throws NullPointerException if *data* is null
   */
  public boolean containsVertex(NodeType data) {
    if (data == null)
      throw new NullPointerException("Cannot contain null data vertex");
    return vertices.containsKey(data);
  }

  /**
   * Check if edge is in the graph.
   * 
   * @param source the data item contained in the source vertex for the edge
   * @param target the data item contained in the target vertex for the edge
   * @return true if the edge is in the graph, false if it is not in the graph
   * @throws NullPointerException if either source or target or both are null
   */
  public boolean containsEdge(NodeType source, NodeType target) {
    if (source == null || target == null)
      throw new NullPointerException("Cannot contain edge adjacent to null data");
    Vertex sourceVertex = vertices.get(source);
    Vertex targetVertex = vertices.get(target);
    if (sourceVertex == null)
      return false;
    for (Edge e : sourceVertex.edgesLeaving)
      if (e.target == targetVertex)
        return true;
    return false;
  }

  /**
   * Return the weight of an edge.
   * 
   * @param source the data item contained in the source vertex for the edge
   * @param target the data item contained in the target vertex for the edge
   * @return the weight of the edge (a Number that represents 0 or a positive value)
   * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the
   *                                  graph
   * @throws NullPointerException     if either sourceVertex or targetVertex or both are null
   * @throws NoSuchElementException   if edge is not in the graph
   */
  public EdgeType getWeight(NodeType source, NodeType target) {
    if (source == null || target == null)
      throw new NullPointerException("Cannot contain weighted edge adjacent to null data");
    Vertex sourceVertex = vertices.get(source);
    Vertex targetVertex = vertices.get(target);
    if (sourceVertex == null || targetVertex == null)
      throw new IllegalArgumentException(
          "Cannot retrieve weight of edge between vertices that do not exist");
    for (Edge e : sourceVertex.edgesLeaving)
      if (e.target == targetVertex)
        return e.weight;
    throw new NoSuchElementException("No directed edge found between these vertices");
  }

  /**
   * Return the number of edges in the graph.
   * 
   * @return the number of edges in the graph
   */
  public int getEdgeCount() {
    int edgeCount = 0;
    for (Vertex v : vertices.values())
      edgeCount += v.edgesLeaving.size();
    return edgeCount;
  }

  /**
   * Return the number of vertices in the graph
   * 
   * @return the number of vertices in the graph
   */
  public int getVertexCount() {
    return vertices.size();
  }

  /**
   * Check if the graph is empty (does not contain any vertices or edges).
   * 
   * @return true if the graph does not contain any vertices or edges, false otherwise
   */
  public boolean isEmpty() {
    return vertices.size() == 0;
  }

  /**
   * Path objects store a discovered path of vertices and the overal distance of cost of the
   * weighted directed edges along this path. Path objects can be copied and extended to include new
   * edges and verticies using the extend constructor. In comparison to a predecessor table which is
   * sometimes used to implement Dijkstra's algorithm, this eliminates the need for tracing paths
   * backwards from the destination vertex to the starting vertex at the end of the algorithm.
   */
  protected class Path implements Comparable<Path> {
    public Vertex start; // first vertex within path
    public double distance; // sumed weight of all edges in path
    public List<NodeType> dataSequence; // ordered sequence of data from vertices in path
    public Vertex end; // last vertex within path

    /**
     * Creates a new path containing a single vertex. Since this vertex is both the start and end of
     * the path, it's initial distance is zero.
     * 
     * @param start is the first vertex on this path
     */
    public Path(Vertex start) {
      this.start = start;
      this.distance = 0.0D;
      this.dataSequence = new LinkedList<>();
      this.dataSequence.add(start.data);
      this.end = start;
    }

    /**
     * This extension constructor makes a copy of the path passed into it as an argument without
     * affecting the original path object (copyPath). The path is then extended by the Edge object
     * extendBy. Use the doubleValue() method of extendBy's weight field to get a double
     * representation of the edge's weight.
     * 
     * @param copyPath is the path that is being copied
     * @param extendBy is the edge the copied path is extended by
     */
    public Path(Path copyPath, Edge extendBy) {
      // creating a deep copy of the Path to copy
      this.start = copyPath.start;
      this.dataSequence = new LinkedList<>();
      boolean targetVertexFound = false;
      for (NodeType vertexData : copyPath.dataSequence) {
        // checking to see if target vertex of edge to add is already in dataSequence
        if (vertexData.equals(extendBy.target.data))
          targetVertexFound = true;
        this.dataSequence.add(vertexData);
      }
      // adding edge to extend the path
      this.distance = copyPath.distance + extendBy.weight.doubleValue();
      this.end = extendBy.target;
      if (targetVertexFound == false)
        dataSequence.add(extendBy.target.data);


    }

    /**
     * Allows the natural ordering of paths to be increasing with path distance. When path distance
     * is equal, the string comparison of end vertex data is used to break ties.
     * 
     * @param other is the other path that is being compared to this one
     * @return -1 when this path has a smaller distance than the other, +1 when this path has a
     *         larger distance that the other, and the comparison of end vertex data in string form
     *         when these distances are tied
     */
    public int compareTo(Path other) {
      int cmp = Double.compare(this.distance, other.distance);
      if (cmp != 0)
        return cmp; // use path distance as the natural ordering
      // when path distances are equal, break ties by comparing the string
      // representation of data in the end vertex of each path
      return this.end.data.toString().compareTo(other.end.data.toString());
    }
  }

  /**
   * Uses Dijkstra's shortest path algorithm to find and return the shortest path between two
   * vertices in this graph: start and end. This path contains an ordered list of the data within
   * each node on this path, and also the distance or cost of all edges that are a part of this
   * path.
   * 
   * @param start data item within first node in path
   * @param end   data item within last node in path
   * @return the shortest path from start to end, as computed by Dijkstra's algorithm
   * @throws NoSuchElementException when no path from start to end can be found, including when no
   *                                vertex containing start or end can be found
   */
  protected Path dijkstrasShortestPath(NodeType start, NodeType end) {
    if (!this.containsVertex(start) || !this.containsVertex(end))
      throw new NoSuchElementException(
          "Cannot find path when the given start or end vertices are not on the graph");
    Vertex startVertex = vertices.get(start);
    // creating initial path
    Path initialPath = new Path(startVertex);
    PriorityQueue<Path> pq = new PriorityQueue<>();
    
    if (start.equals(end)) {
    	return initialPath;
    }

    // adding initial paths to priority queue from start vertex
    for (int i = 0; i < startVertex.edgesLeaving.size(); i++) {
      pq.add(new Path(initialPath, startVertex.edgesLeaving.get(i)));
    }


    while (pq.size() != 0) {
      // getting path to visit
      Path shortestPath = pq.remove();
      // checking if shortest path we are looking for is found
      if (shortestPath.end.data.equals(end))
        return shortestPath;
      LinkedList<Edge> edgesLeaving = vertices.get(shortestPath.end.data).edgesLeaving;
      // adding new possible paths to priority queue
      for (int i = 0; i < edgesLeaving.size(); i++) {
        // checking if proposed path is to a node in which we already have found the shortest path
        if (shortestPath.dataSequence.contains(edgesLeaving.get(i).target.data))
          continue;
        pq.add(new Path(shortestPath, edgesLeaving.get(i)));
      }

    }

    // Path has not been found
    throw new NoSuchElementException("Path has not been found for given start and end vertices");

  }

  /**
   * Returns the shortest path between start and end. Uses Dijkstra's shortest path algorithm to
   * find the shortest path.
   * 
   * @param start the data item in the starting vertex for the path
   * @param end   the data item in the destination vertex for the path
   * @return list of data item in vertices in order on the shortest path between vertex with data
   *         item start and vertex with data item end, including both start and end
   * @throws NoSuchElementException when no path from start to end can be found including when no
   *                                vertex containing start or end can be found
   */
  public List<NodeType> shortestPath(NodeType start, NodeType end) {
    return dijkstrasShortestPath(start, end).dataSequence;
  }

  /**
   * Returns the cost of the path (sum over edge weights) between start and end. Uses Dijkstra's
   * shortest path algorithm to find the shortest path.
   * 
   * @param start the data item in the starting vertex for the path
   * @param end   the data item in the end vertex for the path
   * @return the cost of the shortest path between vertex with data item start and vertex with data
   *         item end, including all edges between start and end
   * @throws NoSuchElementException when no path from start to end can be found including when no
   *                                vertex containing start or end can be found
   */
  public double getPathCost(NodeType start, NodeType end) {
    return dijkstrasShortestPath(start, end).distance;
  }

  public List<NodeType> getMST(NodeType start) {
    // holds every single edge
    ArrayList<Edge> allEdges = new ArrayList<Edge>();

    // set of all keys to vertices
    Set<NodeType> keys = vertices.keySet();
    // populating ArrayList of all edges
    int counter = 0;
    //Hashing all vertices to integers
    Hashtable<NodeType, Integer> VerticeMapping
    = new Hashtable<NodeType, Integer>();
    for (NodeType key : keys) {
      LinkedList<Edge> edges = vertices.get(key).edgesLeaving;
      for (int j = 0; j < edges.size(); j++) {
        allEdges.add(edges.get(j));
      }
      VerticeMapping.put(key, counter);
      counter++;
    }

    numVertices = VerticeMapping.size();
    
    

    // sorting edges in ascending order
    allEdges.sort(new edgeComparator());
    //Removing Duplicates
    for(int i = 0; i < allEdges.size()-1; i++) {
      Edge e = allEdges.get(i);
      for(int j = 0; j < allEdges.size(); j++) {
        if(allEdges.get(j).target.data.equals(e.source) && allEdges.get(j).source.equals(e.target.data)) {
          allEdges.remove(j);
          break;
        }
      }

    }
    ArrayList<Edge> MSTEdgeList = new ArrayList<Edge>();

    
    
    PriorityQueue<Edge> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingInt(o -> o.weight.intValue()));
    for (int i = 0; i <allEdges.size() ; i++) {
      pq.add(allEdges.get(i));
      }
    //create a vertices array []
    int [] parent = new int[this.numVertices];
    //make a set of all vertices that point to themselves
    makeSet(parent);
    
    
    int index = 0;
    while(index < numVertices-1) {
      Edge edge = pq.remove();
      //checking if adding this edge makes a cycle
      int x_set = find(parent, VerticeMapping.get(edge.source));
      int y_set = find(parent, VerticeMapping.get(edge.target.data));
      //this is a loop so nothing happens
      if(x_set == y_set) {
      }else {
        //path between two vertices found for MST, adding
        MSTEdgeList.add(edge);
        index++;
        //merging the two sets of vertices
        union(parent, x_set, y_set);
      }
    }
    


    //populating list of nodes in MST to return
    List<NodeType> mstList = new ArrayList<NodeType>();
    for (int i = 0; i < MSTEdgeList.size(); i++) {
      // System.out.println(MSTEdgeList.get(i).source + " -> " + MSTEdgeList.get(i).target.data +
      // "\n");
      mstList.add(MSTEdgeList.get(i).source);
      mstList.add(MSTEdgeList.get(i).target.data);
    }


    return mstList;
  }
  /**
   * Private helper to make a set
   * @param parent
   */
  private void makeSet(int [] parent){
    //Make set- creating a new element with a parent pointer to itself.
    for (int i = 0; i <numVertices ; i++) {
    parent[i] = i;
    }
    }
  /**
   * Private helper to union two sets
   * @param parent array of vertices
   * @param x source node
   * @param y target node
   */
  private void union(int [] parent, int x, int y){
    int x_set_parent = find(parent, x);
    int y_set_parent = find(parent, y);
    //make x as parent of y
    parent[y_set_parent] = x_set_parent;
    }
  
  /**
   * Private helper to find a vertex
   * @param parent array of vertices
   * @param vertex vertex to find
   * @return found vertex
   */
  private int find(int [] parent, int vertex){
    //chain of parent pointers from x upwards through the tree
    // until an element is reached whose parent is itself
    if(parent[vertex]!=vertex)
    return find(parent, parent[vertex]);;
    return vertex;
    }

  /**
   * Comparator for edges by weight, used to help sort edges by their weight by lowest to greatest
   *
   */
  private class edgeComparator implements Comparator<Edge> {
    /**
     * Compares edges by their double value of weight
     */
    @Override
    public int compare(Graph<NodeType, EdgeType>.Edge o1, Graph<NodeType, EdgeType>.Edge o2) {
      if (o1.weight.doubleValue() > o2.weight.doubleValue())
        return 1;
      if (o1.weight.doubleValue() == o2.weight.doubleValue())
        return 0;
      else
        return -1;

    }

  }
  




}
