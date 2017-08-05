
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


























