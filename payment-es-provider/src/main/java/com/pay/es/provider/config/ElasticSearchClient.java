package com.pay.es.provider.config;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

public class ElasticSearchClient {


    public static  RestHighLevelClient  getClient(){
        //创建 httpHost对象
        HttpHost httpHost = new HttpHost("106.55.159.72",9200);

        //创建 RestClientBuilder
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);

        //创建restHighLevelClient
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;

    }

    /**
     * 测试创建索引
     */
//    public static void main(String[] args)throws Exception {
//
//        String index = "person";
//        String type = "man";
//        RestHighLevelClient restHighLevelClient =  getClient();
//
//
//        //1、准备索引的settings
//        Settings.Builder settings =Settings.builder()
//                .put("number_of_shards","5")
//                .put("number_of_replicas","1");
//
//        //2、准备索引结构的mappings
//        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
//                .startObject()
//                    .startObject("properties")
//                        .startObject("name")
//                            .field("type","text")
//                            .field("analyzer","ik_max_word")
//                        .endObject()
//                        .startObject("age")
//                        .field("type","long")
//                        .endObject()
//                        .startObject("birth")
//                        .field("type","date")
//                        .field("format","yyyy-MM-dd")
//                        .endObject()
//                    .endObject()
//                .endObject();
//
//        //讲settings和mapping封装到一个Resuest对象中
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index)
//                .settings(settings)
//                .mapping(type,xContentBuilder);
//
//        //通过client对象去连接es并执行创建索引
//        getClient().indices().create(createIndexRequest, RequestOptions.DEFAULT);
//
//    }
}
