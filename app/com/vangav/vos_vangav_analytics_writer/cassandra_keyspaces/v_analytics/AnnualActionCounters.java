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

package com.vangav.vos_vangav_analytics_writer.cassandra_keyspaces.v_analytics;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.vangav.backend.cassandra.keyspaces.Query;
import com.vangav.backend.cassandra.keyspaces.Table;
import com.vangav.backend.cassandra.keyspaces.dispatch_message.QueryDispatchable;

/**
 * GENERATED using JavaClientGeneratorMain.java
 */
/**
 * AnnualActionCounters represents
 *   Table [annual_action_counters]
 *   in Keyspace [v_analytics]
 * 
 * Name: annual_action_counters
 * Description:
 *   stores annual count per action 
 * 
 * Columns:
 *   year_action : varchar
 *   action_count : counter

 * Partition Keys: year_action
 * Secondary Keys: 
 * Caching: ALL
 * Order By:

 * Queries:
 *   - Name: increment
 *   Description:
 *     increments action_count 
 *   Prepared Statement:
 *     UPDATE v_analytics.annual_action_counters SET action_count = 
 *     action_count + 1 WHERE year_action = :year_action; 
 *   - Name: select
 *   Description:
 *     selects action_count 
 *   Prepared Statement:
 *     SELECT action_count FROM v_analytics.annual_action_counters WHERE 
 *     year_action = :year_action; 
 * */
public class AnnualActionCounters extends Table {

  private static final String kKeySpaceName =
    "v_analytics";
  private static final String kTableName =
    "annual_action_counters";

  public static final String kYearActionColumnName =
    "year_action";
  public static final String kActionCountColumnName =
    "action_count";

  /**
   * Query:
   * Name: increment
   * Description:
   *   increments action_count 
   * Prepared Statement:
   *   UPDATE v_analytics.annual_action_counters SET action_count = 
   *   action_count + 1 WHERE year_action = :year_action; 
   */
  private static final String kIncrementName =
    "increment";
  private static final String kIncrementDescription =
    "increments action_count ";
  private static final String kIncrementPreparedStatement =
    "UPDATE v_analytics.annual_action_counters SET action_count = "
    + "action_count + 1 WHERE year_action = :year_action; ";

  /**
   * Query:
   * Name: select
   * Description:
   *   selects action_count 
   * Prepared Statement:
   *   SELECT action_count FROM v_analytics.annual_action_counters WHERE 
   *   year_action = :year_action; 
   */
  private static final String kSelectName =
    "select";
  private static final String kSelectDescription =
    "selects action_count ";
  private static final String kSelectPreparedStatement =
    "SELECT action_count FROM v_analytics.annual_action_counters WHERE "
    + "year_action = :year_action; ";

  /**
   * Constructor AnnualActionCounters
   * @return new AnnualActionCounters Object
   * @throws Exception
   */
  private AnnualActionCounters () throws Exception {

    super (
      kKeySpaceName,
      kTableName,
      new Query (
        kIncrementDescription,
        kIncrementName,
        kIncrementPreparedStatement),
      new Query (
        kSelectDescription,
        kSelectName,
        kSelectPreparedStatement));
  }

  private static AnnualActionCounters instance = null;

  /**
   * loadTable
   * OPTIONAL method
   * instance is created either upon calling this method or upon the first call
   *   to singleton instance method i
   * this method is useful for loading upon program start instead of loading
   *   it upon the first use since there's a small time overhead for loading
   *   since all queries are prepared synchronously in a blocking network
   *   operation with Cassandra's server
   * @throws Exception
   */
  public static void loadTable () throws Exception {

    if (instance == null) {

      instance = new AnnualActionCounters();
    }
  }

  /**
   * i
   * @return singleton static instance of AnnualActionCounters
   * @throws Exception
   */
  public static AnnualActionCounters i () throws Exception {

    if (instance == null) {

      instance = new AnnualActionCounters();
    }

    return instance;
  }

  // Query: Increment
  // Description:
  //   increments action_count 
  // Parepared Statement:
  //   UPDATE v_analytics.annual_action_counters SET action_count = 
  //   action_count + 1 WHERE year_action = :year_action; 

  /**
   * getQueryIncrement
   * @return Increment Query in the form of
   *           a Query Object
   * @throws Exception
   */
  public Query getQueryIncrement (
    ) throws Exception {

    return this.getQuery(kIncrementName);
  }

  /**
   * getQueryDispatchableIncrement
   * @param yearaction
   * @return Increment Query in the form of
   *           a QueryDisbatchable Object
   *           (e.g.: to be passed on to a worker instance)
   * @throws Exception
   */
  public QueryDispatchable getQueryDispatchableIncrement (
    Object yearaction) throws Exception {

    return
      this.getQueryDispatchable(
        kIncrementName,
        yearaction);
  }

  /**
   * getBoundStatementIncrement
   * @param yearaction
   * @return Increment Query in the form of
   *           a BoundStatement ready for execution or to be added to
   *           a BatchStatement
   * @throws Exception
   */
  public BoundStatement getBoundStatementIncrement (
    Object yearaction) throws Exception {

    return
      this.getQuery(kIncrementName).getBoundStatement(
        yearaction);
  }

  /**
   * executeAsyncIncrement
   * executes Increment Query asynchronously
   * @param yearaction
   * @return ResultSetFuture
   * @throws Exception
   */
  public ResultSetFuture executeAsyncIncrement (
    Object yearaction) throws Exception {

    return
      this.getQuery(kIncrementName).executeAsync(
        yearaction);
  }

  /**
   * executeSyncIncrement
   * BLOCKING-METHOD: blocks till the ResultSet is ready
   * executes Increment Query synchronously
   * @param yearaction
   * @return ResultSet
   * @throws Exception
   */
  public ResultSet executeSyncIncrement (
    Object yearaction) throws Exception {

    return
      this.getQuery(kIncrementName).executeSync(
        yearaction);
  }

  // Query: Select
  // Description:
  //   selects action_count 
  // Parepared Statement:
  //   SELECT action_count FROM v_analytics.annual_action_counters WHERE 
  //   year_action = :year_action; 

  /**
   * getQuerySelect
   * @return Select Query in the form of
   *           a Query Object
   * @throws Exception
   */
  public Query getQuerySelect (
    ) throws Exception {

    return this.getQuery(kSelectName);
  }

  /**
   * getQueryDispatchableSelect
   * @param yearaction
   * @return Select Query in the form of
   *           a QueryDisbatchable Object
   *           (e.g.: to be passed on to a worker instance)
   * @throws Exception
   */
  public QueryDispatchable getQueryDispatchableSelect (
    Object yearaction) throws Exception {

    return
      this.getQueryDispatchable(
        kSelectName,
        yearaction);
  }

  /**
   * getBoundStatementSelect
   * @param yearaction
   * @return Select Query in the form of
   *           a BoundStatement ready for execution or to be added to
   *           a BatchStatement
   * @throws Exception
   */
  public BoundStatement getBoundStatementSelect (
    Object yearaction) throws Exception {

    return
      this.getQuery(kSelectName).getBoundStatement(
        yearaction);
  }

  /**
   * executeAsyncSelect
   * executes Select Query asynchronously
   * @param yearaction
   * @return ResultSetFuture
   * @throws Exception
   */
  public ResultSetFuture executeAsyncSelect (
    Object yearaction) throws Exception {

    return
      this.getQuery(kSelectName).executeAsync(
        yearaction);
  }

  /**
   * executeSyncSelect
   * BLOCKING-METHOD: blocks till the ResultSet is ready
   * executes Select Query synchronously
   * @param yearaction
   * @return ResultSet
   * @throws Exception
   */
  public ResultSet executeSyncSelect (
    Object yearaction) throws Exception {

    return
      this.getQuery(kSelectName).executeSync(
        yearaction);
  }

}
