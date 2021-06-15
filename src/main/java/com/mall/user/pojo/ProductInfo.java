package com.mall.user.pojo;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 封装商品信息.
 * <p>
 * 创建时间: 2021/5/28 16:52
 *
 * @author KevinHwang
 */
@Data
@Entity
@Table(name = "product_info")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "请输入商品编号")
    private String code;

    @NotBlank(message = "请输入商品名")
    private String name;

    @OneToOne(targetEntity = Type.class)
    @JoinColumn(name = "tid")
    @NotNull(message = "请选择商品种类")
    private Type type;

    @NotBlank(message = "请输入商品品牌")
    private String brand;

    private String pic;

    @Min(value = 0, message = "请选择商品数量")
    private int num;

    @Min(value = 0, message = "请选择商品价格")
    private double price;

    @Min(value = 0, message = "请选择商品状态")
    private int status;

    @NotBlank(message = "请输入商品介绍")
    private String intro;

    public ProductInfo() {
        this.type = new Type();
    }
}
