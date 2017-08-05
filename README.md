
> **why?** vangav analytics is a [vangav backend](https://github.com/vangav/vos_backend) template covering: service oriented architecture and multi-entry-point api; this is also the analytics service used by all vangav's products

# vangav analytics writer

+ [vangav analytics writer](https://github.com/vangav/vos_vangav_analytics_writer) and [vangav analytics reader](https://github.com/vangav/vos_vangav_analytics_reader) services work together and are generated using [vangav backend](https://github.com/vangav/vos_backend)

## prerequisite

+ [vangav backend tutorials](https://github.com/vangav/vos_backend)

## functionality

### design philosophy

+ splitting the analytics backend into writer and reader services is done because writer/reader loads can vary significantly (i.e.: writer can take the load of millions of users using analyzed services while the reader can be used by few system admins); so it's easier to handle these loads by deploying a lot more writer than reader services
+ actions are defined in a flexible/generic json file (as explained later in this section) to make it easily extensible in handling analysis as backend services change/grow
+ both of the writer and reader services have actions-config-reload feature to allow modifying actions without having to re-start the services

### [vangav analytics writer](https://github.com/vangav/vos_vangav_analytics_writer)

+ handles writing analytics

### [vangav analytics reader](https://github.com/vangav/vos_vangav_analytics_reader)

+ handles reading actions in various ways (e.g.: by year, by day, by category, ...)

### actions structure

+ [actions.json](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json) in both services define how analytics are structured

+ here's a simple example

```json
  {
    "categories": [
      {
        "category_name": "page_views",
        "category_name_short": "PV"
      }
    ],
    "action_classes": [
      {
        "class_name": "backend_page_views",
        "class_prefix": "BPV",
        "class_wide_categories": [
          "PV"
        ],
        "action_ids": [
          {
            "action_id": "quick_start",
            "action_categories": []
          }
        ]
      }
    ]
  }
```

| element | explanation |
| ------- | ----------- |
| [categories](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L2) | defines categories (e.g.: page views, button clicks, ...) |
| [category_name](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L4) | a category name is used mainly for clarity |
| [category_name_short](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L5) | is the one used when querying for categories |
| [action_classes](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L8) | defines classes (e.g.: website, ios app, android app, ...) |
| [class_name](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L10) | a class name is used mainly for clarity |
| [class_prefix](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L11) | is the one used when querying for classes |
| [class_wide_categories](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L12) | categories applied for an action class's action-ids |
| [action_ids](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L15) | defines a single action (e.g.: home-page, download-button, camera-flip, ...) |
| [action_id](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L17) | unique action identifier within its class |
| [action_categories](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json#L18) | in addition to the class-wide-categories, here one can add extra categories specific to this action |

## overview

+ this service is based on vangav backend's [vangav analytics template](https://github.com/vangav/vos_backend/tree/master/vangav_backend_templates/analytics)
+ this service has the 90+% of the vangav backend's generated code + the 10-% of the logic code needed to complete the service

## try this service

1. *for first timers* - follow the steps in the [system requirements tutorial](https://github.com/vangav/vos_backend#system-requirements)
2. *for first timers* - follow the steps in the [workspace initialization tutorial](https://github.com/vangav/vos_backend#init)
3. download [`vos_vangav_analytics_writer.zip`](https://github.com/vangav/vos_vangav_analytics_writer) and [`vos_vangav_analytics_reader.zip`](https://github.com/vangav/vos_vangav_analytics_reader) projects (from the green `clone or download` button) inside the workspace directory created previously (`my_services`) and unzip them
4. **rename** unzipped directories, remove the `-master` from their names
5. in the terminal `cd` to `vos_vangav_analytics_writer/cassandra/cql/`
6. execute `./_start_cassandra.sh` to start cassandra
7. `cd` to `vos_vangav_analytics_writer/cassandra/cql/drop_and_create/`
8. execute the command `./_execute_cql.sh v_analytics_dev.cql` to initialize the services' database tables
9. `cd` to `vos_vangav_analytics_writer` and execute `./_run.sh` to start the analytics writer service on port 9000
10. `cd` to `vos_vangav_analytics_reader` and execute `./_run.sh 7000` to start the analytics reader service on port 7000
11. from your prefered client (*we recommned* [postman](https://www.getpostman.com/docs/postman/launching_postman/installation_and_updates)) start trying the service; refer to the **features** and **service references** sections for reference
+ at the end to stop the services: press `control + d` in the terminal session where each service was started in (9 and 10)
+ to stop cassandra: execute `ps auwx | grep cassandra` to get cassandra's `(pid)` then `kill -9 (pid)` to stop cassandra

## covered topics

+ generate multiple services (writer + reader) to work together in a service oriented architecture
+ generic service design (handles any type of analytics)

## features

### [vangav analytics writer](https://github.com/vangav/vos_vangav_analytics_writer)

| controller | feature |
| ------------- | ------- |
| [record action](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/app/com/vangav/vos_vangav_analytics_writer/controllers/record_action) | handles incrementing analytics counters for the specified action |
| [reload properties](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/app/com/vangav/vos_vangav_analytics_writer/controllers/reload_properties) | handles reloading properties files as well as [actions.json](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json) |

### [vangav analytics reader](https://github.com/vangav/vos_vangav_analytics_reader)

| controller(s) | feature |
| ------------- | ------- |
| [get total action counters](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_total_action_counters), [get annual action counters](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_annual_action_counters), [get monthly action counters](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_monthly_action_counters), [get daily action counters](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_daily_action_counters), [get category actions](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_category_actions) and [get current date](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_current_date) | handle getting analytics data by category, action and/or time |
| [reload properties](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers/reload_properties) | handles reloading properties files as well as [actions.json](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/conf/setup_data/actions.json) |

## service references

### [vangav analytics writer](https://github.com/vangav/vos_vangav_analytics_writer)

| reference | explanation |
| --------- | ----------- |
| [routes](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/routes) | api routes |
| [setup data](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/conf/setup_data) | contains [actions.json](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json) used to define the analytics structure |
| [controllers.json](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/generator_config/controllers.json) | api request/response's elements |
| [v_analytics.keyspace](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/generator_config/v_analytics.keyspace) | `v_analytics` is the keyspace used for storing analytics |
| [Global.java](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/app/Global.java) | actions structure initialization |
| [actions](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/app/com/vangav/vos_vangav_analytics_writer/actions) | contains the json representation of [actions.json](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json) as well as its loader |
| [controllers](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/app/com/vangav/vos_vangav_analytics_writer/controllers) | api implementation |
| [v_analytics](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/app/com/vangav/vos_vangav_analytics_writer/cassandra_keyspaces/v_analytics) | `v_analytics` cassandra's keyspace client |

### [vangav analytics reader](https://github.com/vangav/vos_vangav_analytics_reader)

| reference | explanation |
| --------- | ----------- |
| [routes](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/conf/routes) | api routes |
| [setup data](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/conf/setup_data) | contains [actions.json](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/conf/setup_data/actions.json) used to define the analytics structure |
| [controllers.json](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/generator_config/controllers.json) | api request/response's elements |
| [v_analytics.keyspace](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/generator_config/v_analytics.keyspace) | `v_analytics` is the keyspace used for storing analytics |
| [Global.java](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/Global.java) | actions structure initialization |
| [actions](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/actions) | contains the json representation of [actions.json](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/conf/setup_data/actions.json) as well as its loader |
| [controllers](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers) | api implementation |
| [v_analytics](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/cassandra_keyspaces/v_analytics) | `v_analytics` cassandra's keyspace client |

## change log

+ this section lists the 10-% code added after vangav backend generated 90+% of the code

### [vangav analytics writer](https://github.com/vangav/vos_vangav_analytics_writer)

| file/dir | change |
| -------- | ------ |
| [setup data](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/conf/setup_data) | added [actions.json](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json) used to define the analytics structure |
| [Global.java](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/app/Global.java) | added actions structure initialization |
| [actions](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/app/com/vangav/vos_vangav_analytics_writer/actions) | added the json representation of [actions.json](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/conf/setup_data/actions.json) as well as its loader |
| [controllers](https://github.com/vangav/vos_vangav_analytics_writer/tree/master/app/com/vangav/vos_vangav_analytics_writer/controllers) | added the implementation of request processing logic under `controller_name/HandlerControllerName.java` classes and nested response json structures under `controller_name/response_json` packages |

### [vangav analytics reader](https://github.com/vangav/vos_vangav_analytics_reader)

| file/dir | change |
| -------- | ------ |
| [setup data](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/conf/setup_data) | added [actions.json](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/conf/setup_data/actions.json) used to define the analytics structure |
| [Global.java](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/Global.java) | added actions structure initialization |
| [actions](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/actions) | added the json representation of [actions.json](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/conf/setup_data/actions.json) as well as its loader |
| [controllers](https://github.com/vangav/vos_vangav_analytics_reader/tree/master/app/com/vangav/vos_vangav_analytics_reader/controllers) | added the implementation of request processing logic under `controller_name/HandlerControllerName.java` classes and nested response json structures under `controller_name/response_json` packages |

## error codes

+ following are the error codes of whatsapp services

### [vangav analytics writer](https://github.com/vangav/vos_vangav_analytics_writer)

| class | code : sub_code | explanation |
| ----- | --------------- | ----------- |
| [ActionsManager](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/app/com/vangav/vos_vangav_analytics_writer/actions/ActionsManager.java) |  |  |
|  | 400 : 1 | invalid actions' category |
|  | 400 : 2 | actions-config must have at least one actions-class |
|  | 400 : 3 | invalid actions class |
|  | 400 : 4 | action class wide category doesn't exist |
|  | 400 : 5 | an action class must have at least one action id |
|  | 400 : 6 | invalid action id |
|  | 400 : 7 | invalid action category prefix |
| [RequestRecordAction](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/app/com/vangav/vos_vangav_analytics_writer/controllers/record_action/RequestRecordAction.java) |  |  |
|  | 501 : 1 | invalid class prefix and/or action id |

### [vangav analytics reader](https://github.com/vangav/vos_vangav_analytics_reader)

| class | code : sub_code | explanation |
| ----- | --------------- | ----------- |
| [ActionsManager](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/com/vangav/vos_vangav_analytics_reader/actions/ActionsManager.java) |  |  |
|  | 400 : 1 | invalid actions' category |
|  | 400 : 2 | actions-config must have at least one actions-class |
|  | 400 : 3 | invalid actions class |
|  | 400 : 4 | action class wide category doesn't exist |
|  | 400 : 5 | an action class must have at least one action id |
|  | 400 : 6 | invalid action id |
|  | 400 : 7 | invalid action category prefix |
| [RequestGetAnnualActionCounters](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_annual_action_counters/RequestGetAnnualActionCounters.java) |  |  |
|  | 501 : 1 | invalid actions class prefix |
|  | 501 : 2 | invalid action id |
|  | 501 : 3 | to-year can't be smaller than from-year |
|  | 501 : 4 | can't query more than 100 years per request |
| [RequestGetDailyActionCounters](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_daily_action_counters/RequestGetDailyActionCounters.java) |  |  |
|  | 502 : 1 | invalid actions class prefix |
|  | 502 : 2 | invalid action id |
|  | 502 : 3 | can't query more than 365 days |
| [RequestGetMonthlyActionCounters](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_monthly_action_counters/RequestGetMonthlyActionCounters.java) |  |  |
|  | 503 : 1 | invalid actions class prefix |
|  | 503 : 2 | invalid action id |
|  | 503 : 3 | can't query more than 120 months |
| [RequestGetTotalActionCounters](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_total_action_counters/RequestGetTotalActionCounters.java) |  |  |
|  | 504 : 1 | actions class prefix |
|  | 504 : 2 | invalid action id |
| [RequestGetCategoryActions](https://github.com/vangav/vos_vangav_analytics_reader/blob/master/app/com/vangav/vos_vangav_analytics_reader/controllers/get_category_actions/RequestGetCategoryActions.java) |  |  |
|  | 507 : 1 | category-name-short doesn't exists |
