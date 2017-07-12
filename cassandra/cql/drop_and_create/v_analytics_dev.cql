// Keyspace: v_analytics

DROP KEYSPACE IF EXISTS v_analytics;

CREATE KEYSPACE v_analytics
  WITH replication = {
    'class': 'SimpleStrategy', 'replication_factor' : 1
};


USE v_analytics;

DROP TABLE IF EXISTS daily_action_counters;

CREATE TABLE daily_action_counters (
  year_month_day_action varchar,
  action_count counter,
  PRIMARY KEY (year_month_day_action) )
  WITH CACHING = 'ALL';


DROP TABLE IF EXISTS monthly_action_counters;

CREATE TABLE monthly_action_counters (
  year_month_action varchar,
  action_count counter,
  PRIMARY KEY (year_month_action) )
  WITH CACHING = 'ALL';


DROP TABLE IF EXISTS annual_action_counters;

CREATE TABLE annual_action_counters (
  year_action varchar,
  action_count counter,
  PRIMARY KEY (year_action) )
  WITH CACHING = 'ALL';


DROP TABLE IF EXISTS total_action_counters;

CREATE TABLE total_action_counters (
  action varchar,
  action_count counter,
  PRIMARY KEY (action) )
  WITH CACHING = 'ALL';


