package com.zq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zhaoqi
 * @version 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "dangdang",type = "book")
public class Book {
    @Id   //用来将对象中id和Es中_id映射
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Date)
    private Date createDate;
    @Field(type=FieldType.Keyword)
    private String author;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;
}
