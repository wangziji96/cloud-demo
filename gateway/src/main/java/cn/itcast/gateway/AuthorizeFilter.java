package cn.itcast.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author wzj
 * @Date 2022/5/19 20:20
 */
//@Order(-1)//过滤器可以有多个，这个注解决定那个过滤器先执行，值越小优先级越高。定义过滤器的优先级除了注解，还可以在类上实现Ordered接口，两种选一个即可
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();
        //2.获取参数中的authorization 参数
        String auth = params.getFirst("authorization");
        //3.判断参数值是否等于admin
        if ("admin".equals(auth)){
            //4.是，放行
            return chain.filter(exchange);
        }

        //5.否，拦截
        //5.1 设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        //5.2 拦截请求
        return exchange.getResponse().setComplete();
    }

    //定义过滤器优先级
    @Override
    public int getOrder() {
        return -1;
    }
}
