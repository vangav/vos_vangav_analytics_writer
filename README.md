
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


























