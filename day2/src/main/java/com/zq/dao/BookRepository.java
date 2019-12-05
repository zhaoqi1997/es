package com.zq.dao;

import com.zq.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

//扫描的ElasticsearchRepository的接口 自定义接口是为了扩展方法
public interface BookRepository extends ElasticsearchRepository<Book,String> {
    //自定义方法声明 根据nand content
    List<Book> findByNameAndContent(String name,String content);

    //自定义方法声明 根据name or content
    List<Book> findByNameOrContent(String nameKeyword,String contentKeyword);

    //自定义方法 等值查询
}
