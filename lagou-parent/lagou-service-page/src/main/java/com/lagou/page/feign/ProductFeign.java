package com.lagou.page.feign;

import com.lagou.common.pojo.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 自定义Feign接口，调用Product微服务的所有接口方法都在此进行定义
 */
@FeignClient(name = "lagou-service-product", fallback = ProductFeignFallBack.class)
public interface ProductFeign {

    /**
     * 通过商品id查询商品对象
     * @param id
     * @return
     */
    @GetMapping("/product/query/{id}")
    public Products queryById(@PathVariable Integer id);


    @GetMapping("/service/port")
    public String getPort();
}
