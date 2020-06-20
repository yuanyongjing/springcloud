package com.example.cusummer.consummerdemo.dao;

import com.example.cusummer.consummerdemo.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDao {

    // 必须导入org.springframework.cloud.client.discovery.DiscoveryClient
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  UserFeignClient userFeignClient;



    public User queryUserById(Long id){
        //1、 根据user-service获取user-serivce 的集群的信息
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        //2、由于我们没有集群，只有一个，所以直接取出第一个
        ServiceInstance instance = instances.get(0);
        //3、拼接URL
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/user/"+id;

        // 使用restTemplate发起请求
        ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class);
        // 获取返回对象
        User user = entity.getBody();
        return user;
    }

    /**
     * 用集群的，就是有多个服务提供者，会通过负载均衡自动选取服务（根据里面的算法）
     * @param ids
     * @return
     */
    public List<User> queryUserByIdClomser(List<Long> ids){
        List<User> users = new ArrayList<>();
        // 地址直接写服务名称即可
        String baseUrl = "http://user-service/user/";
        ids.forEach(id -> {
            // 我们测试多次查询，
            users.add(this.restTemplate.getForObject(baseUrl + id, User.class));
            // 每次间隔500毫秒
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return users;
    }

    @HystrixCommand(fallbackMethod = "queryUserByIdFallback")
    public User queryUserById11(Long id){
        long begin = System.currentTimeMillis();
        String url = "http://user-service/user/" + id;
        User user = this.restTemplate.getForObject(url, User.class);
        long end = System.currentTimeMillis();
        // 记录访问用时：
        System.out.println("访问用时：{}"+ (end - begin));
        return user;
    }

    public User queryUserByIdFallback(Long id){
        User user = new User();
        user.setId(id);
        user.setName("用户信息查询出现异常！");
        return user;
    }

}
