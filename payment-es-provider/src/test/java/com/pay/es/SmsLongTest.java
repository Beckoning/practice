package com.pay.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.es.provider.EsApplication;
import com.pay.es.provider.config.ElasticSearchClient;
import com.pay.es.provider.model.Person;
import com.pay.es.provider.model.SmsLogs;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
@Slf4j
public class SmsLongTest {

    RestHighLevelClient restHighLevelClient =  ElasticSearchClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    /**
     * 新增索引
     * @throws Exception
     */
    @Test
    public void createSmsLogIndexTest() throws Exception{



        //1、准备索引的settings
        Settings.Builder settings =Settings.builder()
                .put("number_of_shards","5")
                .put("number_of_replicas","1");


        //2、准备索引结构的mappings
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("createDate")
                            .field("type","date")
                        .endObject()
                        .startObject("sendDate")
                            .field("type","date")
                        .endObject()
                        .startObject("longCode")
                            .field("type","text")
                            .field("analyzer","ik_max_word")
                        .endObject()
                        .startObject("mobile")
                            .field("type","text")
                            .field("analyzer","ik_max_word")
                        .endObject()
                        .startObject("corpName")
                            .field("type","text")
                            .field("analyzer","ik_max_word")
                        .endObject()
                        .startObject("smsContent")
                             .field("type","text")
                             .field("analyzer","ik_max_word")
                        .endObject()
                        .startObject("status")
                            .field("type","integer")
                        .endObject()
                        .startObject("operatorId")
                            .field("type","integer")
                        .endObject()
                        .startObject("province")
                            .field("type","keyword")
//                            .field("analyzer","ik_max_word")
                        .endObject()
                        .startObject("ipAddr")
                            .field("type","ip")
                        .endObject()
                        .startObject("replyTotal")
                             .field("type","integer")
                        .endObject()
                        .startObject("free")
                            .field("type","integer")
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
     * 批量添加
     * @throws Exception
     */
    @Test
    public void bulkCreateTest() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();

        BulkRequest bulkRequest = new BulkRequest();
        //准备数据
        for (int i=20; i<28;i++) {
            int status = i % 2;
            int operatorId = i % 3;
            int provinceId = i % 5;
            String province;
            switch (provinceId){
                case 0 :
                    province = "上海";break;
                case 1 :
                    province = "北京";break;
                case 2 :
                    province = "湖南";break;

                case 3:
                    province = "河南";break;
                case 4:
                    province = "天津";break;
                case 5:
                    province = "福建";break;
                case 6:
                    province = "安庆";break;
                default :
                    province = "河北";break;
            }

            bulkRequest.add(new IndexRequest(index, type, "" + i).source(objectMapper.writeValueAsString(new SmsLogs("" + i, new Date(), new Date(), "sdsd" + i, "9999" + i, "sdsdsd" + i, "wewewe" + i, status, operatorId, province, "127.0.0.1", i, i)), XContentType.JSON));
        }

        BulkResponse bulkResponse =  restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());

    }


}
