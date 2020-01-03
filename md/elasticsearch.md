elasticsearch
第一课：创建索引、支持的类型、理解分片的概念
1.1 创建索引
PUT lj_user
{
  "mappings": {
    "properties": {
      "id":{
        "type": "long"
      },
      "name":{
        "type": "text",
        "fields": {
          "keyword":{
            "type":"keyword"
          }
        }
      },
      "password":{
        "type": "keyword"
      },
      "mobile":{
        "type": "keyword"
      },
      "token":{
        "type": "keyword"
      },
      "ip":{
        "type": "ip"
      },
      "city":{
        "type": "keyword"
      },
      "country":{
        "type": "keyword"
      },
      "status":{
        "type": "keyword"
      },
      "point":{
        "type": "geo_point"
      },
      "create_time":{
        "type": "date"
      },
      "last_login_time":{
        "type": "date"
      }
    }
  }
}
1.2 支持的字段类型
字符类型：text 和 keyword 
数字类型：long,integer,short,byte,double,float,half_float,scaled_float
日期类型：date (字符串"2015-01-01"，long类型毫秒数，integer类型秒数)
日期毫秒数：date_nanos
布尔类型：boolean
Binary: binary
Range: integer_range,float_range,long_range,double_range,date_range
Object：json object（object for single JSON objects）
Nested：json 数组（nested for arrays of JSON objects）
Geo-point：geo_point for lat/lon points
Geo-shape：geo_shape for complex shapes like polygons
IP：ip for IPv4 and IPv6 addresses
Specialised datatypesedit：见官方文档
Arrays：见官方文档
Multi-fields：见官方文档
官方文档：https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html
问题：
字段属性（index,store,null_value,doc_values，index_phrases）什么意思，用途是什么？

1.3 理解分片的概念
1.3.1 你可以增加节点node到集群，提升搜索能力
1.3.2 ES自动分发数据和查询负载到所有的节点node
1.3.3 ES可以把一个索引分成多个分片，好处是构成分布式搜索
1.3.4 主分片(shard)在索引创建前指定，并且索引创建完后不能修改
1.3.5 副本(replicas)副本提高系统容错，另外提升查询效率
1.3.6 recovery代表数据恢复或重新分布，当有节点加入或退出会对索引分片进行重新分配
问题：主分片设置多少合适？