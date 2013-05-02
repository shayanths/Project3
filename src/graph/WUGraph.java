package graph;
/* WUGraph.java */


import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	//vertices represented as an array and as a DList
	//the array is the hash table that the objects get hashed to

	HashTableChained verticesHashTable, edgesHashTable;
	DList vertexlist;
	int numVertices;
	int numEdges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  verticesHashTable = new HashTableChained();
	  edgesHashTable = new HashTableChained();
	  vertexlist = new DList();
	  numVertices = 0;
	  numEdges = 0;

  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	  return numVertices;
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	  return numEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  Object[] vertexArray = new Object[vertexlist.length()];
	  int element = 0;
	  DListNode node = (DListNode) vertexlist.front();
	  while (node.isValidNode()){
		  try{
			  vertexArray[element] = node.item();
			  node = (DListNode) node.next();
			  element += 1;
		  }catch(InvalidNodeException e1){
			  System.out.println("Invalid Node at " + e1);
		  }
	  }
	  return vertexArray;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  if (!isVertex(vertex)){
		  vertexlist.insertBack(vertex);
		  verticesHashTable.insert(vertex, new DList()).VertexList = (DListNode) vertexlist.back();
		  numVertices++;
	  }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  Entry e1 = verticesHashTable.find(vertex);
	  if (e1 == null){
		  return;
	  }
	  else{
		  DListNode iterateNode = (DListNode)((DList) e1.value()).front();
		  while (iterateNode.isValidNode()){
			  try{
				  if (((Entry)iterateNode.item()).e2 != iterateNode){
					  ((Entry) iterateNode.item()).e2.remove();
				  }
				  else if (((Entry)iterateNode.item()).e1 != iterateNode){
					  ((Entry) iterateNode.item()).e1.remove();
				  }
				  edgesHashTable.remove(((Entry)iterateNode.item()).key());
				  iterateNode = (DListNode) iterateNode.next();
			  } catch (InvalidNodeException e){
				  System.out.println("Remove Vertex Error at Node " + e);
			  }
		  }
		  numVertices  -= 1;
		  numEdges -= ((List)e1.value()).length();
		  try{
			  e1.VertexList.remove();
		  }catch(InvalidNodeException e){
			  System.out.println("Invalid Node Error at Remove Vertex at Node " + e);
		  }
		  verticesHashTable.remove(vertex);
	  }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  if (verticesHashTable.find(vertex) != null){
		  return true;
	  }
	  else{
		  return false;
	  }
	  
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	  Entry e1 = verticesHashTable.find(vertex);
	  if (e1 == null){
		  return 0;
	  }
	  else{
		  return ((List)e1.value()).length();
	  }
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  Entry e1 = verticesHashTable.find(vertex);
	  int element = 0;
	  if (e1 == null || ((List) e1.value()).length() == 0 ){
		  return null;
	  }
	  DListNode checker = (DListNode) ((DList) e1.value()).front();
	  Neighbors neighbors = new Neighbors();
	  neighbors.neighborList = new Object[((List)e1.value()).length()];
	  neighbors.weightList = new int[((List)e1.value()).length()];
	  while (checker.isValidNode()){
		  try{
			  if (((VertexPair)((Entry) checker.item()).key()).object1 != vertex){
				  neighbors.neighborList[element] = ((VertexPair) ((Entry) checker.item()).key()).object1;
			  }
			  else{
				  neighbors.neighborList[element] = ((VertexPair) ((Entry) checker.item()).key()).object2;
			  }
			  neighbors.weightList[element] = (Integer) ((Entry) checker.item()).value();
			  element += 1;
			  checker = (DListNode) checker.next();
			  
		  }catch(InvalidNodeException e){
			  System.out.println("Invalid Node at" + e + "in getNeighbors");
		  }
	  }
	  return neighbors;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  VertexPair vpair = new VertexPair(u,v);
	  Entry e1 = edgesHashTable.find(vpair);
	  if (e1 == null){
		 e1 = edgesHashTable.insert(vpair, weight);
		 numEdges += 1;
		 ((DList) verticesHashTable.find(u).value()).insertBack(e1);
		 if (u != v){
			 ((DList) verticesHashTable.find(v).value()).insertBack(e1);
		 }
		 e1.e1 = (DListNode) ((DList) verticesHashTable.find(u).value()).back();
		 e1.e2 = (DListNode) ((DList) verticesHashTable.find(v).value()).back();
	  }
	  else{
		  e1.weightchange(weight);
	  }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	  VertexPair vpair = new VertexPair(u,v);
	  Entry e1 = edgesHashTable.find(vpair);
	  if (e1 == null){
		  return;
	  }
	  else{
		  try{
			  if (e1.e1 != e1.e2){
				  e1.e1.remove();
			  }
			  e1.e2.remove();
			  edgesHashTable.remove(vpair);
			  numEdges -= 1;
			  
		  }catch (InvalidNodeException e){
			  System.out.println("Invalid Node" + e + "in Remove Edge");
		  }
	  }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	  VertexPair vpair = new VertexPair(u,v);
	  Entry e1 = edgesHashTable.find(vpair);
	  if (e1 == null){
		  return false;
	  }
	  else{
		  return true;
	  }
	  
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
	  VertexPair vpair = new VertexPair(u,v);
	  Entry e1 = edgesHashTable.find(vpair);
	  if (e1 == null){
		  return 0;
	  }
	  else{
		  return (Integer) e1.value();
	  }
  }
}
