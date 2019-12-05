package com.zq.dao;

import com.zq.entity.Poem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoemDao {
    public void insert(Poem poem);
    public void delete(String id);
    public void update(Poem poem);

    public List<Poem> selectAll(
            @Param("id") String id,
            @Param("start") Integer start,
            @Param("size") Integer size
    );

    public List<Poem> select();
    public Integer selectSize();
}
