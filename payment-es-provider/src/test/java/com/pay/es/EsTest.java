package com.pay.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.es.provider.EsApplication;
import com.pay.es.provider.config.ElasticSearchClient;
import com.pay.es.provider.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexAction;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkAction;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
@Slf4j
public class EsTest {

    RestHighLevelClient restHighLevelClient =  ElasticSearchClient.getClient();


    /**
     * 新增索引
     * @throws Exception
     */
    @Test
    public void createIndexTest() throws Exception{

        String index = "person";
        String type = "man";


        //1、准备索引的settings
        Settings.Builder settings =Settings.builder()
                .put("number_of_shards","5")
                .put("number_of_replicas","1");

        //2、准备索引结构的mappings
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("name")
                            .field("type","text")
                            .field("analyzer","ik_max_word")
                        .endObject()
                        .startObject("age")
                            .field("type","long")
                        .endObject()
                        .startObject("birth")
                            .field("type","date")
                            .field("format","yyyy-MM-dd")
                        .endObject()
                    .endObject()
                .endObject();

        //讲settings和mapping封装到一个Resuest对象中
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index)
                .settings(settings)
                .mapping(type,xContentBuilder);

        //通过client对象去连接es并执行创建索引
        restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
    }


    /**
     * 检查索引是否存在
     * @throws Exception
     */
    @Test
    public void getIndexTest() throws Exception{

        String index = "person";

        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);

        //通过client对象去连接es并执行创建索引
        boolean checkStatus = restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
        System.out.println(checkStatus);
    }

    /**
     * 删除索引
     * @throws Exception
     */
    @Test
    public void deleteIndexTest() throws Exception{

        String index = "person";

        DeleteIndexRequest getIndexRequest = new DeleteIndexRequest();
        getIndexRequest.indices(index);

        //通过client对象去连接es并执行创建索引
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(getIndexRequest,RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());
    }


    /**
     * 新增Doc
     * @throws Exception
     */
    @Test
    public void createDocTest() throws Exception{


        ObjectMapper objectMapper = new ObjectMapper();

        //准备数据
        Person person = new Person(1,"张三",2,new Date());
        String json = objectMapper.writeValueAsString(person);
        System.out.println("--------"+json);

        String index = "person";
        String type = "man";


        //准备Resquest对象
        IndexRequest indexRequest = new IndexRequest(index,type,person.getId().toString());
        indexRequest.source(json, XContentType.JSON);

        //通过client执行添加
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
        System.out.println(indexResponse.getIndex());
    }

    /**
     * 修改Doc
     * @throws Exception
     */
    @Test
    public void updateDocTest() throws Exception{

        String index = "person";
        String type = "man";

        Map<String,Object> map = new HashMap<>();
        map.put("name","李四");


        //准备Resquest对象
        UpdateRequest updateRequest = new UpdateRequest(index,type,"1");
        updateRequest.doc(map);

        //通过client执行添加
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
        System.out.println(updateResponse.getGetResult());
    }

    /**
     * 删除Doc
     * @throws Exception
     */
    @Test
    public void detelteDocTest() throws Exception{

        String index = "person";
        String type = "man";

        //准备Resquest对象
        DeleteRequest updateRequest = new DeleteRequest(index,type,"1");

        //通过client执行添加
        DeleteResponse deleteResponse = restHighLevelClient.delete(updateRequest,RequestOptions.DEFAULT);
        System.out.println(deleteResponse.getResult());
    }


    /**
     * 批量添加
     * @throws Exception
     */
    @Test
    public void bulkCreateTest() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        String index = "person";
        String type = "man";
        //准备数据
        Person person1 = new Person(1,"张三",2,new Date());
        Person person2 = new Person(2,"李四",2,new Date());
        Person person3 = new Person(3,"王五",2,new Date());
        String json1 = objectMapper.writeValueAsString(person1);
        String json2 = objectMapper.writeValueAsString(person2);
        String json3 = objectMapper.writeValueAsString(person3);


        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest(index,type,person1.getId().toString()).source(json1, XContentType.JSON));
        bulkRequest.add(new IndexRequest(index,type,person2.getId().toString()).source(json2, XContentType.JSON));
        bulkRequest.add(new IndexRequest(index,type,person3.getId().toString()).source(json3, XContentType.JSON));



        BulkResponse bulkResponse =  restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());

    }


    /**
     * 批量修改Doc
     * @throws Exception
     */
    @Test
    public void bulkUpdateDocTest() throws Exception{

        String index = "person";
        String type = "man";

        Map<String,Object> map1 = new HashMap<>();
        map1.put("name","李小四");

        Map<String,Object> map2 = new HashMap<>();
        map2.put("name","李二四");
        Map<String,Object> map3 = new HashMap<>();
        map3.put("name","李三四");

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new UpdateRequest(index,type,"1").doc(map1));
        bulkRequest.add(new UpdateRequest(index,type,"2").doc(map2));
        bulkRequest.add(new UpdateRequest(index,type,"3").doc(map3));

        //通过client执行添加
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
    }


    /**
     * 批量修改Doc
     * @throws Exception
     */
    @Test
    public void bulkDeleteDocTest() throws Exception{

        String index = "person";
        String type = "man";


        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new DeleteRequest(index,type,"1"));
        bulkRequest.add(new DeleteRequest(index,type,"2"));
        bulkRequest.add(new DeleteRequest(index,type,"3"));

        //通过client执行添加
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
    }


}

