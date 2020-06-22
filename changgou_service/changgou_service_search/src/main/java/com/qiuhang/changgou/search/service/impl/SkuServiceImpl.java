package com.qiuhang.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qiuhang.changgou.entity.Result;
import com.qiuhang.changgou.goods.feign.SkuFeign;
import com.qiuhang.changgou.goods.pojo.Sku;
import com.qiuhang.changgou.search.dao.SkuEsMappper;
import com.qiuhang.changgou.search.pojo.SkuInfo;
import com.qiuhang.changgou.search.service.SkuService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ProjectName: changgou_parent
 * @Package: com.qiuhang.changgou.service.impl
 * @ClassName: SkuServiceImpl
 * @Author: qiuhang
 * @Description: ${description}
 * @Date: 2020/6/1 10:02
 * @Version: 1.0
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMappper skuEsMappper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    /**
     * 导入索引库
     */
    @Override
    public void importData() {
        Result<List<Sku>> all = skuFeign.findAll();
        List<SkuInfo> skuInfoList= JSON.parseArray(JSON.toJSONString(all.getData()),SkuInfo.class);
        for (SkuInfo skuInfo:skuInfoList){
            Map<String,Object> map =JSON.parseObject(skuInfo.getSpec(),Map.class);
            skuInfo.setSpecMap(map);
        }
        skuEsMappper.saveAll(skuInfoList);
    }

    /**
     * 多条件搜索
      * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {

        //搜索条件构建对象
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        if(searchMap!=null){
            String keyword=searchMap.get("keyword");
            if (!StringUtils.isEmpty(keyword)){
                bqb.must(QueryBuilders.matchPhraseQuery("name",keyword));

            }
        }
        //排序条件
        FieldSortBuilder fsb = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
        //分页条件
        Integer pageindex=Integer.parseInt(searchMap.get("pageindex"));
        Integer pageSize=Integer.parseInt(searchMap.get("pageSize"));
        pageindex = pageindex == 0 ? 1 : pageindex;
        pageSize = pageSize == 0 ? 10 : pageSize;
        Pageable pageable = PageRequest.of(pageindex - 1, pageSize);
        //条件查询
        Map<String, Object> resultMap = searchPageList(bqb, fsb, pageable);
        //分组查询
        List categoryList = searchCategoryList(bqb, fsb, pageable);

        List brandList = searchBrandList(bqb, fsb, pageable);
        resultMap.put("categoryList",categoryList);
        resultMap.put("brandList",brandList);


        searchSpecList(bqb, fsb, pageable);
        return resultMap;
    }

    /**
     * 分页条件查询
     * @param bqb
     * @param fsb
     * @param pageable
     * @return
     */
    public Map<String, Object> searchPageList(BoolQueryBuilder bqb, FieldSortBuilder fsb, Pageable pageable) {
        //构建查询
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(bqb)
                .withSort(fsb)
                .withPageable(pageable)
                .build();

        /****
        /****
         * 1 搜索条件封装
         * 2 返回类型
         */
        AggregatedPage<SkuInfo> skuInfos =
                elasticsearchTemplate.queryForPage(query, SkuInfo.class);


        //总记录数
        long totalElements = skuInfos.getTotalElements();
        //总页数
        int totalPages = skuInfos.getTotalPages();
        //数据结果集
        List<SkuInfo> content = skuInfos.getContent();


        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("rows",content);
        resultMap.put("total",totalElements);
        resultMap.put("totalPages",totalPages);
        return resultMap;
    }

    /**
     * 分类分组查询
     * @param bqb
     * @param fsb
     * @param pageable
     * @return
     */
    public List searchCategoryList(BoolQueryBuilder bqb, FieldSortBuilder fsb, Pageable pageable) {
        /**
         * 分组查询分类集合
         * 取别名
         * 添加一个聚合操作
         */
        TermsAggregationBuilder field = AggregationBuilders.terms("skuCategory").field("categoryName");
        SearchQuery query2 =new NativeSearchQueryBuilder()
                .addAggregation(field)
                .withQuery(bqb)
                .withSort(fsb)
                .withPageable(pageable)
                .build();
        AggregatedPage<SkuInfo> skuInfos1 = elasticsearchTemplate.queryForPage(query2, SkuInfo.class);
        /**
         * 获取分组数据
         */
        StringTerms stringTerms = skuInfos1.getAggregations().get("skuCategory");
        List categoryList=new ArrayList();
        for (StringTerms.Bucket bucket:stringTerms.getBuckets()){
            String categoryName = bucket.getKeyAsString();
            categoryList.add(categoryName);
        }
        return categoryList;
    }

    /**
     * 品牌分组查询
     * @param bqb
     * @param fsb
     * @param pageable
     * @return
     */
    public List searchBrandList(BoolQueryBuilder bqb, FieldSortBuilder fsb, Pageable pageable) {
        /**
         * 分组查询分类集合
         * 取别名
         * 添加一个聚合操作
         */
        TermsAggregationBuilder field = AggregationBuilders.terms("brandName").field("brandName");
        SearchQuery query2 =new NativeSearchQueryBuilder()
                .addAggregation(field)
                .withQuery(bqb)
                .withSort(fsb)
                .withPageable(pageable)
                .build();
        AggregatedPage<SkuInfo> skuInfos1 = elasticsearchTemplate.queryForPage(query2, SkuInfo.class);
        /**
         * 获取分组数据
         */
        StringTerms stringTerms = skuInfos1.getAggregations().get("brandName");
        List brandList=new ArrayList();
        for (StringTerms.Bucket bucket:stringTerms.getBuckets()){
            String brandName = bucket.getKeyAsString();
            brandList.add(brandName);
        }
        return brandList;
    }

    /**
     * 筛选条件分组查询
     * @param bqb
     * @param fsb
     * @param pageable
     * @return
     */
    public void searchSpecList(BoolQueryBuilder bqb, FieldSortBuilder fsb, Pageable pageable) {
        /**
         * 分组查询分类集合
         * 取别名
         * 添加一个聚合操作
         */
        TermsAggregationBuilder field = AggregationBuilders.terms("skuSpec").field("spec.keyword");
        SearchQuery query2 =new NativeSearchQueryBuilder()
                .addAggregation(field)
                .withQuery(bqb)
                .withSort(fsb)
                .withPageable(pageable)
                .build();
        AggregatedPage<SkuInfo> skuInfos1 = elasticsearchTemplate.queryForPage(query2, SkuInfo.class);
        /**
         * 获取分组数据
         */
        Map<String, Set<String>> allSpec=new HashMap<>();
        StringTerms stringTerms = skuInfos1.getAggregations().get("skuSpec");
        List specList=new ArrayList();
        for (StringTerms.Bucket bucket:stringTerms.getBuckets()){
            String skuSpec = bucket.getKeyAsString();
            Map<String,String> map = JSONObject.parseObject(skuSpec, Map.class);
           for (Map.Entry<String,String> entry:map.entrySet()){
               String key=entry.getKey();
               String value=entry.getValue();
               Set<String> specSet=new HashSet<>();
               for (Map)

           }
        }

    }
}
