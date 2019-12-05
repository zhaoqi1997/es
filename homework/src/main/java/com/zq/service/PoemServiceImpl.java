package com.zq.service;

import com.zq.dao.PoemDao;
import com.zq.entity.Poem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhaoqi
 * @version 1.8
 */
@Service
@Transactional
public class PoemServiceImpl  implements PoemService{
    @Autowired
    private PoemDao poemDao;
    @Override
    public void insert(Poem poem) {
        poemDao.insert(poem);
    }

    @Override
    public void delete(String id) {
        poemDao.delete(id);
    }

    @Override
    public void update(Poem poem) {
        poemDao.update(poem);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Poem> selectAll(String id,Integer rows,Integer page) {
        Integer start= (page-1)*rows;
        return poemDao.selectAll(id,start,rows);
    }

    @Override
    public Integer selectSize() {
        return poemDao.selectSize();
    }

    @Override
    public List<Poem> select() {
        return poemDao.select();
    }
}
