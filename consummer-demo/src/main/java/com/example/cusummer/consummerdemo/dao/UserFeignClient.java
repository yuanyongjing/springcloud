package com.example.cusummer.consummerdemo.dao;

import com.example.cusummer.consummerdemo.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserFeignClient {
    @GetMapping("/user/{id}")
    User queryById(@PathVariable("id") Long id);
}
