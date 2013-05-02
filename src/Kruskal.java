/* Kruskal.java */

import dict.HashTableChained;
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
	 * @param g the WUGraph to be manipulated
	 * @return a minimum spanning tree of g
	 */
    public static WUGraph minSpanTree(WUGraph g){ 
		WUGraph t = new WUGraph(); 
		int indexEdge = 0;
		int counter = 0;
		Object[] gVert = g.getVertices();
		Object[] edgeArray = new Object[2];
		Edge[] edgesObjects = new Edge[2 * g.edgeCount() + 1];
		for (int i = 0; i < gVert.length; i++) {
			Neighbors neighbors = g.getNeighbors(gVert[i]);
				for (int j = 0; j < neighbors.neighborList.length; j++) {
						edgesObjects[indexEdge] = new Edge(gVert[i], neighbors.neighborList[j], neighbors.weightList[j]); 
						// Error here on second run of KruskalTest .. Null Pointer Exception ... 
						// The Edge class is being filled with null values instead of the values from gVert, neighborlist, and weightlist 
						indexEdge += 1;
				}
			}
		edgeArray[0] = edgesObjects;
		edgeArray[1] = indexEdge - 1;
		Object[] IndexEdges = edgeArray;
		Edge[] edges = (Edge []) IndexEdges[0];
		HashTableChained gTable = new HashTableChained(gVert.length);
		DisjointSets sets = new DisjointSets(gTable.size());
		for (int i = 0; i < gVert.length; i++){
			g.addVertex(gVert[i]);
			gTable.insert(gVert[i], counter);
			counter += 1;
		}
		quicksort(edges, 0, (Integer) IndexEdges[1]);
		for (int i = 0; i < (Integer) IndexEdges[1] +1; i++){ // Need to add one in order to access every element
			int v1 = sets.find((Integer) (gTable.find(edges[i].v1()).value()));
			int v2 = sets.find((Integer) (gTable.find(edges[i].v2()).value()));
			if (v1 != v2){
				sets.union(v1, v2);
				t.addEdge(edges[i].v1(), edges[i].v2(), edges[i].weight());
			}
		}
		return t;
		
	}

	
	/**
	 * Method to swap two ints in an array
	 * @param a an array of ints.
	 * @param index1 the index of the first int to be swapped.
	 * @param index2 the index of the second int to be swapped.
	 **/
	public static void swapReferences(Edge[] a, int index1, int index2) {
		Edge tmp = a[index1];
		a[index1] = a[index2];
		a[index2] = tmp;
	}

	/**
	 * This is a version of C.A.R Hoare's Quick Sort algorithm modified to sort Edges. This
	 * will handle arrays that are already sorted, and arrays with duplicate
	 * keys.
	 * 
	 * If you think of an array as going from the lowest index on the left to
	 * the highest index on the right then the parameters to this function are
	 * lowest index or left and highest index or right. The first time you call
	 * this function it will be with the parameters 0, a.length - 1.
	 * 
	 * @param a an Edge array to be sorted
	 * @param lo0 left boundary of array partition
	 * @param hi0 right boundary of array partition
	 **/
	private static void quicksort(Edge a[], int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;
		int mid;

		if (hi0 > lo0) {

			// Arbitrarily establishing partition element as the midpoint of
			// the array.
			swapReferences(a, lo0, (lo0 + hi0) / 2);
			mid = a[(lo0 + hi0) / 2].weight;

			// loop through the array until indices cross.
			while (lo <= hi) {
				// find the first element that is greater than or equal to
				// the partition element starting from the left Index.
				while ((lo < hi0) && (a[lo].weight < mid)) {
					lo++;
				}

				// find an element that is smaller than or equal to
				// the partition element starting from the right Index.
				while ((hi > lo0) && (a[hi].weight > mid)) {
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
