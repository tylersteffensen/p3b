//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P3b Hash Table Implementation
// Files:           DataStuctureADT.java, HashTableADT.java, HashTable.java,
//                  HashTableTest.java
// Course:          CS400, Spring 2019
//
// Author:          Tyler Steffensen
// Email:           steffensen@wisc.edu
// Lecturer's Name: Deb Deppeler
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    n/a
// Partner Email:   n/a
// Partner Lecturer's Name: n/a
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         n/a
// Online Sources:  n/a
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Hash table implementation that uses a CHAINED BUCKET: array of linked nodes implementation.
 *  Note: This means that when a new key value pair is inserted into the table,
 *      a new node is created to store the information and a hash index is calculated using the
 *      key of the pair. The hash index is calculated taking the absolute value using Java's 
 *      hashCode() algorithm and then the remainder of that number based on the capacity of the array
 *      list which the nodes are stored in. To solve for collisions, each index in the array list is 
 *      a reference to a linked list and the new node is added to the linked list. This linked list
 *      could get very long if the same hash index is produced many times
 *  
 * @author tylersteffensen
 *
 * @param <K> the type of the key object of the pair 
 * @param <V> the type of the value object of the pair
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
    
    private ArrayList<LinkedList<Node<K,V>>> table; // the hash table of LinkedList node references
    private int cap; // current capacity of the hash table
    private int size; // number of nodes in the hash table 
    private double lf; // load factor threshold for the hash table (#items / T.S.)
    
    private class Node<K,V> {
      private K key; // key of the node being added to the hash table
      private V value; // value of the node being added to the hash table
      
      protected Node(K key, V value) {
        this.key = key;
        this.value = value;
      }
    }
    
    /**
     * No argument constructor for the Hash Table class
     *     Initialize a new ArrayList for the hash table
     *     Initial capacity set to 10
     *     Initial load factor threshold set to .75
     *     Size set to 0 
     */
    public HashTable() { 
      this.cap = 10;
      this.size = 0;
      this.lf = .75;
      this.table = new ArrayList<>(this.cap);
      for (int i=0; i < this.cap; i++) 
        table.add(i, null);
    }
    

    /**
     * Two argument constructor for the Hash Table class: 
     *     
     *     Initializes a new ArrayList 
     *     Capacity set by user
     *     Size 0
     *     Load Factor Threshold set by user
     *
     * @param initialCapacity - initial size of the hash table (ArrayList)
     * @param loadFactorThreshold - load factor threshold before expanding ArrayList
     */
    public HashTable(int initialCapacity, double loadFactorThreshold) {
      this.size = 0;
      this.cap = initialCapacity;
      this.table = new ArrayList<LinkedList<Node<K,V>>>(this.cap);
      this.lf = loadFactorThreshold;
      for (int i=0; i < this.cap; i++) 
        table.add(i, null);
    }

    /**
     * Add the key,value pair to the data structure and increase the number of keys. 
     * If key is null, throw IllegalNullKeyException; 
     * If key is already in data structure, throw DuplicateKeyException();
     */
    @Override
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
      // check for null key
      if (key == null) 
        throw new IllegalNullKeyException();
      // generate hash index for the new node to be added 
      int i = hashFunction(key); 
      // if there is no node at this hash index create a new linked list and add the new node to it
      if (table.get(i) == null) {
        LinkedList<Node<K,V>> newList = new LinkedList<>();
        newList.add(new Node<K,V>(key,value));
        table.add(i, newList);
        ++size;
      } else {
        // traverse the linked list to check for duplicates then add to the end
        for (int x=0; x<table.get(i).size(); x++) {
          if (table.get(i).get(x).key.equals(key))
            throw new DuplicateKeyException();
        }
        // append the new node to the end of the linked list at the specified hash index
        table.get(i).add(new Node<K,V>(key,value));
        ++size;
      }
      // check if the hash table needs to be re-sized 
      getLoadFactorThreshold();
    }
    
    /**
     * Private method to calculate a hash index for the hash table using java's
     * hashCode method. Unique key generates hash code which is converted to the absolute
     * value to ensure all hash indexes returned are positive. Then find the remainder of the 
     * hash index compared to the size of the table to make sure the index returned is within 
     * range of the current hash table 
     * @param key - unique key for the key value pair
     * @return hash index to map the key value pair into the hash table
     */
    private int hashFunction(K key) {
      int hash = Math.abs(key.hashCode());
      hash = (hash % this.cap);
      return hash;
    }
    
    /**
     * If key is found, 
     *   remove the key,value pair from the data structure
     *   decrease number of keys.
     *   return true
     * If key is null, throw IllegalNullKeyException
     * If key is not found, return false
     */
    @Override
    public boolean remove(K key) throws IllegalNullKeyException {
      // check if key is null
      if (key == null)
        throw new IllegalNullKeyException();
      // generate hash index for the new node to be added 
      int i = hashFunction(key); 
    
      // Search for the key,value pair
      if (table.get(i) != null) {
        for (int a=0; a<table.get(i).size(); a++) {
          if (table.get(i).get(a).key.equals(key)) {
            table.get(i).remove(a);
            --size;
            return true;
          }
        }
      }
      return false; // pair was not found
    }

    /**
     * Returns the value associated with the specified key
     * Does not remove key or decrease number of keys
     * If key is null, throw IllegalNullKeyException 
     * If key is not found, throw KeyNotFoundException().
     */
    @Override
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
      if (key == null)
        throw new IllegalNullKeyException();
      // generate hash index for the new node to be added 
      int i = hashFunction(key);
      Node<K,V> match = null;
      // Search for the key,value pair
      if (table.get(i) != null) {
        // traverse the linked list looking for a matching value
        for (int a=0; a<table.get(i).size(); a++) {
          if (table.get(i).get(a).key.equals(key)) {
            match = table.get(i).get(a);
            return match.value;
          }
        }
      }
      // check if the match was found
      if (match != null)
        return match.value;
      else 
        throw new KeyNotFoundException();
    }

    /**
     * Returns the number of key,value pairs in the data structure
     */
    @Override
    public int numKeys() {
      return size;
    }

  /**
   * Returns the load factor threshold that was passed into the constructor 
   * when creating the instance of the HashTable. When the current load factor 
   * is greater than or equal to the specified load factor threshold, the table 
   * is resized and elements are rehashed.
   */
  @Override
  public double getLoadFactorThreshold() {
    if (getLoadFactor() >= this.lf) {
      // save the current capacity to be used in rehashing 
      int currentCap = this.cap;
      // double the capacity + 1 of the hash table 
      getCapacity();
      // create a new hash table to begin rehashing (first fill it with null values)
      ArrayList<LinkedList<Node<K,V>>> newTable = new ArrayList<>(this.cap);
      for (int i=0; i < this.cap; i++) 
        newTable.add(i, null);
      
      // re-hash the nodes within the current array into the new array
      for (int i=0; i<currentCap; i++) {
        LinkedList<Node<K,V>> tempNode = table.get(i);
        
        // rehash all of the elements in the linked list into the table 
        if (tempNode != null) {
          for (int z=0; z<tempNode.size(); z++) {
            int a = hashFunction(tempNode.get(z).key); // compute hash index from node's key
            
            // create a new linked list object to add to the hash table
            LinkedList<Node<K,V>> newNode = new LinkedList<>();
            newNode.add(new Node<K,V>(tempNode.get(z).key, tempNode.get(z).value));
            
            // check if this index has been rehashed yet
            if (newTable.get(a) == null) {
              newTable.add(a, newNode); 
            }
            else {
              newTable.get(a).add(new Node<K,V>(tempNode.get(z).key, tempNode.get(z).value));
            }
          }
        }
      }
      this.table = newTable;
    }
    return this.lf;
  }

    /**
     * Returns the current load factor for this hash table
     * load factor = number of items / current table size
     */
    @Override
    public double getLoadFactor() {
      return (double)size/(double)cap;
    }

    /**
     * Return the current Capacity (table size)
     * of the hash table array.
     * 
     * The initial capacity must be a positive integer, 1 or greater
     * and is specified in the constructor.
     * 
     * REQUIRED: When the load factor threshold is reached, 
     * the capacity must increase to: 2 * capacity + 1
     * 
     * Once increased, the capacity never decreases
     * @return int number of (key,value) pairs in the array
     */
    @Override
    public int getCapacity() {
      if (getLoadFactor() >= this.lf) 
        // double the size of the hash table (+1 so the size is an odd number)
        this.cap = (this.cap * 2)+1;
      return this.cap;
    }

    /**
     * Returns the collision resolution scheme used for this hash table.
     * Implement with one of the following collision resolution strategies.
     * Define this method to return an integer to indicate which strategy.
     * 
     * This hash table implementation uses a CHAINED BUCKET: array of linked nodes
     * and returns 5 to signify that fact
     * 
     * @return int 5 CHAINED BUCKET: array of linked nodes
     */
    @Override
    public int getCollisionResolution() {
      return 5;
    }       
}
