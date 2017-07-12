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
 * DailyActionCounters represents
 *   Table [daily_action_counters]
 *   in Keyspace [v_analytics]
 * 
 * Name: daily_action_counters
 * Description:
 *   stores daily count per action 
 * 
 * Columns:
 *   year_month_day_action : varchar
 *   action_count : counter

 * Partition Keys: year_month_day_action
 * Secondary Keys: 
 * Caching: ALL
 * Order By:

 * Queries:
 *   - Name: increment
 *   Description:
 *     increments action_count 
 *   Prepared Statement:
 *     UPDATE v_analytics.daily_action_counters SET action_count = 
 *     action_count + 1 WHERE year_month_day_action = 
 *     :year_month_day_action; 
 *   - Name: select
 *   Description:
 *     selects action_count 
 *   Prepared Statement:
 *     SELECT action_count FROM v_analytics.daily_action_counters WHERE 
 *     year_month_day_action = :year_month_day_action; 
 * */
public class DailyActionCounters extends Table {

  private static final String kKeySpaceName =
    "v_analytics";
  private static final String kTableName =
    "daily_action_counters";

  public static final String kYearMonthDayActionColumnName =
    "year_month_day_action";
  public static final String kActionCountColumnName =
    "action_count";

  /**
   * Query:
   * Name: increment
   * Description:
   *   increments action_count 
   * Prepared Statement:
   *   UPDATE v_analytics.daily_action_counters SET action_count = 
   *   action_count + 1 WHERE year_month_day_action = 
   *   :year_month_day_action; 
   */
  private static final String kIncrementName =
    "increment";
  private static final String kIncrementDescription =
    "increments action_count ";
  private static final String kIncrementPreparedStatement =
    "UPDATE v_analytics.daily_action_counters SET action_count = "
    + "action_count + 1 WHERE year_month_day_action = "
    + ":year_month_day_action; ";

  /**
   * Query:
   * Name: select
   * Description:
   *   selects action_count 
   * Prepared Statement:
   *   SELECT action_count FROM v_analytics.daily_action_counters WHERE 
   *   year_month_day_action = :year_month_day_action; 
   */
  private static final String kSelectName =
    "select";
  private static final String kSelectDescription =
    "selects action_count ";
  private static final String kSelectPreparedStatement =
    "SELECT action_count FROM v_analytics.daily_action_counters WHERE "
    + "year_month_day_action = :year_month_day_action; ";

  /**
   * Constructor DailyActionCounters
   * @return new DailyActionCounters Object
   * @throws Exception
   */
  private DailyActionCounters () throws Exception {

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

  private static DailyActionCounters instance = null;

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

      instance = new DailyActionCounters();
    }
  }

  /**
   * i
   * @return singleton static instance of DailyActionCounters
   * @throws Exception
   */
  public static DailyActionCounters i () throws Exception {

    if (instance == null) {

      instance = new DailyActionCounters();
    }

    return instance;
  }

  // Query: Increment
  // Description:
  //   increments action_count 
  // Parepared Statement:
  //   UPDATE v_analytics.daily_action_counters SET action_count = 
  //   action_count + 1 WHERE year_month_day_action = 
  //   :year_month_day_action; 

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
   * @param yearmonthdayaction
   * @return Increment Query in the form of
   *           a QueryDisbatchable Object
   *           (e.g.: to be passed on to a worker instance)
   * @throws Exception
   */
  public QueryDispatchable getQueryDispatchableIncrement (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQueryDispatchable(
        kIncrementName,
        yearmonthdayaction);
  }

  /**
   * getBoundStatementIncrement
   * @param yearmonthdayaction
   * @return Increment Query in the form of
   *           a BoundStatement ready for execution or to be added to
   *           a BatchStatement
   * @throws Exception
   */
  public BoundStatement getBoundStatementIncrement (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQuery(kIncrementName).getBoundStatement(
        yearmonthdayaction);
  }

  /**
   * executeAsyncIncrement
   * executes Increment Query asynchronously
   * @param yearmonthdayaction
   * @return ResultSetFuture
   * @throws Exception
   */
  public ResultSetFuture executeAsyncIncrement (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQuery(kIncrementName).executeAsync(
        yearmonthdayaction);
  }

  /**
   * executeSyncIncrement
   * BLOCKING-METHOD: blocks till the ResultSet is ready
   * executes Increment Query synchronously
   * @param yearmonthdayaction
   * @return ResultSet
   * @throws Exception
   */
  public ResultSet executeSyncIncrement (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQuery(kIncrementName).executeSync(
        yearmonthdayaction);
  }

  // Query: Select
  // Description:
  //   selects action_count 
  // Parepared Statement:
  //   SELECT action_count FROM v_analytics.daily_action_counters WHERE 
  //   year_month_day_action = :year_month_day_action; 

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
   * @param yearmonthdayaction
   * @return Select Query in the form of
   *           a QueryDisbatchable Object
   *           (e.g.: to be passed on to a worker instance)
   * @throws Exception
   */
  public QueryDispatchable getQueryDispatchableSelect (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQueryDispatchable(
        kSelectName,
        yearmonthdayaction);
  }

  /**
   * getBoundStatementSelect
   * @param yearmonthdayaction
   * @return Select Query in the form of
   *           a BoundStatement ready for execution or to be added to
   *           a BatchStatement
   * @throws Exception
   */
  public BoundStatement getBoundStatementSelect (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQuery(kSelectName).getBoundStatement(
        yearmonthdayaction);
  }

  /**
   * executeAsyncSelect
   * executes Select Query asynchronously
   * @param yearmonthdayaction
   * @return ResultSetFuture
   * @throws Exception
   */
  public ResultSetFuture executeAsyncSelect (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQuery(kSelectName).executeAsync(
        yearmonthdayaction);
  }

  /**
   * executeSyncSelect
   * BLOCKING-METHOD: blocks till the ResultSet is ready
   * executes Select Query synchronously
   * @param yearmonthdayaction
   * @return ResultSet
   * @throws Exception
   */
  public ResultSet executeSyncSelect (
    Object yearmonthdayaction) throws Exception {

    return
      this.getQuery(kSelectName).executeSync(
        yearmonthdayaction);
  }

}
