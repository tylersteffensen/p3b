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

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*; 

import org.junit.Test;


/**
 * Test class to ensure that the hash table is functioning as expected 
 * @author tylersteffensen
 *
 */
public class HashTableTest{
    
    /** 
     * Tests that a HashTable returns an integer code
     * indicating which collision resolution strategy 
     * is used.
     * REFER TO HashTableADT for valid collision scheme codes.
     */
    @Test
    public void test000_collision_scheme() {
        HashTableADT htIntegerKey = new HashTable<Integer,String>();
        int scheme = htIntegerKey.getCollisionResolution();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }
        
    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that insert(null,null) throws IllegalNullKeyException
     */
    @Test
    public void test001_IllegalNullKey() {
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.insert(null, null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }
    
    /**
     * Test to see if number of keys inserted tracked correctly
     */
    @Test
    public void test002_Insert_increases_size() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert 4 pairs into the table
        hash.insert(1, 1);
        hash.insert(2, 2);
        hash.insert(3, 3);
        hash.insert(4, 4);
        
        // check if keys inserted is tracked properly
        if (hash.numKeys() != 4)
          fail("hash table not counting number of keys correctly");
        
      } catch (Exception e) {
        fail("Should not throw exception "+e.getClass().getName());
      }
    }
    
    /**
     * Test to see if number of keys inserted then removed tracked correctly
     */
    @Test
    public void test003_Insert_remove_decreases_size() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert 4 pairs into the table
        hash.insert(1, 1);
        hash.insert(2, 2);
        hash.insert(3, 3);
        hash.insert(4, 4);
        // remove 4 pairs from the table
        hash.remove(1);
        hash.remove(2);
        hash.remove(3);
        hash.remove(4);
        
        if (hash.numKeys() != 0)
          fail("hash table not counting number of keys correctly");
        
      } catch (Exception e) {
        fail("Should not throw exception "+e.getClass().getName());
      }
    }
    
    /**
     * Test to see if inserting duplicates throws the correct exception
     */
    @Test
    public void test004_Insert_duplicate_throws_exception() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert duplicate pairs into the table
        hash.insert(1, 1);
        hash.insert(1, 1);
        fail("inserting duplicates should throw an exception");
      } catch (DuplicateKeyException e){
        // exception that should be thrown
      } catch (Exception e) {
      fail("Should not throw exception "+e.getClass().getName());
      }
    } 
    
    /**
     * Test to see if removing null keys throws the correct exception
     */
    @Test
    public void test005_Remove_null_throws_exception() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert duplicate pairs into the table
        hash.remove(null);
        fail("removing null should throw an exception");
      } catch (IllegalNullKeyException e){
        // exception that should be thrown
      } catch (Exception e) {
      fail("Should not throw exception "+e.getClass().getName());
      }
    }
    
    /**
     * Test to see if keys actually inserted and removed from the table
     */
    @Test
    public void test006_Insert_actually_inserts_and_remove_removes() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert 4 pairs into the table
        hash.insert(1, 1);
        hash.insert(2, 2);
        hash.insert(3, 3);
        hash.insert(4, 4);
        
        if (hash.get(2) != 2 && hash.get(4) != 4)
          fail("hash table not actually inserting values");
        
        // remove 4 pairs from the table
        hash.remove(2);
        hash.remove(4);
        
        hash.get(2);
        fail("Exception should be thrown; hash not removing nodes properly");
        
      } catch (KeyNotFoundException e) {
        // this should be thrown
      } catch (Exception e) {
        fail("Should not throw exception "+e.getClass().getName());
      }
    }
    
    /**
     * Test to see if remove returns true when key is removed and false when 
     * a key is not removed 
     */
    @Test
    public void test007_Remove_returns_true_AND_false() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert 4 pairs into the table
        hash.insert(1, 1);
        hash.insert(2, 2);
        hash.insert(3, 3);
        hash.insert(4, 4);
        // remove 4 pairs from the table
        if (!hash.remove(1))
          fail("hash should return true since 1 is in the table");
        hash.remove(2);
        hash.remove(3);
        hash.remove(4);
        if (hash.remove(4))
          fail("hash should return false since 4 was already removed from the table");
        
      } catch (Exception e) {
        fail("Should not throw exception "+e.getClass().getName());
      }
    }
    
    /**
     * Test to see if getting null key throws an exception
     */
    @Test
    public void test008_get_null_throws_exception() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert one and get null key
        hash.insert(1, 1);
        hash.get(null);
        fail("getting null should throw exception");
      } catch (IllegalNullKeyException e){
        // exception that should be thrown
      } catch (Exception e) {
      fail("Should not throw exception "+e.getClass().getName());
      }
    }
    
    /**
     * Test to see if table resizes correctly once load factor threshold is reached 
     */
    @Test
    public void test009_get_load_factor_threshold() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>(5, .5);
        // insert duplicate pairs into the table
        if (hash.getLoadFactorThreshold() != .5)
          fail("load factor threshold not being set correctly");
        // insert 3 values to trigger an expansion of the 
        hash.insert(1, 1);
        hash.insert(2, 3);
        hash.insert(3, 3);
        if (hash.getCapacity() != 11)
          fail("table not being resized correctly");
      } catch (Exception e) {
      fail("insert should not throw exception "+e.getClass().getName());
      }
    }
    
    /**
     * Test to see if keys are rehashed correctly after table is resized 
     */
    @Test
    public void test010_test_rehash() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>(5, .5);
        // check if load factor threshold is set correctly
        if (hash.getLoadFactorThreshold() != .5)
          fail("load factor threshold not being set correctly");
        // insert 3 values to trigger an expansion of the 
        hash.insert(1, 1);
        hash.insert(2, 3);
        hash.insert(3, 3);
        
        if (hash.get(3) != 3)
          fail("When table is resized the keys inside the old table are not rehashed correctly");
      } catch (Exception e) {
      fail("insert should not throw exception "+e.getClass().getName());
      }
    }
    
    
//    /**
//     * Test to see if half a million keys can be inserted into the table
//     */
//    @Test
//    public void test011_insert_a_bunch() {
//      try {
//        HashTable<Integer, Integer> hash = new HashTable<>();
//        // insert half a million values
//        for (int load = 0; load < 500000; load++) 
//          hash.insert(load, load);
//        // check if numKeys incremented correctly
//        if (hash.numKeys() != 500000)
//          fail("numkeys is not incremented correctly for large data set");
//        
//        if (hash.get(500) != 500)
//          fail("Could not get value after insertion");
//      } catch (Exception e) {
//      fail("insert should not throw exception "+e.getClass().getName());
//      }
//    }
    
    
    /**
     * Test to see if half a million keys can be inserted into the table
     */
    @Test
    public void test012_insert_a_bunch_and_get_all() {
      try {
        HashTable<Integer, Integer> hash = new HashTable<>();
        // insert half a million values
        for (int load = 0; load < 50000; load++) 
          hash.insert(load, load);
        
        for (int x = 0; x < 50000; x++) 
          if (hash.get(x) != x)
            fail("Could not get value after insertion");

      } catch (Exception e) {
        e.printStackTrace();
      fail("insert should not throw exception "+e.getClass().getName());
      }
    }
    
    
}
