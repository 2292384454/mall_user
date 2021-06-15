package com.mall.user.repository;

import com.mall.user.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/28 17:29
 *
 * @author KevinHwang
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
    /**
     * 获取系统合法客户，即数据表user_info中status字段为1的客户列表.
     *
     * @return 合法客户列表
     */
    @Query("SELECT u FROM UserInfo u WHERE u.status=1")
    List<UserInfo> getValidUser();

    /**
     * 更新用户状态
     */
    @Modifying
    @Query("UPDATE UserInfo u set u.status=?2 WHERE u.id=?1")
    void modifyStatus(Long id, int flag);

    Optional<UserInfo> findByUsername(String username);
}
