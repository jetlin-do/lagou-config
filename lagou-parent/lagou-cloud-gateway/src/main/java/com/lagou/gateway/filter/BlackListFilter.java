package com.lagou.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 通常情况下进行网关自定义过滤器时，需要实现两个接口GlobalFilter, Ordered(指定过滤器的执行顺序)
 */
@Component
public class BlackListFilter implements GlobalFilter, Ordered {

    /**
     * 加载黑名单列表
     * MySQL -> Redis -> 加载到内存中
     */
    private static List<String> blackList = new ArrayList<>();

    static {
        blackList.add("0:0:0:0:0:0:0:1"); // 模拟本机地址
        // 将本机地址加入黑名单中
        blackList.add("127.0.0.1");
    }

    /**
     * GlobalFilter, 过滤器的核心逻辑
     * @param exchange  ：封装了request和responses上下文
     * @param chain ：表示路由器网关的调用链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("....BlackListFilter....");

        // 获取请求和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 获取来访者的IP地址
        String clientIP = request.getRemoteAddress().getHostString();
        // 判断是否在黑名单中
        if (blackList.contains(clientIP)) {
            // 拒绝访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            String data = "request be denied";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        return chain.filter(exchange);
    }

    /**
     * Ordered，定义过滤的顺序，getOrder()返回值的大小决定了过滤器执行的优先级，越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
