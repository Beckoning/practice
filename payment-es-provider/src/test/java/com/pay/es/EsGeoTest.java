package com.pay.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.es.provider.EsApplication;
import com.pay.es.provider.config.ElasticSearchClient;
import com.pay.es.provider.model.GeoMap;
import com.pay.es.provider.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.geobounds.ParsedGeoBounds;
import org.elasticsearch.search.aggregations.metrics.geocentroid.ParsedGeoCentroid;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
@Slf4j
public class EsGeoTest {


    RestHighLevelClient restHighLevelClient =  ElasticSearchClient.getClient();
    String index = "museums";
    String type = "_doc";

    /**
     * 新增索引
     * @throws Exception
     */
    @Test
    public void createMuseumsIndexTest() throws Exception{

        //1、准备索引的settings
        Settings.Builder settings =Settings.builder()
                .put("number_of_shards","5")
                .put("number_of_replicas","1");


        //2、准备索引结构的mappings
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("id")
                            .field("type","keyword")
                        .endObject()
                        .startObject("name")
                            .field("type","text")
                        .endObject()
                        .startObject("city")
                            .field("type","text")
                        .endObject()
                        .startObject("location")
                            .field("type","geo_point")
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

        bulkRequest.add(new IndexRequest(index, type, 1+"").source(objectMapper.writeValueAsString(new GeoMap("" + 1,"天安门","北京",new Location(116.403855,39.91517))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 2+"").source(objectMapper.writeValueAsString(new GeoMap("" + 2,"典籍馆","北京",new Location(116.401052,39.921479))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 3+"").source(objectMapper.writeValueAsString(new GeoMap("" + 3,"午门","北京",new Location(116.403639,39.920013))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 4+"").source(objectMapper.writeValueAsString(new GeoMap("" + 4,"太和门","北京",new Location(116.403478,39.921811))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 5+"").source(objectMapper.writeValueAsString(new GeoMap("" + 5,"东华门","北京",new Location(116.407736,39.921368))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 6+"").source(objectMapper.writeValueAsString(new GeoMap("" + 6,"慈宁宫","北京",new Location(116.400837,39.92538))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 7+"").source(objectMapper.writeValueAsString(new GeoMap("" + 7,"什刹海公园","北京",new Location(116.391135,39.947924))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 8+"").source(objectMapper.writeValueAsString(new GeoMap("" + 8,"御花园","北京",new Location(116.4031,39.927676))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 9+"").source(objectMapper.writeValueAsString(new GeoMap("" + 9,"英华殿","北京",new Location(116.399974,39.928036))), XContentType.JSON));



        bulkRequest.add(new IndexRequest(index, type, 10+"").source(objectMapper.writeValueAsString(new GeoMap("" + 10,"九溪十八涧","杭州",new Location(120.113876,30.217098))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 11+"").source(objectMapper.writeValueAsString(new GeoMap("" + 11,"灵隐景区","杭州",new Location(120.106977,30.246429))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 12+"").source(objectMapper.writeValueAsString(new GeoMap("" + 12,"法净禅寺","杭州",new Location(120.108846,30.240439))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 13+"").source(objectMapper.writeValueAsString(new GeoMap("" + 13,"雷峰塔","杭州",new Location(120.155198,30.23657))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 14+"").source(objectMapper.writeValueAsString(new GeoMap("" + 14,"杨公堤","杭州",new Location(120.140969,30.238691))), XContentType.JSON));
        bulkRequest.add(new IndexRequest(index, type, 15+"").source(objectMapper.writeValueAsString(new GeoMap("" + 15,"断桥残雪","杭州",new Location(120.157713,30.264148))), XContentType.JSON));



        BulkResponse bulkResponse =  restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());

    }


    /**
     * geoDistance 传入一个经纬度和长度，以传入的经纬度为中心查询附近n米内的地点
     * @throws Exception
     */
    @Test
    public void geoDistanceTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.geoDistanceQuery("location").point(39.925795,116.403352).distance("250"));
        searchRequest.types(type);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * geo_bounding_box
     * 选取两个坐标点，组成一个矩形  查询该矩形内的所有复合的坐标
     * @throws Exception
     */
    @Test
    public void geoBoundsTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        GeoPoint topLeft = new GeoPoint(39.948795,116.36);

        GeoPoint bottomRight = new GeoPoint(39.920248,116.408581);


        searchSourceBuilder.query(QueryBuilders.geoBoundingBoxQuery("location").setCorners(topLeft,bottomRight));
        searchRequest.types(type);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }


    /**
     * geoPolygon
     * 获取多个坐标  确定一个多边形 获取多边形内的全部数据
     *
     * @throws Exception
     */
    @Test
    public void geoPolygonTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        GeoPoint geoPoint1 = new GeoPoint(39.948795,116.36);

        GeoPoint geoPoint2 = new GeoPoint(39.920248,116.408581);
        GeoPoint geoPoint3 = new GeoPoint(39.920912,116.417636);

        List<GeoPoint> points = new ArrayList<>();
        points.add(geoPoint1);
        points.add(geoPoint2);
        points.add(geoPoint3);

        searchSourceBuilder.query(QueryBuilders.geoPolygonQuery("location",points));
        searchRequest.types(type);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }

    }

    /**
     * 地理边界聚合
     * 查询所有符合关键词的查询 ，并获取所有符合关键词的坐标的边界框度量集合， 即获的两个坐标组合成的矩形 包含华的地名的坐标都在里面
     * @throws Exception
     */
    @Test
    public void aggGeoBoundsTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        searchSourceBuilder.query(QueryBuilders.matchQuery("name","华"));
        searchSourceBuilder.aggregation(AggregationBuilders.geoBounds("geo_bounds").field("location"));

        searchRequest.types(type);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }
        ParsedGeoBounds parsedGeoBounds =  ((ParsedGeoBounds)searchResponse.getAggregations().get("geo_bounds"));
        System.out.println(parsedGeoBounds.getName() + "topLeft: lat = "+ parsedGeoBounds.topLeft().getLat()+"lon = "+parsedGeoBounds.topLeft().getLon());
        System.out.println(parsedGeoBounds.getName() + "bottomRight: lat = "+ parsedGeoBounds.bottomRight().getLat()+"lon = "+parsedGeoBounds.bottomRight().getLon());

    }


    /**
     * 获取多个地点的中心位置
     *
     * @throws Exception
     */
    @Test
    public void aggGeoCentroidTest() throws Exception{

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


//        searchSourceBuilder.query(QueryBuilders.matchQuery("name","华"));
        //查询多个地点的中心地点
//        searchSourceBuilder.aggregation(AggregationBuilders.geoCentroid("geo_centroid").field("location"));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("cities").field("city.keyword").subAggregation(AggregationBuilders.geoCentroid("geo_centroid").field("location")));


        searchRequest.types(type);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }
        // 获取多个地点的中心位置
//        ParsedGeoCentroid parsedGeoCentroid =  ((ParsedGeoCentroid)searchResponse.getAggregations().get("geo_centroid"));
//        System.out.println(parsedGeoCentroid.getName() + "centroid: lat = "+ parsedGeoCentroid.centroid().getLat()+"lon = "+parsedGeoCentroid.centroid().getLon());


        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsMap());
        }
        ParsedStringTerms parsedStringTerms =  ((ParsedStringTerms)searchResponse.getAggregations().get("cities"));

        //TODO 先按照城市分组，然后统计每个城市下地点的中心  未实现
        for (Terms.Bucket bucket: parsedStringTerms.getBuckets()){
            ParsedGeoCentroid parsedGeoCentroid =  (ParsedGeoCentroid)bucket.getAggregations().get("geo_centroid");
            System.out.println(parsedGeoCentroid.getName() + "centroid: lat = "+ parsedGeoCentroid.centroid().getLat()+"lon = "+parsedGeoCentroid.centroid().getLon());
        }
    }






}
