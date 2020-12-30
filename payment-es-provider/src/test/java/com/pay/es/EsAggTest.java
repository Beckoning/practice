package com.pay.es;

import com.pay.es.provider.EsApplication;
import com.pay.es.provider.config.ElasticSearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
@Slf4j
public class EsAggTest {

    RestHighLevelClient restHighLevelClient =  ElasticSearchClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";


    /**
     * （1）统计某个字段的数量
     *   ValueCountBuilder vcb=  AggregationBuilders.count("count_uid").field("uid");
     * （2）去重统计某个字段的数量（有少量误差）
     *  CardinalityBuilder cb= AggregationBuilders.cardinality("distinct_count_uid").field("uid");
     * （4）按某个字段分组
     * TermsBuilder tb=  AggregationBuilders.terms("group_name").field("name");
     * （5）求和
     * SumBuilder  sumBuilder= AggregationBuilders.sum("sum_price").field("price");
     * （6）求平均
     * AvgBuilder ab= AggregationBuilders.avg("avg_price").field("price");
     * （7）求最大值
     * MaxBuilder mb= AggregationBuilders.max("max_price").field("price");
     * （8）求最小值
     * MinBuilder min= AggregationBuilders.min("min_price").field("price");
     * （9）按日期间隔分组
     * DateHistogramBuilder dhb= AggregationBuilders.dateHistogram("dh").field("date");
     * （10）获取聚合里面的结果
     * TopHitsBuilder thb=  AggregationBuilders.topHits("top_result");
     *
     *
     *
     *
     * （3）聚合过滤
     * FilterAggregationBuilder fab= AggregationBuilders.filter("uid_filter").filter(QueryBuilders.queryStringQuery("uid:001"));

     * （11）嵌套的聚合
     * NestedBuilder nb= AggregationBuilders.nested("negsted_path").path("quests");
     * （12）反转嵌套
     * AggregationBuilders.reverseNested("res_negsted").path("kps ");
     * @throws Exception
     */


    /**
     * 过滤出province 为河南的数据
     * TODO
     * @throws Exception
     */
    @Test
    public void filterQuery()throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.aggregation(AggregationBuilders.filters("uid_filter", QueryBuilders.termQuery("province","河南")));
//        searchSourceBuilder.aggregation(AggregationBuilders.filters("uid_filter", QueryBuilders.termQuery("province","上海"),QueryBuilders.termQuery("province","北京")));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


    }

    /**
     * topHits获取聚合后的结果
     * fetchSource 中第一个是包含 第二个是排除
     *
     * @throws Exception
     */
    @Test
    public void testTopHits()throws Exception{


        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.terms("provinceagg").field("province").size(3)
                .subAggregation(AggregationBuilders.topHits("top_sales_hits").fetchSource(new String[]{"createDate", "price"},new String[]{})));

        searchRequest.source(searchSourceBuilder);


        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        searchResponse.getAggregations().get("top_sales_hits");
    }

    /**
     * 按日期间隔分组,年、月、日、小时、分钟、秒
     * dateHistogram日期直方图 📊
     * @throws Exception
     */
    @Test
    public void dateHistogramTest()throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        DateHistogramAggregationBuilder dhb= AggregationBuilders.dateHistogram("dh").dateHistogramInterval(DateHistogramInterval.WEEK).format("yyyy-MM-dd").field("createDate");
        searchSourceBuilder.aggregation(dhb);

        searchRequest.source(searchSourceBuilder);


        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);



    }


    /**
     * 嵌套聚合 TODO
     *
     * @throws Exception
     */
    @Test
    public void nestedTest()throws Exception{


        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        NestedAggregationBuilder nb= AggregationBuilders.nested("negsted_path","quests");

        TermsAggregationBuilder tb=  AggregationBuilders.terms("group_name").field("quests.province");
        nb.subAggregation(tb );
        searchSourceBuilder.aggregation(nb);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


    }



}
