package com.mall.user.repository;

import com.mall.user.pojo.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/28 17:29
 *
 * @author KevinHwang
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long>, JpaSpecificationExecutor<ProductInfo> {

    /**
     * 获取在售商品列表，即数据表product_info中status字段为1的客户列表.
     *
     * @return 在售商品列表
     */
    @Query("SELECT p FROM ProductInfo p WHERE p.status=1")
    List<ProductInfo> getOnSaleProduct();


    /**
     * 更新商品状态
     */
    @Modifying
    @Query("UPDATE ProductInfo p set p.status=?2 WHERE p.id=?1")
    void modifyStatus(Long id, int flag);
}
