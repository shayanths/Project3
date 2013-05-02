
// This will be a similar representation to VPair, but with the use of edges instead of the VertexPair
public class Edge {
	protected Object object1;
	protected Object object2;
	protected int weight;
	
	/** Construct an Edge object with two vertices connected by said edge and a weight
	 * @param obj1 the first vertex
	 * @param obj2 the second vertex
	 * @param wt the weight of the edge
	 */
	public Edge(Object obj1, Object obj2, int wt){
		obj1 = object1;
		obj2 = object2;
		wt = weight;
	}
	/**
	   * hashCode() returns a hashCode equal to the sum of the hashCodes of each
	   * of the two objects of the pair, so that the order of the objects will
	   * not affect the hashCode.  Self-edges are treated differently:  we don't
	   * add an object's hashCode to itself, since the result would always be even.
	   * We add one to the hashCode so that a self-edge will not collide with the
	   * object itself if vertices and edges are stored in the same hash table.
	   * @return the hash code for the object
	   */
	  public int hashCode() {
	    if (object1.equals(object2)) {
	      return object1.hashCode() + 1;
	    } else {
	      return object1.hashCode() + object2.hashCode();
	    }
	  }

	  /**
	   * equals() returns true if this VertexPair represents the same unordered
	   * pair of objects as the parameter "o".  The order of the pair does not
	   * affect the equality test, so (u, v) is found to be equal to (v, u).
	   * @return true if the objects are equal, false otherwise
	   */
	  public boolean equals(Object o) {
	    if (o instanceof Edge) {
	      return ((object1.equals(((Edge) o).object1)) &&
	              (object2.equals(((Edge) o).object2))) ||
	             ((object1.equals(((Edge) o).object2)) &&
	              (object2.equals(((Edge) o).object1)));
	    } else {
	      return false;
	    }
	  }
	  /**Getter method for the first vertex
	   * @return the first vertex
	   */
	  public Object v1(){
		  return object1;
	  }
	  /**Getter method for the second vertex
	   * @return the second vertex
	   */
	  public Object v2(){
		  return object2;
	  }
	  /** Getter method for the weight
	   * @return the weight of the edge
	   */
	  public int weight(){
		  return weight;
	  }
}
