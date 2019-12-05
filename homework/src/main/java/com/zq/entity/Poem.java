package com.zq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zhaoqi
 * @version 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

@Document(indexName = "homework",type = "poem")
public class Poem {

    @Id
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Keyword)
    private String author;
    private String type;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;
    private String href;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String authordes;
    private String origin;
    private Cate cate;     //    表链接
    
    
}
