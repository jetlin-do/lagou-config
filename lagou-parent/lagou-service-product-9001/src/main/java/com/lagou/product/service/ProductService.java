package com.lagou.product.service;

import com.lagou.common.pojo.Products;

public interface ProductService {

    /**
     * 根据商品ID查询商品对象
     */
    Products queryById(Integer id);

}
