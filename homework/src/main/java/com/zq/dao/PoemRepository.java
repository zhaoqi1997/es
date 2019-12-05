package com.zq.dao;

import com.zq.entity.Poem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PoemRepository extends ElasticsearchRepository<Poem,String> {
}
