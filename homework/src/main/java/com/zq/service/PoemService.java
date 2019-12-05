package com.zq.service;

import com.zq.entity.Poem;

import java.util.List;

public interface PoemService {
    public void insert(Poem poem);
    public void delete(String id);
    public void update(Poem poem);
    public List<Poem> selectAll(String id,Integer rows,Integer page);
    public Integer selectSize();
    public List<Poem> select();
}
