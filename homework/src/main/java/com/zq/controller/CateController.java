package com.zq.controller;

import com.zq.entity.Cate;
import com.zq.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author zhaoqi
 * @version 1.8
 */
@RestController
@RequestMapping("cate")
public class CateController {
    @Autowired
    private CateService cateService ;
    @RequestMapping("select")
    public void select(HttpServletResponse response) throws IOException {
        List<Cate> cates = cateService.select();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        StringBuffer res = new StringBuffer( "<select>");
        cates.forEach(cate->{
            res.append("<option value='"+cate.getId()+"'>"+cate.getName()+"</option>");
        });
        res.append("</select>");
        writer.print(res);
        writer.close();
    }
}
