// GENERATED using CqlScriptsGeneratorMain.java
// Keyspace: v_analytics

CREATE KEYSPACE IF NOT EXISTS v_analytics
  WITH replication = {
    'class': 'SimpleStrategy', 'replication_factor' : 1
};

USE v_analytics;


CREATE TABLE IF NOT EXISTS daily_action_counters (
  year_month_day_action varchar,
  action_count counter,
  PRIMARY KEY (year_month_day_action) )
  WITH CACHING = 'ALL';


CREATE TABLE IF NOT EXISTS monthly_action_counters (
  year_month_action varchar,
  action_count counter,
  PRIMARY KEY (year_month_action) )
  WITH CACHING = 'ALL';


CREATE TABLE IF NOT EXISTS annual_action_counters (
  year_action varchar,
  action_count counter,
  PRIMARY KEY (year_action) )
  WITH CACHING = 'ALL';


CREATE TABLE IF NOT EXISTS total_action_counters (
  action varchar,
  action_count counter,
  PRIMARY KEY (action) )
  WITH CACHING = 'ALL';


