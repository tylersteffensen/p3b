/**
 * Filename:   MyProfiler.java
 * Project:    p3b-201901     
 * Authors:    TODO: add your name(s) and lecture numbers here
 *
 * Semester:   Spring 2019
 * Course:     CS400
 * 
 * Due Date:   TODO: add assignment due date and time
 * Version:    1.0
 * 
 * Credits:    TODO: name individuals and sources outside of course staff
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;
    
    /**
     * Initialize both data structures. Hashtable capactity initiated to num_elements
     * @param num_elements - number of elements to insert/retrieve from each data structure
     */
    public MyProfiler(int num_elements) {
        treemap = new TreeMap<>();
        hashtable = new HashTable<>(num_elements, .80);
    }
    
    /**
     * Insert the specified key value pair into both data structures. Print out error message if one
     * is unable to do so
     * @param key - key to insert 
     * @param value - value to insert with specified key
     */
    public void insert(K key, V value) {
      try {
        treemap.put(key,value);
      } catch (Exception e) {
        System.out.print("treemap was unable to insert: ("+key+","+value+")");
      }
      try {
        hashtable.insert(key, value);
      } catch (Exception e) {
        System.out.print("hashtable was unable to insert: ("+key+","+value+")");
      }
    }
    
    /**
     * Retrieve the specified value from both data structures. Print out error message if one is 
     * unable to do so.
     * @param key - key to retrieve from the data structure 
     */
    public void retrieve(K key) {
      try {
        treemap.get(key);
      } catch (Exception e) {
        System.out.print("treemap failed to retrieve "+ key);
      }
      
      try {
        hashtable.get(key);
      } catch (Exception e) {
        System.out.print("hashtable failed to retrieve "+ key);
      }
    }
    
    /**
     * Main Method to run the Profiler class. This will test the efficiency of both java treemap
     * and Tyler Steffensen's hash table implementation by running large amounts of inserts and 
     * retrieves on both 
     * @param args
     */
    public static void main(String[] args) {
        try {
            int numElements = Integer.parseInt(args[0]);
            MyProfiler<Integer, Integer> profile = new MyProfiler<>(numElements);
            
            for (int i=0; i<numElements; i++) 
              profile.insert(i, i);
            
            for (int a=0; a<numElements; a++)
              profile.retrieve(a);            
        
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        }
        catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
