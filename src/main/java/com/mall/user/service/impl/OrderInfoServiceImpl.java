package com.mall.user.service.impl;

import com.mall.user.pojo.OrderDetail;
import com.mall.user.pojo.OrderInfo;
import com.mall.user.pojo.OtherConfig;
import com.mall.user.repository.OrderDetailRepository;
import com.mall.user.repository.OrderInfoRepository;
import com.mall.user.service.OrderInfoService;
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
 * 创建时间: 2021/5/29 21:54
 *
 * @author KevinHwang
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OtherConfig otherConfig;

    @Autowired
    public OrderInfoServiceImpl(OrderInfoRepository orderInfoRepository, OrderDetailRepository orderDetailRepository, OtherConfig otherConfig) {
        this.orderInfoRepository = orderInfoRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.otherConfig = otherConfig;
    }

    @Override
    public Page<OrderInfo> findOrderInfo(Specification<OrderInfo> specification, int pageNum) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(pageNum, otherConfig.getPerPageRows(), Sort.by(orders));
        return orderInfoRepository.findAll(specification, pageable);
    }

    @Override
    public Long count() {
        return orderInfoRepository.count();
    }

    @Override
    public Long addOrderInfo(OrderInfo oi) {
        return orderInfoRepository.save(oi).getId();
    }

    @Override
    @Transactional
    public void modifyStatus(Long id, int flag) {
        orderInfoRepository.modifyStatus(id, flag);
    }

    @Override
    public Long addOrderDetailInfo(OrderDetail od) {
        return orderDetailRepository.save(od).getId();
    }

    @Override
    public OrderInfo getOrderInfoById(Long id) {
        return orderInfoRepository.findById(id).orElse(null);
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        orderInfoRepository.deleteById(id);
        orderDetailRepository.deleteById(id);
    }
}
