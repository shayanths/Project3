/* Entry.java */

package dict;
import list.DListNode;

/**
 *  A class for dictionary entries.
 *
 *  DO NOT CHANGE THIS FILE.  It is part of the interface of the
 *  Dictionary ADT.
 **/

public class Entry {

  protected Object key;
  protected Object value;
  public DListNode e1;
  public DListNode e2;
  public DListNode VertexList;
 

  public Object key() {
    return key;
  }

  public Object value() {
    return value;
  }
  
  public void weightchange(int w){
	 value = w;
  }

}
