package com.mall.user.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 封装商品类型信息.
 * <p>
 * 创建时间: 2021/5/28 16:56
 *
 * @author KevinHwang
 */
@Data
@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Override
    public String toString() {
        return name;
    }

    public Type() {

    }

    public Type(String name) {
        this.name = name;
    }
}
