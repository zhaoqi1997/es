package com.zq.test;

import com.zq.Application;
import com.zq.dao.BookRepository;
import com.zq.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhaoqi
 * @version 1.8
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TestQuery {
    @Autowired
    private BookRepository bookRepository;
    @Test
    public void test(){
        List<Book> byNameAndContent = bookRepository.findByNameAndContent("余额", "李白");
        byNameAndContent.forEach(a-> System.out.println(a));

    }
}
