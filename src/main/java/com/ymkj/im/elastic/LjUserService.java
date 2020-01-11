package com.ymkj.im.elastic;

import com.alibaba.fastjson.JSON;
import com.ymkj.im.pojo.LjUser;
import com.ymkj.im.util.DateUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class LjUserService {


//    @Autowired
//    RestHighLevelClient client;

    private static final RestHighLevelClient client;

    static {
         client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("116.62.19.162", 9200, "http"),
                        new HttpHost("47.98.48.58", 9200, "http"),
                        new HttpHost("47.96.101.123", 9200, "http")));
    }

    public static void insert(LjUser user) throws IOException {
        IndexRequest indexRequest = new IndexRequest("lj_user");
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
    }

    public static List<LjUser> search(LjUser user) throws IOException {
        SearchRequest searchRequest = new SearchRequest("lj_user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if(!StringUtils.isEmpty(user.getName())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("name",user.getName()));
        }
        if(!StringUtils.isEmpty(user.getMobile())){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("mobile",user.getMobile()));
        }
        if(!StringUtils.isEmpty(user.getCreate_time())){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("create_time").gte(user.getCreate_time()));
        }
        if(!StringUtils.isEmpty(user.getCountry())){
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("country","*"+user.getCountry()+"*"));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
        System.out.println(response.getHits().getTotalHits().value);

        return null;
    }

    public static void main(String[] args) throws IOException {
        for(int i=0;i<10;i++){
            new Thread(new WorkTask(),"t"+i).start();
            new Thread(new QueryTask(),"t"+i).start();
        }
    }

    static class WorkTask implements Runnable{
        @Override
        public void run() {
            Date start = new Date();
            for(int i=0;i<10000;i++){
                LjUser user = new LjUser();
                user.setName("张志-"+i+"-"+Thread.currentThread().getName());
                user.setCreate_time(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                user.setId((long)i);
                user.setCity("hangzhou"+i);
                user.setCountry("中国"+i);
                user.setIp("192.168.1.1");
                user.setLastLoginTime(new Date());
                user.setMobile("18858132416"+i);
                user.setPassword("123456"+i);
                user.setStatus("online"+i);
                user.setToken("xxxxxxxxxxxxrrrrrrrrr"+i);
                user.setPoint(new GeoPoint().reset(70.20000,30.26667).toString());
                try {
                    insert(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Date end = new Date();
            long time = end.getTime()-start.getTime();
            System.out.println("写完"+time);
        }
    }

    static class QueryTask implements Runnable{

        @Override
        public void run() {
            Date start = new Date();
            for(int i=0;i<10000;i++){
                LjUser user = new LjUser();
                if(i%4 == 0){
                    user.setName("张"+i);
                }else if(i%4 == 1){
                    user.setMobile("18858132416"+i);
                }else if(i%4 == 2){
                    user.setCreate_time("2020-01-11 16:10:10");
                }else if(i%4 == 3){
                    user.setCountry("中国"+i);
                }
                try {
                    search(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Date end = new Date();
            long time = end.getTime()-start.getTime();
            System.out.println("读完"+time);
        }
    }
}
