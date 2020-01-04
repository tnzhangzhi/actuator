elasticsearch
第一课：创建索引、支持的类型、理解分片的概念
1.1 创建索引
PUT lj_user
{
  "settings":{
    "analysis": {
      "analyzer": {
        "my_ik":{ //还可以配置Token Filters、Character Filters 见官方文档
          "tokenizer": "ik_smart"
        }
      }
    }, 
    "number_of_shards":1,
    "number_of_replicas":2
  },
  "mappings": {
    "properties": {
      "id":{
        "type": "long"
      },
      "name":{
        "type": "text",
        "analyzer": "my_ik", 
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
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss"
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
答：1.doc_values 默认开启，当一个字段被索引时，ES会把"词"加入到倒排索引中，
 同时也会把这些词加入到列式存储doc_values(存储在硬盘)上，主要作用基于字段聚合、排序
   2.null_value 该属性指定一个值，当字段的值为NULL时，该字段使用null_value代替NULL值；
   在ES中，NULL 值不能被索引和搜索，当一个字段设置为NULL值，ES引擎认为该字段没有任何值，
   使用该属性为NULL字段设置一个指定的值，使该字段能够被索引和搜索。
   3.该属性控制字段是否编入索引被搜索，该属性共有三个有效值：analyzed(默认)、no和not_analyzed
   4.store默认为no，设置为true时候，会在_source之外单独存储。

1.3 理解分片的概念
1.3.1 你可以增加节点node到集群，提升搜索能力
1.3.2 ES自动分发数据和查询负载到所有的节点node
1.3.3 ES可以把一个索引分成多个分片，好处是构成分布式搜索
1.3.4 主分片(shard)在索引创建前指定，并且索引创建完后不能修改
1.3.5 副本(replicas)副本提高系统容错，另外提升查询效率
1.3.6 recovery代表数据恢复或重新分布，当有节点加入或退出会对索引分片进行重新分配
问题：
1.主分片设置多少合适？
答： ES 在重新平衡数据时移动(例如发生故障)分片的速度，将取决于分片的大小和数量以及网络和磁盘性能,
避免使用非常大的分片，因为这会对群集从故障中恢复的能力产生负面影响。 对分片的大小没有固定的限制，
但是通常情况下很多场景限制在 50GB 的分片大小以内,每个分片本质上就是一个Lucene索引, 因此会消耗相应的文件句柄, 内存和CPU资源
注1：由于每个分片的开销取决于分段的数量和大小，因此通过 forcemerge 操作强制将较小的分段合并为较大的分段，
这样可以减少开销并提高查询性能。 理想情况下，一旦不再向索引写入数据，就应该这样做。 
请注意，这是一项比较耗费性能和开销的操作，因此应该在非高峰时段执行。
注2：如果担心数据的快速增长, 建议根据这条限制: ES推荐的最大JVM堆空间 是 30~32G, 
所以把分片最大容量限制为 30GB, 然后再对分片数量做合理估算。例如, 如果的数据能达到 200GB, 则最多分配7到8个分片。
注3：如果是基于日期的索引需求, 并且对索引数据的搜索场景非常少. 也许这些索引量将达到成百上千, 
但每个索引的数据量只有1GB甚至更小. 对于这种类似场景, 建议是只需要为索引分配1个分片。
如果使用ES的默认配置(5个分片), 并且使用 Logstash 按天生成索引, 那么 6 个月下来,
 拥有的分片数将达到 890 个. 再多的话, 你的集群将难以工作--除非提供了更多(例如15个或更多)的节点。

2.如何让主分片均匀分配？
参见移动分片的API

1.4 常用命令
1.4.1 查看所有索引 GET /_cat/indices?v
1.4.2 删除索引 DELETE lj_user

第二课：分词
1.测试我是中国人的分词情况
GET lj_user/_analyze
{
  "analyzer": "ik_smart", 
  "text":"我是中国人"
}
2.查看某条记录的某个字段分词情况
GET lj_user/_doc/1/_termvectors?fields=name
{
}

第三课：搜索
第四课：集群
第五课：运维