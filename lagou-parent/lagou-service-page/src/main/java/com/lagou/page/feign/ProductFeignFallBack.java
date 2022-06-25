package com.lagou.page.feign;

import com.lagou.common.pojo.Products;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignFallBack implements ProductFeign {
    @Override
    public Products queryById(Integer id) {
        return null;
    }

    @Override
    public String getPort() {
        return "-1";
    }
}
