package com.zq.test;

import com.zq.Application;
import com.zq.dao.BookRepository;
import com.zq.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author zhaoqi
 * @version 1.8
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TestSpringBootEs {
    @Autowired
    private BookRepository bookRepository;
    @Test
    public void test(){
        System.out.println(bookRepository);
    }

    /*
    * 添加索引和更新索引
    * id 存在 更新
    * id 不存在 添加
    * */
    @Test
    public void test1(){
        Book book = new Book();
        book.setId("22");
        book.setName("余额大小");
        book.setCreateDate(new Date());
        book.setAuthor("李白");
        book.setContent("李白是谁啊");
        bookRepository.save(book);
    }
    /*
    * 删除一条记录
    * */
    @Test
    public void delete(){
        bookRepository.delete(new Book().setId("21"));
    }
    /*
    * 查询所有
    * */
    @Test
    public void findAll(){
        Iterable<Book> all = bookRepository.findAll();
        all.forEach(a-> System.out.println(a));
    }

    /*
    * 查询排序
    * */
    @Test
    public void test2(){
        Iterable<Book> createDate = bookRepository.findAll(Sort.by(Sort.Order.desc("createDate")));
        createDate.forEach(a-> System.out.println(a));
    }
}

