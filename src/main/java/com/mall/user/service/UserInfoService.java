package com.mall.user.service;

import com.mall.user.pojo.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 用户信息服务接口.
 * <p>
 * 创建时间: 2021/5/28 18:41
 *
 * @author KevinHwang
 */
public interface UserInfoService {
    /**
     * @return 合法客户
     */
    List<UserInfo> getValidUser();

    /**
     * 根据客户id查询客户
     *
     * @param id 客户id
     * @return 查询到的结果
     */
    UserInfo getUserInfoById(Long id);

    /**
     * 分页显示客户
     */
    Page<UserInfo> findUserInfo(int pageNum);

    /**
     * 带条件分页查询
     */
    Page<UserInfo> findByCondition(Specification<UserInfo> specification, int pageNum);

    /**
     * 客户计数
     *
     * @return 客户总数
     */
    Long count();

    /**
     * 修改指定id的用户状态
     *
     * @param id   用户id
     * @param flag 要修改的状态
     */
    void modifyStatus(Long id, int flag);

    /**
     * 根据用户名获取UserInfo对象
     *
     * @param username 用户名
     * @return UserInfo对象
     */
    UserInfo findByUserName(String username);
}
