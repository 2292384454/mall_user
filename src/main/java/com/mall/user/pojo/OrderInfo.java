package com.mall.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 封装订单信息.
 * <p>
 * 创建时间: 2021/5/28 16:59
 *
 * @author KevinHwang
 */

@Data
@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单8种状态.<p>
     * {@link GlobalVariables#orderStatus}
     */
    @Max(value = 7)
    @Min(value = 0)
    private int status;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "order_price")
    private double orderPrice;

    /**
     * 每个订单只会属于一个用户，但是一个用户可能有多个订单
     */
    @ManyToOne
    @JoinColumn(name = "uid")
    private UserInfo userInfo;

    /**
     * 每个订单可能有多个商品，即多个订单明细。<p>
     * 级联保存、更新、删除、刷新;延迟加载。当删除订单，会级联删除该订单的所有订单详情<p>
     * 拥有mappedBy注解的实体类为关系被维护端<p>
     * mappedBy="oid"中的oid是OrderDetail中的oid属性
     */
    @JsonIgnoreProperties({"orderInfo"})
    @OneToMany(targetEntity = OrderDetail.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid")
    private List<OrderDetail> orderDetails;

    /**
     * 设置订单创建时间
     */
    @PrePersist
    void orderTime() {
        this.orderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
