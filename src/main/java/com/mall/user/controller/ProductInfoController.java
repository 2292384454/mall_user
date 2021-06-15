package com.mall.user.controller;

import com.mall.user.pojo.ProductInfo;
import com.mall.user.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/9 16:26
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/product")
public class ProductInfoController {
    private final ProductInfoService productInfoService;

    @Autowired
    public ProductInfoController(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }

    /**
     * 调用业务接口ProductInfoService中的getProductInfo方法获取所有商品数据，并将其转为JSON格式，再发送到前端页面.
     */
    @RequestMapping(value = "/getProduct", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<ProductInfo> getProduct() {
        return productInfoService.getProductInfo();
    }

    @RequestMapping("/getProductById/{id}")
    @ResponseBody
    public ProductInfo getProductInfoById(@PathVariable("id") Long id) {
        return productInfoService.getProductInfoById(id);
    }
}
