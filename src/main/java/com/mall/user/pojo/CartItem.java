package com.mall.user.pojo;

import lombok.Data;

/**
 * 封装购物车信息.
 * <p>
 * 创建时间: 2021/6/9 16:19
 *
 * @author KevinHwang
 */
@Data
public class CartItem {
    private Long id;
    private int count;
}
