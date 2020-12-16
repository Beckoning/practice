package com.pay.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.es.provider.EsApplication;
import com.pay.es.provider.config.ElasticSearchClient;
import com.pay.es.provider.model.Canal;
import com.pay.es.provider.model.SmsLogs;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 测试canal binlog日志日志同步
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
@Slf4j
public class EsCanalTest {

    RestHighLevelClient restHighLevelClient =  ElasticSearchClient.getClient();
    String index = "canal";
    String type = "_doc";

    /**
     * 创建canal索引
     * @throws Exception
     */
    @Test
    public void createCanalIndexTest() throws Exception{



        //1、准备索引的settings
        Settings.Builder settings =Settings.builder()
                .put("number_of_shards","5")
                .put("number_of_replicas","1");


        //2、准备索引结构的mappings
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id")
                .field("type","long")
                .endObject()
                .startObject("name")
                .field("type","text")
                .endObject()
                .startObject("sex")
                .field("type","text")
                .endObject()
                .startObject("age")
                .field("type","long")
                .endObject()
                .startObject("amount")
                .field("type","text")
                .endObject()
                .startObject("email")
                .field("type","text")
                .endObject()
                .startObject("occur_time")
                .field("type","date")
                .endObject()


                .endObject()
                .endObject();

        //讲settings和mapping封装到一个Resuest对象中
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index)
                .settings(settings)
                .mapping(type,xContentBuilder);

        //通过client对象去连接es并执行创建索引
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }



    /**
     * 查询所有的信息
     * @throws Exception
     */
    @Test
    public void matchAllQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.matchAllQuery());

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * 批量添加
     * @throws Exception
     */
    @Test
    public void bulkCreateTest() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();

        BulkRequest bulkRequest = new BulkRequest();
        //准备数据

        bulkRequest.add(new IndexRequest(index, type, 2+"").source(objectMapper.writeValueAsString(new Canal((long) 1, "12", "223", 1 , 1.1 , "sdsdsd", new Date())), XContentType.JSON));


        BulkResponse bulkResponse =  restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());

    }

}
