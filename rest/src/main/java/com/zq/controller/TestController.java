package com.zq.controller;

import com.zq.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoqi
 * @version 1.8
 */
@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping("show")
    public void show(){
        System.out.println("hahhah1");
    }

    @PutMapping("insert")
    public String insert(User user){
        System.out.println(user);
        return user+"插入成功";
    }

    @DeleteMapping("delete")
    public String delete(@PathVariable  String id){
        System.out.println(id);
        return id+"删除成功！~";
    }

    @PostMapping("update")
    public String update(@RequestBody User user){
        System.out.println(user);
        return user+"更新成功";
    }

    @GetMapping("select/{id}")
    public User select(@PathVariable String id){
        System.out.println(id);
        return new User("1",18,100.0,new Date());
    }

    @GetMapping("selectAll")
    public List<User> selectAll(){
        List<User> list = new ArrayList<>();
        list.add(new User("1",18,100.0,new Date()));
        list.add(new User("2",19,101.0,new Date()));
        return  list;
    }
}
