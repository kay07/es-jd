package com.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kay
 * @date 2021/9/15
 */
@Configuration
public class EsConfig {
    @Value("${es.hostname}")
    private String hostname;
    @Value("${es.port}")
    private int port;
    @Value("${es.scheme}")
    private String scheme;
    @Bean
    public RestHighLevelClient client(){
        HttpHost httpHost = new HttpHost(hostname,port,scheme);
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient client=new RestHighLevelClient(builder);
        return client;
    }
}
