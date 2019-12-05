package com.zq.controller;

import com.zq.dao.PoemRepository;
import com.zq.entity.Poem;
import com.zq.service.PoemService;
import com.zq.util.TrimUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoqi
 * @version 1.8
 */
@RestController
@RequestMapping("es")
public class EsController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PoemRepository poemRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PoemService poemService;
    @RequestMapping("build")
    public void build(){
        List<Poem> poems = poemService.select();
        poemRepository.saveAll(poems);
    }
    @RequestMapping("clear")
    public void clear(){
        poemRepository.deleteAll();
    }
    @RequestMapping("search")
    public List<Poem> search(String word){
        word = TrimUtil.trim(word);
        ZSetOperations<String, String> sh = stringRedisTemplate.opsForZSet();
        Double keyword = sh.score("keyword", word);
        if (keyword!=null){
            Double core = keyword+0.1;
            String format = String.format("%.2f", core);
            sh.add("keyword",word,Double.valueOf(format));
        } else {
            sh.add("keyword",word,0.1);
        }
        //设置高亮对象
        HighlightBuilder.Field a = new HighlightBuilder.Field("*")
                .preTags("<span style='color:red'>")
                .postTags("</span>")
                .requireFieldMatch(false);  //去除默认选项
        //设置查询条件对象
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(word,"name","content","authordes"))
                .withPageable(PageRequest.of(0,10))
                .withHighlightFields(a)
                .build();
        //融合条件对象
        List<Poem> list = new ArrayList<Poem>();
        AggregatedPage<Poem> products = elasticsearchTemplate.queryForPage(searchQuery, Poem.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHit[] hits = searchResponse.getHits().getHits();

                for (SearchHit hit : hits) {
                    Poem poem = new Poem();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    poem.setId(sourceAsMap.get("id").toString());
                    poem.setName(sourceAsMap.get("name").toString());
                    poem.setAuthor(sourceAsMap.get("author").toString());
                    poem.setType(sourceAsMap.get("type").toString());
                    poem.setContent(sourceAsMap.get("content").toString());
                    poem.setAuthordes(sourceAsMap.get("authordes").toString());
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if(highlightFields.containsKey("name")){
                        poem.setName(highlightFields.get("name").fragments()[0].toString());
                    }
                    if(highlightFields.containsKey("content")){
                        poem.setContent(highlightFields.get("content").fragments()[0].toString());
                    }
                    if(highlightFields.containsKey("authordes")){
                        poem.setAuthordes(highlightFields.get("authordes").fragments()[0].toString());
                    }
                    list.add(poem);
                }
                return new AggregatedPageImpl<T>((List<T>)list);
            }
        });
        return list;
    }

    @RequestMapping("insert")
    public void insert(String word, HttpServletRequest request) throws IOException {
        word = TrimUtil.trim(word);
        System.out.println(word);
        String realPath = request.getServletContext().getRealPath("/back/word.txt");
        BufferedReader reader = new BufferedReader(new FileReader(realPath));
        List<String> list  =new ArrayList<>();
        while (true){
            String s = reader.readLine();
            if (word.equals(s)) return;
            if (s==null) break;
        }
        FileOutputStream out = new FileOutputStream(realPath,true);
        byte[] bytes = word.getBytes();
        out.write(bytes);
        out.write("\r".getBytes());
        out.close();

    }
    @RequestMapping("find")
    public List<String> find(HttpServletRequest request) throws IOException {
        String realPath = request.getServletContext().getRealPath("/back/word.txt");
        BufferedReader reader = new BufferedReader(new FileReader(realPath));
        List<String> list  =new ArrayList<>();
        while (true){
            String s = reader.readLine();
            if (s==null) break;
            list.add(s);
        }
        reader.close();
        return list;
    }

    @RequestMapping("delete")
    public void delete(String word,HttpServletRequest request) throws IOException {
        word = TrimUtil.trim(word);
        String realPath = request.getServletContext().getRealPath("/back/word.txt");
        BufferedReader reader = new BufferedReader(new FileReader(realPath));
        List<String> list  =new ArrayList<>();
        while (true){
            String s = reader.readLine();
            if (s==null) break;
            list.add(s);
        }
        reader.close();
        list.remove(word);
        FileOutputStream out = new FileOutputStream(realPath,false);
        for(String s : list){
            byte[] bytes = s.getBytes();
            out.write(bytes);
            out.write("\r".getBytes());
        }
        out.close();
    }
}
