package com.example.cusummer.consummerdemo.controller;

import com.example.cusummer.consummerdemo.dao.UserDao;
import com.example.cusummer.consummerdemo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("userConsummer")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
       return userDao.queryUserById(id);


    }

    @GetMapping("/tt")
    public List<User> queryById(List<Long> ids) {
       return userDao.queryUserByIdClomser(ids);
    }
}
