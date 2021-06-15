package com.mall.user.service.impl;

import com.mall.user.pojo.OtherConfig;
import com.mall.user.pojo.ProductInfo;
import com.mall.user.repository.ProductInfoRepository;
import com.mall.user.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
public class ProductInfoInfoServiceImpl implements ProductInfoService {
    private final ProductInfoRepository productInfoRepository;
    private final OtherConfig otherConfig;

    @Autowired
    public ProductInfoInfoServiceImpl(ProductInfoRepository productInfoRepository, OtherConfig otherConfig) {
        this.productInfoRepository = productInfoRepository;
        this.otherConfig = otherConfig;
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
        return productInfoRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }

    @Override
    public List<ProductInfo> getProductInfo() {
        return productInfoRepository.findAll();
    }
}
