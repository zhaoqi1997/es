package com.zq.controller;

import com.zq.entity.Poem;
import com.zq.service.PoemService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhaoqi
 * @version 1.8
 */
@RestController
@RequestMapping("poem")
public class PoemController {
    @Autowired
    private PoemService poemService;

    @RequestMapping("selectById")
    public void selectById(String id){
        List<Poem> poems = poemService.selectAll(id,1,1);
        Poem poem=poems.get(0);
    }
    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(Integer rows,Integer page){
        List<Poem> poems = poemService.selectAll(null, rows, page);
        Map<String,Object> map = new HashMap<>();
        Integer integer = poemService.selectSize();
//        总页数
        Integer total;
//        总数据条数
        map.put("records",integer);
//        当前页的数据集
        map.put("rows",poems);

        map.put("total",total=integer%rows==0?(integer/rows):integer/rows+1);
//        当前页数
        map.put("page",page);
        return map;
    }

    @RequestMapping("opera")
    public void opera(String oper,Poem poem){
//        使用commons-codec 或者 是commons-lang3
        if (StringUtils.equals("del",oper)){
            poemService.delete(poem.getId());
        }else if (StringUtils.equals("add",oper)){
            poemService.insert(poem.setId(UUID.randomUUID().toString()).setHref("www.baidu.com"));
        }else if (StringUtils.equals("edit",oper)){
            poemService.update(poem);
        }
    }
}
