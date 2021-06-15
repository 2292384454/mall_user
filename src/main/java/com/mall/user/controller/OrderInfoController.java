package com.mall.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.user.pojo.CartItem;
import com.mall.user.pojo.OrderDetail;
import com.mall.user.pojo.OrderInfo;
import com.mall.user.pojo.ProductInfo;
import com.mall.user.service.OrderInfoService;
import com.mall.user.service.ProductInfoService;
import com.mall.user.service.UserInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/10 15:10
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/order")
@Log4j2
public class OrderInfoController {
    private final UserInfoService userInfoService;
    private final OrderInfoService orderInfoService;
    private final ProductInfoService productInfoService;

    @Autowired
    public OrderInfoController(UserInfoService userInfoService, OrderInfoService orderInfoService, ProductInfoService productInfoService) {
        this.userInfoService = userInfoService;
        this.orderInfoService = orderInfoService;
        this.productInfoService = productInfoService;
    }

    @RequestMapping(value = "/handlerOrder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String handleOrder(Authentication authentication, String cart) throws IOException {
        // 创建ObjectMapper对象，实现JaveBean和JSON的转换
        ObjectMapper mapper = new ObjectMapper();
        // 将JSON字符串转换成List<CartItem>集合
        List<CartItem> ciList = mapper.readValue(cart, new TypeReference<ArrayList<CartItem>>() {
        });
        OrderInfo oi = new OrderInfo();
        oi.setUserInfo(userInfoService.findByUserName(authentication.getName()));
        // 订单状态:已提交
        oi.setStatus(0);
        oi.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        double orderPrice = 0.0;
        for (CartItem ci : ciList) {
            ProductInfo pi = productInfoService.getProductInfoById(ci.getId());
            orderPrice += ci.getCount() * pi.getPrice();
        }
        oi.setOrderPrice(orderPrice);
        //生成订单明细
        List<OrderDetail> orderDetails = new LinkedList<>();
        for (CartItem ci : ciList) {
            OrderDetail od = new OrderDetail();
            od.setOrderInfo(oi);
            od.setProductInfo(productInfoService.getProductInfoById(ci.getId()));
            od.setNum(ci.getCount());
            orderDetails.add(od);
        }
        //保存订单
        oi.setOrderDetails(orderDetails);
        orderInfoService.addOrderInfo(oi);
        log.info("用户 " + oi.getUserInfo().getUsername() + " 创建订单 " + oi.getId());
        return "success";
    }
}
