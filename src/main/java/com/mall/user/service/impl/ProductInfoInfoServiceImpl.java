package com.mall.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mall.user.pojo.OtherConfig;
import com.mall.user.pojo.ProductInfo;
import com.mall.user.repository.ProductInfoRepository;
import com.mall.user.service.ProductInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/29 21:09
 *
 * @author KevinHwang
 */
@Service
@Log4j2
public class ProductInfoInfoServiceImpl implements ProductInfoService {
    private final ProductInfoRepository productInfoRepository;
    private final OtherConfig otherConfig;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ProductInfoInfoServiceImpl(OtherConfig otherConfig, ProductInfoRepository productInfoRepository, RedisTemplate<String, Object> redisTemplate) {
        this.otherConfig = otherConfig;
        this.productInfoRepository = productInfoRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Page<ProductInfo> findProductInfo(Specification<ProductInfo> specification, int pageNum) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(pageNum, otherConfig.getPerPageRows(), Sort.by(orders));
        return productInfoRepository.findAll(specification, pageable);
    }

    @Override
    public Long count() {
        return productInfoRepository.count();
    }

    @Override
    @Transactional
    public void modifyStatus(Long id, int flag) {
        productInfoRepository.modifyStatus(id, flag);
    }

    @Override
    public List<ProductInfo> getOnSaleProduct() {
        return productInfoRepository.getOnSaleProduct();
    }

    @Override
    public ProductInfo getProductInfoById(Long id) {
        // 先查询redis
        String key = "prod" + id;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get(key);
            if (jsonObject != null) {
                return jsonObject.toJavaObject(ProductInfo.class);
            } else {
                return productInfoRepository.findById(id).orElse(null);
            }
        }
        return productInfoRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }
}
