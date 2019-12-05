package com.zq.service;

import com.zq.dao.CateDao;
import com.zq.entity.Cate;
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
@Transactional(propagation = Propagation.SUPPORTS)
    public class CateServiceImpl implements CateService{
    @Autowired
    private CateDao cateDao;
    @Override
    public List<Cate> select() {
        return cateDao.select();
    }
}
