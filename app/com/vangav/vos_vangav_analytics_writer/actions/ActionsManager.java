/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.vos_vangav_analytics_writer.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.vangav.backend.content.checking.CharVerifierInl;
import com.vangav.backend.content.checking.CharVerifierInl.CharType;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.vos_vangav_analytics_writer.actions.json.ActionClassJson;
import com.vangav.vos_vangav_analytics_writer.actions.json.ActionIdJson;
import com.vangav.vos_vangav_analytics_writer.actions.json.ActionsJson;
import com.vangav.vos_vangav_analytics_writer.actions.json.CategoryJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ActionsManager loads/reloads and stores in memory actions
 *   it's also used to validate and retrieve actions
 */
public class ActionsManager {
  
  private static ActionsManager instance = null;
  
  // category name short > class prefix > action id
  private Map<String, Map<String, Set<String> > > categoriesIndex;
  // class prefix > action id
  private Map<String, Set<String> > actionClassesIndex;
  /**
   * loadLatch is activated on loading/reloading actions to make new get
   *   requests wait till loading/reloading is finished
   */
  private CountDownLatch loadLatch;
  /**
   * getLatch is used to make loading/reloading requests wait till current
   *   get requests are finished
   */
  private AtomicInteger getLatch;
  
  /**
   * Constructor - ActionsManager
   * @return new ActionsManager Object
   * @throws Exception
   */
  private ActionsManager () throws Exception {
    
    this.categoriesIndex = new HashMap<String, Map<String, Set<String> > >();
    this.actionClassesIndex = new HashMap<String, Set<String> >();

    this.loadLatch = new CountDownLatch(0);
    this.getLatch = new AtomicInteger(0);
  }
  
  /**
   * i
   * @return singleton instance of ActionsManager
   * @throws Exception
   */
  public static ActionsManager i () throws Exception {
    
    if (instance == null) {
      
      instance = new ActionsManager();
    }
    
    return instance;
  }
  
  /**
   * lockLoad
   * locks for loading/reloading then waits for in-processing get requests
   * @throws Exception
   */
  private void lockLoad () throws Exception {
    
    // lock for loading/reloading actions
    this.loadLatch = new CountDownLatch(1);
    
    // wait for in-processing get requests to finish
    while (this.getLatch.get() > 0) {
      
      try {
        
        synchronized (this.getLatch) {

          this.getLatch.wait(); 
        }
      } catch (InterruptedException e) {
        
      }
    }
  }
  
  /**
   * releaseLoad
   * releases the loading/reloading lock to allow for new get requests
   * @throws Exception
   */
  private void releaseLoad () throws Exception {
    
    // release loading/reloading lock
    this.loadLatch.countDown();
  }
  
  /**
   * lockGet
   * waits for in-processing loading/reloading requests then adds a get lock
   * @throws Exception
   */
  private void lockGet () throws Exception {
    
    // wait for loading/reloading to finish
    try {
      
      this.loadLatch.await();
    } catch (InterruptedException e) {
    }
    
    // increment get latch
    this.getLatch.incrementAndGet();
  }
  
  /**
   * releaseGet
   * releases a get lock
   * @throws Exception
   */
  private void releaseGet () throws Exception {
    
    // decrement get latch and notify it
    this.getLatch.decrementAndGet();
    
    synchronized (this.getLatch) {

      this.getLatch.notify(); 
    }
  }

  /**
   * load
   * loads actions.json and organizes it in in-memory data structures
   * @throws Exception
   */
  public synchronized void load () throws Exception {
    
    // locks for loading/reloading
    this.lockLoad();
    
    // load actions.json
    ActionsJson actionsJson =
      (ActionsJson)FileLoaderInl.loadJsonFile(new ActionsJson() );
    
    // verify actions.json
    this.verify(actionsJson);
    
    // store in memory
    this.storeInMemory(actionsJson);
    
    // releases loading/reloading lock
    this.releaseLoad();
  }
  
  /**
   * verify
   * throws an exception if the loaded json file isn't correct
   * @param actionsJson
   * @throws Exception
   */
  private void verify (ActionsJson actionsJson) throws Exception {
    
    Set<String> categoryNamesShort = new HashSet<String>();
    
    // verify categories?
    if (actionsJson.categories != null) {
      
      // verify categories
      for (CategoryJson categoryJson : actionsJson.categories) {
        
        // verify category
        if (categoryJson == null ||
            categoryJson.category_name_short == null ||
            categoryJson.category_name_short.length() == 0 ||
            CharVerifierInl.consistsOfCharTypes(
              categoryJson.category_name_short,
              CharType.LOWER_CASE,
              CharType.UPPER_CASE,
              CharType.DIGIT,
              CharType.DASH,
              CharType.UNDER_SCORE) == false ||
            categoryNamesShort.contains(categoryJson.category_name_short) ==
              true) {
          
          throw new CodeException(
            400,
            1,
            "an actions' catoegory must be:"
              + " 1) not null"
              + " 2) its short name isn't null"
              + " 3) its short name isn't empty"
              + " 4) its short name consists of"
              + " letters/numbers/dashes/underscores"
              + " 5) its short name isn't a duplicate",
            ExceptionClass.JSON);
        }
        
        categoryNamesShort.add(categoryJson.category_name_short);
      }
    }
    
    // verify action classes
    
    if (actionsJson.action_classes == null ||
        actionsJson.action_classes.length == 0) {
      
      throw new CodeException(
        400,
        2,
        "an actions.json file must have at least one actions class",
        ExceptionClass.JSON);
    }
    
    Set<String> classesPrefixes = new HashSet<String>();
    Set<String> actionIds;
    
    // for each actions class
    for (ActionClassJson actionClassJson : actionsJson.action_classes) {
      
      // verify actions class's prefix
      if (actionClassJson == null ||
          actionClassJson.class_prefix == null ||
          actionClassJson.class_prefix.length() == 0 ||
          CharVerifierInl.consistsOfCharTypes(
            actionClassJson.class_prefix,
            CharType.LOWER_CASE,
            CharType.UPPER_CASE,
            CharType.DIGIT,
            CharType.DASH,
            CharType.UNDER_SCORE) == false ||
          classesPrefixes.contains(actionClassJson.class_prefix) == true ||
          actionClassJson.action_ids == null ||
          actionClassJson.action_ids.length == 0) {
        
        throw new CodeException(
          400,
          3,
          "an actions class must be:"
            + " 1) not null"
            + " 2) its prefix isn't null"
            + " 3) its prefix isn't empty"
            + " 4) its prefix consists of"
            + " letters/numbers/dashes/underscores"
            + " 5) its prefix isn't a duplicate"
            + " 6) its array of action ids has at least one element",
          ExceptionClass.JSON);
      }
      
      classesPrefixes.add(actionClassJson.class_prefix);
      
      // verify actions class's wide categories?
      if (actionClassJson.class_wide_categories != null) {
        
        // for each action class wide category
        for (String classWideCategory :
             actionClassJson.class_wide_categories) {
          
          // action class wide category doesn't exist?
          if (categoryNamesShort.contains(classWideCategory) == false) {
            
            throw new CodeException(
              400,
              4,
              "class wide category prefix ["
                + classWideCategory
                + "] for action class ["
                + actionClassJson.class_name
                + ", "
                + actionClassJson.class_prefix
                + "] doesn't exist",
              ExceptionClass.JSON);
          }
        }
      }
      
      // an action class must have at least one action id
      if (actionClassJson.action_ids == null ||
          actionClassJson.action_ids.length == 0) {
        
        throw new CodeException(
          400,
          5,
          "action class ["
            + actionClassJson.class_name
            + ", "
            + actionClassJson.class_prefix
            + "] must have at least one action id",
          ExceptionClass.JSON);
      }
      
      // verify action class's action ids
      
      actionIds = new HashSet<String>();
      
      // for each action id
      for (ActionIdJson actionIdJson : actionClassJson.action_ids) {
        
        // verify action and id
        if (actionIdJson == null ||
            actionIdJson.action_id == null ||
            actionIdJson.action_id.length() == 0 ||
            CharVerifierInl.consistsOfCharTypes(
              actionIdJson.action_id,
              CharType.LOWER_CASE,
              CharType.UPPER_CASE,
              CharType.DIGIT,
              CharType.DASH,
              CharType.UNDER_SCORE) == false ||
            actionIds.contains(actionIdJson.action_id) == true) {
          
          throw new CodeException(
            400,
            6,
            "action class ["
              + actionClassJson.class_name
              + ", "
              + actionClassJson.class_prefix
              + "]"
              + " an action id must be:"
              + " 1) not null"
              + " 2) its id isn't null"
              + " 3) its id isn't empty"
              + " 4) its id consists of"
              + " letters/numbers/dashes/underscores"
              + " 5) its id isn't a duplicate",
            ExceptionClass.JSON);
        }
        
        actionIds.add(actionIdJson.action_id);
        
        // verify action's categories?
        if (actionIdJson.action_categories != null) {
          
          // for each of this action's category
          for (String actionCategory : actionIdJson.action_categories) {
            
            // action's category doesn't exist?
            if (categoryNamesShort.contains(actionCategory) == false) {
              
              throw new CodeException(
                400,
                7,
                "action category prefix ["
                  + actionCategory
                  + "] for action class ["
                  + actionClassJson.class_name
                  + ", "
                  + actionClassJson.class_prefix
                  + "] for action id ["
                  + actionIdJson.action_id
                  + "] doesn't exist",
                ExceptionClass.JSON);
            }
          }
        }
      }
    }
  }
  
  /**
   * storeInMemory
   * stores actions in categories index and action classes index
   * @param actionsJson
   * @throws Exception
   */
  private void storeInMemory (ActionsJson actionsJson) throws Exception {
    
    // reset memory
    this.categoriesIndex.clear();
    this.actionClassesIndex.clear();
    
    // got categories
    if (actionsJson.categories != null) {
      
      // index categories' keys (short names)
      for (CategoryJson categoryJson : actionsJson.categories) {
        
        this.categoriesIndex.put(
          categoryJson.category_name_short,
          new HashMap<String, Set<String> >() );
      }
    }
    
    // fill categories index
    for (ActionClassJson actionClassJson : actionsJson.action_classes) {
      
      // got class wide categories?
      if (actionClassJson.class_wide_categories != null) {
        
        // index action class's wide categories
        for (String classWideCategory :
             actionClassJson.class_wide_categories) {
          
          // first entry?
          if (this.categoriesIndex.get(classWideCategory).containsKey(
                actionClassJson.class_prefix) == false) {
            
            this.categoriesIndex.get(classWideCategory).put(
              actionClassJson.class_prefix,
              new HashSet<String>() );
          }
          
          // index action id
          for (ActionIdJson actionIdJson : actionClassJson.action_ids) {
            
            this.categoriesIndex.get(classWideCategory).get(
              actionClassJson.class_prefix).add(actionIdJson.action_id);
          }
        }
      }
      
      // index per-action-id categories
      for (ActionIdJson actionIdJson : actionClassJson.action_ids) {
        
        if (actionIdJson.action_categories != null) {
          
          for (String actionCategory : actionIdJson.action_categories) {
            
            // first entry?
            if (this.categoriesIndex.get(actionCategory).containsKey(
                  actionClassJson.class_prefix) == false) {
              
              this.categoriesIndex.get(actionCategory).put(
                actionClassJson.class_prefix,
                new HashSet<String>() );
            }
            
            // index action id
            this.categoriesIndex.get(actionCategory).get(
              actionClassJson.class_prefix).add(actionIdJson.action_id);
          }
        }
      }
    }
    
    // fill action classes index
    for (ActionClassJson actionClassJson : actionsJson.action_classes) {
      
      this.actionClassesIndex.put(
        actionClassJson.class_prefix,
        new HashSet<String>() );
      
      // for each action class's action id
      for (ActionIdJson actionIdJson : actionClassJson.action_ids) {
        
        this.actionClassesIndex.get(actionClassJson.class_prefix).add(
          actionIdJson.action_id);
      }
    }
  }
  
  /**
   * validateCategory
   * @param categoryNameShort
   * @return true if param categoryNameShort exists and false otherwise
   * @throws Exception
   */
  public boolean validateCategory (String categoryNameShort) throws Exception {
    
    this.lockGet();
    
    boolean result = this.categoriesIndex.containsKey(categoryNameShort);
    
    this.releaseGet();
    
    return result;
  }
  
  /**
   * validateActionClass
   * @param actionClassPrefix
   * @return true if param actionClassPrefix exists and false otherwise
   * @throws Exception
   */
  public boolean validateActionClass (
    String actionClassPrefix) throws Exception {
    
    this.lockGet();
    
    boolean result = this.actionClassesIndex.containsKey(actionClassPrefix);
    
    this.releaseGet();
    
    return result;
  }
  
  /**
   * validateActionId
   * @param actionClassPrefix
   * @param actionId
   * @return true if param actionId exists under param actionClassPrefix and
   *           false otherwise
   * @throws Exception
   */
  public boolean validateActionId (
    String actionClassPrefix,
    String actionId) throws Exception {
    
    this.lockGet();
    
    boolean result;
    
    if (this.actionClassesIndex.containsKey(actionClassPrefix) == false) {
      
      result = false;
    } else {
      
      result = this.actionClassesIndex.get(actionClassPrefix).contains(actionId);
    }
    
    this.releaseGet();
    
    return result;
  }
  
  /**
   * getCategoryActions
   * @param categoryNameShort
   * @return a map of action classes pointing to their action ids belonging to
   *           the category identified by param categoryNameShort and null
   *           otherwise
   * @throws Exception
   */
  public Map<String, Set<String> > getCategoryActions (
    String categoryNameShort) throws Exception {
    
    this.lockGet();
    
    Map<String, Set<String> > result =
      this.categoriesIndex.get(categoryNameShort);
    
    this.releaseGet();
    
    return result;
  }
  
  /**
   * getClassActions
   * @param classPrefix
   * @return a set of the action ids belonging to the action class identified
   *           by param classPrefix and null otherwise
   * @throws Exception
   */
  public Set<String> getClassActions (String classPrefix) throws Exception {
    
    this.lockGet();
    
    Set<String> result = this.actionClassesIndex.get(classPrefix);
    
    this.releaseGet();
    
    return result;
  }
  
  private static final String kSeparator = "_";
  
  /**
   * format
   * @param actionClassPrefix
   * @param actionId
   * @return formatted version of the action id for purposes like using it
   *           as a single string (varchar) id in cassandra by concatenating
   *           the action class's prefix with the action id
   * @throws Exception
   */
  public static String format (
    String actionClassPrefix,
    String actionId) throws Exception {
    
    if (actionClassPrefix == null) {
      
      return actionId;
    }
    
    if (actionId == null) {
      
      return actionClassPrefix;
    }
    
    return
      actionClassPrefix
      + kSeparator
      + actionId;
  }
  
  /**
   * format
   * @param actions
   * @return formatted version of the action id for purposes like using it
   *           as a single string (varchar) id in cassandra by concatenating
   *           the action class's prefix with the action id
   * @throws Exception
   */
  public static ArrayList<String> format (
    Map<String, Set<String> > actions) throws Exception {
    
    ArrayList<String> result = new ArrayList<String>();
    
    if (actions == null) {
      
      return result;
    }
    
    for (String key : actions.keySet() ) {
      
      if (key == null || actions.get(key) == null) {
        
        continue;
      }
      
      for (String value : actions.get(key) ) {
        
        if (value == null) {
          
          continue;
        }
        
        result.add(
          key
          + kSeparator
          + value);
      }
    }
    
    return result;
  }
  
  /**
   * format
   * @param actionClassPrefix
   * @param actionIds
   * @return formatted version of the action id for purposes like using it
   *           as a single string (varchar) id in cassandra by concatenating
   *           the action class's prefix with the action id
   * @throws Exception
   */
  public static ArrayList<String> format (
    String actionClassPrefix,
    Set<String> actionIds) throws Exception {
    
    ArrayList<String> result = new ArrayList<String>();
    
    if (actionClassPrefix == null || actionIds == null) {
      
      return result;
    }
    
    for (String actionId : actionIds) {
      
      if (actionId == null) {
        
        continue;
      }
      
      result.add(
        actionClassPrefix
        + kSeparator
        + actionId);
    }
    
    return result;
  }
}
