package com.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.es.config.EsConfig;
import com.es.entity.Book;
import com.es.service.BookService;
import com.es.utils.ParseHtml;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kay
 * @date 2021/9/15
 */
@Service
public class BookServiceImpl implements BookService {
    @Value("${es.index}")
    private String index;
    @Resource
    private ParseHtml parseHtml;
    @Resource
    private EsConfig esConfig;

    public List<Book> list(int from,String keywords) throws IOException {
        SearchRequest searchRequest=new SearchRequest(index);
        SearchSourceBuilder builder=new SearchSourceBuilder();
        //matchQuery：会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
        //termQuery：不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到。
        builder.query(QueryBuilders.matchQuery("title",keywords));
        //默认进行了分页
        //from表示开始位置，默认0；size表示每页大小，默认10
        builder.from(from);
        builder.size(30);
        searchRequest.source(builder);
        SearchResponse search = esConfig.client().search(searchRequest, RequestOptions.DEFAULT);
        List<Book> list=new ArrayList<>();
        for (SearchHit hit : search.getHits().getHits()) {
            String js=hit.getSourceAsString();
            JSONObject parse = (JSONObject) JSONObject.parse(js);
            Book book=new Book();
            book.setTitle(parse.getString("title"));
            book.setPrice(parse.getString("price"));
            book.setImage(parse.getString("image"));
            list.add(book);
        }
        return list;
    }

    public boolean intoEs(String keywords) throws Exception {
            List<Book> parse = parseHtml.parse(keywords);
            BulkRequest bulkRequest = new BulkRequest();
            for (int i = 0; i < parse.size(); i++) {
                IndexRequest indexRequest=new IndexRequest(index);
                indexRequest.source(JSON.toJSONString(parse.get(i)),XContentType.JSON);
                bulkRequest.add(indexRequest);
            }
        BulkResponse   bulk = esConfig.client().bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }
}
