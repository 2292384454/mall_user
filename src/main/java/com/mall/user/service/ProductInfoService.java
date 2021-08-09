package com.mall.user.service;

import com.mall.user.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/29 20:42
 *
 * @author KevinHwang
 */
public interface ProductInfoService {
    /**
     * 按条件分页显示商品
     */
    Page<ProductInfo> findProductInfo(Specification<ProductInfo> specification, int pageNum);

    /**
     * 商品计数
     */
    Long count();

    /**
     * 更新商品状态
     */
    void modifyStatus(Long id, int flag);

    /**
     * 获取在售商品列表
     */
    List<ProductInfo> getOnSaleProduct();

    /**
     * 根据商品id获取商品对象
     */
    ProductInfo getProductInfoById(Long id);

    /**
     * 保存商品到数据库
     */
    void save(ProductInfo productInfo);
}
