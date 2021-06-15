package com.mall.user.service;

import com.mall.user.pojo.OrderDetail;
import com.mall.user.pojo.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/29 21:36
 *
 * @author KevinHwang
 */
public interface OrderInfoService {
    /**
     * 按条件分页显示订单
     */
    Page<OrderInfo> findOrderInfo(Specification<OrderInfo> specification, int pageNum);

    /**
     * 订单计数
     */
    Long count();

    /**
     * 添加订单主表
     */
    Long addOrderInfo(OrderInfo oi);


    /**
     * 更新订单状态
     */
    void modifyStatus(Long id, int flag);

    /**
     * 添加订单明细
     */
    Long addOrderDetailInfo(OrderDetail od);

    /**
     * 根据订单编号获取订单信息
     */
    OrderInfo getOrderInfoById(Long id);

    /**
     * 根据订单编号获取订单明细信息
     */
    OrderDetail getOrderDetailById(Long id);

    /**
     * 删除订单
     */
    public void deleteOrder(Long id);
}
