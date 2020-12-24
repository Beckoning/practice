package com.pay.es;

import com.pay.es.provider.EsApplication;
import com.pay.es.provider.config.ElasticSearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.swing.text.Highlighter;
import java.util.Arrays;
import java.util.Objects;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
@Slf4j
public class EsQuery {

    RestHighLevelClient restHighLevelClient =  ElasticSearchClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";


    /**
     * term query不会将指定关键字分词，直接拿着关键词去分词库中匹配
     *
     * 查询时：一个字段对应多个值
     *
     * 这种查询适合keyword 、numeric、date
     * term查询keword字段 term不会分词，而keyword字段也不会分词。所以需要完全匹配
     * term查询text字段 term不会分词 而text字段会分词 因此term查询必须为text分词后的某一个词组
     *
     * @throws Exception
     */
    @Test
    public void termQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);
//        builder.query(QueryBuilders.termQuery("province","福建"));

        builder.query(QueryBuilders.termsQuery("province","河南","北京"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

       for (SearchHit searchHit : searchResponse.getHits().getHits()){
           System.out.println(searchHit.getSourceAsMap());
       }

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
     * 查询字段可以使用分词机器
     * @throws Exception
     */
    @Test
    public void matchQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.matchQuery("smsContent","14"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * 匹配词组
     * match_phrase 匹配keword和term匹配keword字段一样，必须一致才行
     * match_phrase匹配text字段：match_phrase的分词结果必须在text字段分词中都包含，并且顺序必须相同，而且必须都是连续的
     *
     * @throws Exception
     */
    @Test
    public void matchPhraseQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.matchPhraseQuery("smsContent","huang ming"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }
    /**
     * es查询 一个值可以对应多个字段
     *
     * @throws Exception
     */
    @Test
    public void multiMatchQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.multiMatchQuery("99","mobile","smsContent"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * 通过id查询数据（单个ID）
     * @throws Exception
     */
    @Test
    public void getByIdQueryTest() throws Exception{

        GetRequest getRequest = new GetRequest(index,type,"14");

        GetResponse getResponse =  restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);

        System.out.println(getResponse.getSourceAsMap());

    }


    /**
     * 通过多个Ids查询
     * @throws Exception
     */
    @Test
    public void getByIdsQueryTest() throws Exception{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.idsQuery().addIds("20"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }

    /**
     * 以什么开头的信息查询
     * 依据与分词器  索引倒排序
     * @throws Exception
     */
    @Test
    public void prefixQueryTest() throws Exception{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.prefixQuery("smsContent","视频中"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * 模糊查询
     * @throws Exception
     */
    @Test
    public void fuzzyQueryTest() throws Exception{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.fuzzyQuery("smsContent","视频").prefixLength(1));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * 通配查询
     * 类似于mysql中的like
     * @throws Exception
     */
    @Test
    public void wildcardQueryTest() throws Exception{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.wildcardQuery("smsContent","视频*"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * range 范围查询
     *
     * @throws Exception
     */
    @Test
    public void rangeQueryTest() throws Exception{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10);

        builder.query(QueryBuilders.rangeQuery("free").gte(5).lte(5));


        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * range 范围查询
     *
     * @throws Exception
     */
    @Test
    public void regexpQueryTest() throws Exception{
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(20);

        builder.query(QueryBuilders.regexpQuery("corpName","sd[a-z,0-9]{4}"));

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * 深度查询：存在缓存，数据查询不是实时性的
     * 先将查询数据缓存起来，然后通过scrollId查询已经缓存的数据  因此查询的数据不是实时的
     *  scroll查询
     *
     *  分页查询
     *    from+size在Es查询数据的方式
     *      第一步现将用户指定的关键词进行分词
     *      第二步将词汇去分词库中进行检索，得到多个文档的id
     *      第三步去各个分片中去拉去指定的数据，耗时较长
     *      第四步将数据根据score进行排序。耗时较长
     *      第五步根据from的值，将查询到的数据舍弃一部分
     *      第六步返回结果
     *    scroll+size在ES查询数据的方式（不适合实时查询）
     *      第一步现将用户指定的关键字进行分词
     *      第二步将词汇去分词库中进行检索，得到多个文档id
     *      第三步将检索出来的文档id存放到一个ES的上下文中
     *      第四步根据你指定的size的个数去ES中检索指定个数的数据，拿完数据的文档id 会从上下文中移除
     *      第五步如果需要下一页数据，直接去ES的上下文，找后续内容
     *      第六步循环第四步和第五步
     *
     *
     */
    @Test
    public void scrollQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        //指定scroll查询
        searchRequest.scroll(TimeValue.timeValueMinutes(5L));


        //执行查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
//        builder.from(0);
        builder.size(4);
        builder.sort("free", SortOrder.DESC);
        builder.query(QueryBuilders.matchAllQuery());


        searchRequest.source(builder);

        //获取返回的scrollId
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        String scrollId = searchResponse.getScrollId();
        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }
        System.out.println("scrollId："+scrollId);

        while(true){
            //循环创建SearchScrollRequest
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
            searchScrollRequest.scroll(TimeValue.timeValueMinutes(5L));
            SearchResponse response =  restHighLevelClient.scroll(searchScrollRequest,RequestOptions.DEFAULT);


            System.out.println("---------------------------------");
            if(Objects.nonNull(response.getHits()) && !CollectionUtils.isEmpty(Arrays.asList(response.getHits().getHits()))){
                for (SearchHit searchHit : response.getHits().getHits()){
                    System.out.println(searchHit.getSourceAsMap());
                }
            }else{
                break;
            }
        }

        //查询结束后 清除Scroll缓存
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);

        ClearScrollResponse clearScrollResponse =  restHighLevelClient.clearScroll(clearScrollRequest,RequestOptions.DEFAULT);

        System.out.println(clearScrollResponse.status());


    }


    /**
     * 删除符合查询条件的数据
     * 根据term 、match等查询方式去删除大量的数据（该方法比较耗时）
     * ps：
     *  合理方式：如果你要删除的内容，是index下的大部分数据，推荐使用一个全新的index，将要保留的文档内容，添加到全新的索引中去
     *
     * @throws Exception
     */
    @Test
    public void deleteByQueryTest() throws Exception{
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(index);
        deleteByQueryRequest.types(type);

        deleteByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds("19"));
        BulkByScrollResponse bulkByScrollResponse =  restHighLevelClient.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);

        System.out.println(bulkByScrollResponse.getStatus());
    }


    /**
     * bool复合查询
     * should 表示or符号
     * mustNot 表示!=符号
     * must 表示= 服务号
     * @throws Exception
     */
    @Test
    public void boolCompoundQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //等于或者等于
        queryBuilder.should(QueryBuilders.termQuery("province","河南"));
        queryBuilder.should(QueryBuilders.termQuery("province","北京"));
        //不等于
        queryBuilder.mustNot(QueryBuilders.termQuery("mobile","999911"));
        queryBuilder.mustNot(QueryBuilders.termQuery("mobile","999918"));
        queryBuilder.mustNot(QueryBuilders.termQuery("mobile","999916"));
        queryBuilder.mustNot(QueryBuilders.termQuery("mobile","999913"));
        //等于
        queryBuilder.must(QueryBuilders.termQuery("mobile","99991"));


        builder.query(queryBuilder);
        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }


    /**
     * boosting查询
     *  boosting查询可以帮助我们去影响查询后的score
     *    positive：只有匹配上的positive的查询的内容，才会被放到返回的结果集中
     *    negative：如果匹配上的positive并且也匹配上了nagative，就可以降低这样的文档score
     *    nagative_boost:指定系数，必须小于1.0
     *
     *  关于查询时，分数时如何计算的
     *    搜索的关键字在文档中出现的频次越高，分数就越高
     *    指定的文档内容越短，分数就越高
     *    我们在搜索时，指定的关键字也会被分词，这个被分词的内容，在分词库匹配的个数越多，分数越高
     *
     * @throws Exception
     */
    @Test
    public void boostingQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        QueryBuilder positiveQuery =  QueryBuilders.matchQuery("province","河南");
        QueryBuilder negativeQuery = QueryBuilders.matchQuery("smsContent","新加坡");

        BoostingQueryBuilder boostingQuery = QueryBuilders.boostingQuery(positiveQuery,negativeQuery).negativeBoost(0.2f);


        builder.query(boostingQuery);
        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }


    /**
     * query查询 根据你的查询条件，去计算文档的匹配度得到一个分数，并且根据这个分数进行排序，不会做缓存的
     * filter 根据你的查询条件去查询文档，不去计算分数，而且filter会对经常过滤的数据进行缓存
     * @throws Exception
     */
    @Test
    public void filterQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);


        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.size(5);
        builder.from(6);
        builder.sort("sendDate",SortOrder.DESC);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.filter(QueryBuilders.termQuery("province","河南"));
//        boolQuery.filter(QueryBuilders.rangeQuery("free").lte(5));
        boolQuery.filter(QueryBuilders.matchAllQuery());



        builder.query(boolQuery);
        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }


    /**
     * 高亮查询
     *
     * 高亮查询就是你用户输入的关键字，以一定的特殊样式展示给用户，让用户知道为什么这个结果被检索出来
     * 高亮展示的数据，本身就是一个文档的一个Field，单独将Field以highlight的形式返回给你
     * ES提供一个highlight属性，和query同级别
     *  fragment_size:指定高亮数据展示多个字符显示
     *  pre_tags:指定前缀标签，举个例子<font color='red'>
     *  post_tags:指定后缀标签 举个例子</font>
     *
     * @throws Exception
     */
    @Test
    public void highLigthQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.size(1);
        builder.from(0);
        builder.sort("sendDate",SortOrder.DESC);
        //指定查询条件
        builder.query(QueryBuilders.matchQuery("province","河南"));

        //指定高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("smsContent",10).preTags("<font color = 'red'>").postTags("</font>");
        highlightBuilder.fields();
        builder.highlighter(highlightBuilder);

        searchRequest.source(builder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
            System.out.println("-------------------------------"+"\n");
            System.out.println(hit.getHighlightFields());
        }
    }


    /**
     * 去重计算查询
     * ES的聚合查询和Mysql的聚合查询类型一样，ES的聚合查询相比Mysql要强大的多，ES提供的统计数据的方式多种多样
     *
     * @throws Exception
     */
    @Test
    public void cardlinalityQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();


        //去重复  使用聚合查询的方式
        builder.aggregation(AggregationBuilders.cardinality("agg").field("province"));
//        builder.aggregation(AggregationBuilders.max("agg").field("free"));

        searchRequest.source(builder);
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
//        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            System.out.println(hit.getSourceAsMap());
//            System.out.println("-------------------------------"+"\n");
//
//        }


        System.out.println(((ParsedMax)searchResponse.getAggregations().get("agg")).getValue());
    }


    /**
     * 统计一定范围内出现的文档个数，比如：针对一个Field的值0-100，100-200，200-300之间文档出现的个数分别是多少。
     *
     * 范围统计可以针对普通的数值，针对时间类型，针对ip类型都可以做相应的统计
     * range date_range  ip_range
     * @throws Exception
     */
    @Test
    public void rangePolymerizeQueryTest() throws Exception{
//         index = "sms-record-index";
//         type = "sms-record-type";
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();


        //指定聚合查询方式（数字）
//        builder.aggregation(AggregationBuilders.range("agg").field("free")
//                .addRange(10,20)
//                .addUnboundedFrom(1)
//                .addUnboundedTo(100));
        //时间
//        builder.aggregation(AggregationBuilders.dateRange("agg").field("createDate").format("yyy-MM-dd")
//                .addRange("2020-12-07","2020-12-08")
//                .addUnboundedFrom("2020-12-08")
//                .addUnboundedTo("2020-12-07"));

        //ip聚合  text 字段类型不能进行分组  如果text字段需要进行分组 需要修改参数类型将ipAddr修改为ipAddr.keyword
        //查询ip范围时 字段保存类型使用ip
        // .startObject("ipAddr")
        //     .field("type","ip")
        // .endObject()
        builder.aggregation(AggregationBuilders.ipRange("agg").field("ipAddr")
                .addRange("0.0.0.1","127.0.0.1")
                .addUnboundedFrom("127.0.0.1")
                .addUnboundedTo("127.0.0.2"));

        searchRequest.source(builder);
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);


        Range range = searchResponse.getAggregations().get("agg");
        for (Range.Bucket bucket : range.getBuckets()){
            System.out.println(bucket.getFromAsString());
            System.out.println(bucket.getToAsString());
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());

            System.out.println("---------------");

        }

    }


    /**
     * 统计聚合查询
     * 他可以帮你查询指定Field的最大值、最小值、平均值、平方和。。。。。
     * 使用extended_stats
     * @throws Exception
     */
    @Test
    public void extendedStatsQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();


        builder.aggregation(AggregationBuilders.extendedStats("agg").field("free"));


        searchRequest.source(builder);
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);


        ExtendedStats extendedStats = searchResponse.getAggregations().get("agg");

        System.out.println(extendedStats.getMaxAsString());
        System.out.println(extendedStats.getSumAsString());


    }


    /**
     * 查询按照城市分组
     * @throws Exception
     */
    @Test
    public void cityGroupQueryTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder builder = new SearchSourceBuilder();


        builder.aggregation(AggregationBuilders.terms("agg").field("province")
                .subAggregation(AggregationBuilders.avg("avg").field("free")));


        searchRequest.source(builder);
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);


        ParsedStringTerms parsedStringTerms  = searchResponse.getAggregations().get("agg");

        for(Terms.Bucket parsedBucket: parsedStringTerms.getBuckets()){
//            System.out.println(((ParsedStringTerms.ParsedBucket) parsedBucket).getKeyAsNumber());
            System.out.println(parsedBucket.getKeyAsString() + "————" +parsedBucket.getDocCount()+"----"+((ParsedAvg)parsedBucket.getAggregations().get("avg")).getValue());

        }

    }

}
