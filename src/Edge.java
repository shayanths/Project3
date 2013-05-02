
// This will be a similar representation to VPair, but with the use of edges instead of the VertexPari
public class Edge {
	protected Object object1;
	protected Object object2;
	protected int weight;
	
	public Edge(Object obj1, Object obj2, int wt){
		object1 = obj1;
		object2 = obj2;
		weight = wt;
	}
	/**
	   * hashCode() returns a hashCode equal to the sum of the hashCodes of each
	   * of the two objects of the pair, so that the order of the objects will
	   * not affect the hashCode.  Self-edges are treated differently:  we don't
	   * add an object's hashCode to itself, since the result would always be even.
	   * We add one to the hashCode so that a self-edge will not collide with the
	   * object itself if vertices and edges are stored in the same hash table.
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
	  public Object v1(){
		  return object1;
	  }
	  public Object v2(){
		  return object2;
	  }
	  public int weight(){
		  return weight;
	  }
}
