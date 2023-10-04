package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Chloe Kelly
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
      DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> cloneArr = new AssociativeArray<K, V>();
    int i = 0;
    while(this.pairs[i] != null){
      cloneArr.set(this.pairs[i].key, this.pairs[i].value);
      i++;
    }
    return cloneArr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    if (this.size <= 0){
      return "{}";
    } //if
    String[] strings = new String[this.size]; //create string array 
    for(int i = 0; i < strings.length; i++){
      strings[i] = this.pairs[i].key.toString() + ": " + this.pairs[i].value.toString();
      //save KVPair in the correct string format to the array
    }
    return "{ " + String.join(", ", strings) + " }"; //print the array
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) {
    try{
      this.pairs[find(key)].value = value;
    } catch (Exception e) {
      if(this.size >= this.pairs.length){
        this.expand();
      } //if
      this.pairs[this.size++] = new KVPair<K, V>(key, value);
    } //try/catch
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key does not appear in the associative
   *                              array.
   */
  public V get(K key) throws KeyNotFoundException {
    try{
      return this.pairs[this.find(key)].value;
    } catch (Exception e) {
      throw new KeyNotFoundException();
    } //try/catch
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) {
    try{
      return (this.find(key) >= 0);
    } catch (Exception e){
      return false;
    }  //try/catch
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    try{
      int toDelete = this.find(key);
      KVPair<K,V> moveToEmpty = this.pairs[this.size-1]; //Save ending value
      this.pairs[toDelete] = moveToEmpty; //Overwrite the pair to delete with the ending pair
      this.pairs[this.size-1] = null;//Set that last index to null
      --this.size;
    } catch (Exception e){
      //Do nothing
    }//try/catch
  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    int i = 0;
    while(this.pairs[i] != null){
      if (key.equals(this.pairs[i].key)) {
        return i;
      }//if
      i++;
    }//while
    throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray