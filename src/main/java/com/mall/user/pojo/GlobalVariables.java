package com.mall.user.pojo;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目的全局变量
 * <p>
 * 创建时间: 2021/6/4 11:08
 *
 * @author KevinHwang
 */
@Component
@Log4j2
public class GlobalVariables {
    /**
     * 订单状态表
     */
    public static List<String> orderStatus = new ArrayList<>();

    /**
     * 用户状态表
     */
    public static List<String> userStatus = new ArrayList<>();

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行。
     */
    @PostConstruct
    public void init() {
        /* 订单状态表 */
        // 0
        orderStatus.add("已提交");
        // 1
        orderStatus.add("待付款");
        // 2
        orderStatus.add("已付款");
        // 3
        orderStatus.add("已发货");
        // 4
        orderStatus.add("已关闭");
        // 5
        orderStatus.add("已完成");
        // 6
        orderStatus.add("用户取消");
        // 7
        orderStatus.add("管理员取消");
        /* END: 订单状态 */

        log.info("订单状态表加载完成，内容： " + orderStatus);
        /* 用户状态表 */
        userStatus.add("停用");
        userStatus.add("启用");
        /* END：用户状态表 */
        log.info("用户状态表加载完成，内容： " + userStatus);
    }
}
