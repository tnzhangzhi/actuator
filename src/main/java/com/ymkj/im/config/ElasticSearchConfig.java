package com.ymkj.im.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("116.62.19.162", 9200, "http"),
                        new HttpHost("47.98.48.58", 9200, "http"),
                        new HttpHost("47.96.101.123", 9200, "http")));
        return client;
    }
}
