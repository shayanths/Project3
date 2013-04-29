/* Kruskal.java */

import graph.*;
import set.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g){
	  //create a new WUGraph, fill it with the same # of vertices as g
	  WUGraph t = new WUGraph();
	  for (int i =0; i<g.vertexCount(); i++){
		  t.addVertex(new Object());
	  }
	  //create a list of all the edges in g
	  Edge[] gEdges = new Edge[g.edgeCount()]; //each edge in the list has a from[0], to[1], and weight[2]
	  Object[] gVert = g.getVertices();
	  for (int i =0; i < g.vertexCount(); i++){
		  Neighbors n = g.getNeighbors(gVert[i]);
		  for (int j=0; j<n.neighborList.length;j++){
			  Object[] verts = {gVert[i], n.neighborList[j]};
			  Edge e = new Edge(verts, n.weightList[j]);
			  gEdges[i] = e;
		  }
	  }
	  quicksort(gEdges);
  }
  
  /**
   *  Quicksort algorithm. (Taken from Lab 12 Sort.java
   *  @param a an array of int items.
   **/
  public static void quicksort(Edge[] a) {
    quicksort(a, 0, a.length - 1);
  }

  /**
   *  Method to swap two ints in an array.
   *  @param a an array of ints.
   *  @param index1 the index of the first int to be swapped.
   *  @param index2 the index of the second int to be swapped.
   **/
  public static void swapReferences(Edge[] a, int index1, int index2) {
    Edge tmp = a[index1];
    a[index1] = a[index2];
    a[index2]= tmp;
  }

  /**
   *  This is a generic version of C.A.R Hoare's Quick Sort algorithm.  This
   *  will handle arrays that are already sorted, and arrays with duplicate
   *  keys.
   *
   *  If you think of an array as going from the lowest index on the left to
   *  the highest index on the right then the parameters to this function are
   *  lowest index or left and highest index or right.  The first time you call
   *  this function it will be with the parameters 0, a.length - 1.
   *
   *  @param a       an integer array
   *  @param lo0     left boundary of array partition
   *  @param hi0     right boundary of array partition
   **/
   private static void quicksort(Edge a[], int lo0, int hi0) {
     int lo = lo0;
     int hi = hi0;
     int mid;

     if (hi0 > lo0) {

       // Arbitrarily establishing partition element as the midpoint of
       // the array.
       swapReferences(a, lo0, (lo0 + hi0)/2);
       mid = a[(lo0 + hi0) / 2].weight;

       // loop through the array until indices cross.
       while (lo <= hi) {
         // find the first element that is greater than or equal to 
         // the partition element starting from the left Index.
         while((lo < hi0) && (a[lo].weight < mid)) {
           lo++;
         }

         // find an element that is smaller than or equal to 
         // the partition element starting from the right Index.
         while((hi > lo0) && (a[hi].weight > mid)) {
           hi--;
         }
         // if the indices have not crossed, swap them.
         if (lo <= hi) {
           swapReferences(a, lo, hi);
           lo++;
           hi--;
         }
       }

       // If the right index has not reached the left side of array
       // we must now sort the left partition.
       if (lo0 < hi) {
         quicksort(a, lo0, hi);
       }

       // If the left index has not reached the right side of array
       // must now sort the right partition.
       if (lo < hi0) {
         quicksort(a, lo, hi0);
       }
     }
   }

}
