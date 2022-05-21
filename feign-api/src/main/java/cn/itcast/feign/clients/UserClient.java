package cn.itcast.feign.clients;


import cn.itcast.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author wzj
 * @Date 2022/5/17 17:00
 */
@FeignClient("userservice")//写上服务名
public interface UserClient {

    @GetMapping("/user/{id}")//定义请求路径
    User findById(@PathVariable("id") Long id);//将查询到的数据封装到User里面

}
