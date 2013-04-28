/* HashTableChained.java */

//HASH TABLE UNCHAINED

package dict;
import list.*;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
	protected DList[] buckets;
	protected int size;
	protected int numBuckets;



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
	  size =0;
	  numBuckets = sizeEstimate + sizeEstimate/3;
	  
	  while (!isPrime(numBuckets)){
		  numBuckets++;
	  }
	  buckets = new DList[numBuckets];
	  for (int i =0; i<numBuckets; i++){
		  buckets[i] = new DList();
	  }
	  
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    size =0;
    numBuckets = 101;
    buckets = new DList[numBuckets];
    for (int i=0; i<numBuckets; i++){
    	buckets[i] = new DList();
    }
  }
  
  private boolean isPrime(int num){
	  for (int divisor = 2; divisor < num; divisor++) {
	        if (num % divisor == 0) {
	          return false;
	        }
	      }
	      return true;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int magic= Math.abs(131 * code +66673) % numBuckets;
    return magic;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    return size ==0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
	Entry entry = new Entry();
	entry.key = key;
	entry.value = value;
    int hashCode = key.hashCode();
    int comp = compFunction(hashCode);
    buckets[comp].insertFront(entry);
    size++;
    double n = size;
    double N = numBuckets;
    if (n/N > 0.75){
    	resize();
    }
    return entry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int hashCode = key.hashCode();
    int comp = compFunction(hashCode);
    List chain = buckets[comp];
    ListNode n = chain.front();
    while (n.isValidNode()){
    	try{
    		Entry entry = (Entry) n.item();
    		if (entry.key.equals(key)){
    			return entry;
    		}
    		n = n.next();
    	} catch (InvalidNodeException e){
    		System.err.println(e);
    	}
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    int hashCode = key.hashCode();
    int comp = compFunction(hashCode);
    List list = buckets[comp];
    ListNode n = list.front();
    while (n.isValidNode()){
    	try{
    		Entry entry = (Entry) n.item();
    		if (entry.key.equals(key)){
    			n.remove();
    			size--;
    			return entry;
    		}
    		n = n.next();
    	} catch (InvalidNodeException e){
    		System.err.println(e);
    	}
    }
    return null;
    
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    size =0;
    for(int i =0; i<numBuckets; i++){
    	buckets[i] = new DList();
    }
  }
  
  private void resize(){
	  HashTableChained newHashTable = new HashTableChained(numBuckets*2);
	  for(int i =0;i<buckets.length;i++){
		  try{
			  DListNode n = (DListNode)buckets[i].front();
			  while (n.isValidNode()){
				  newHashTable.insert(((Entry)n.item()).key(),((Entry)n.item()).value());
				  n= (DListNode) n.next();
			  }
		  }catch (InvalidNodeException e){System.err.println(e);}
		  
	  }
	  this.buckets = newHashTable.buckets;
	  this.size = newHashTable.size;
	  this.numBuckets = newHashTable.numBuckets;
  }

}
