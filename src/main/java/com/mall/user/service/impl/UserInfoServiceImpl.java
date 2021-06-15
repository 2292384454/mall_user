package com.mall.user.service.impl;

import com.mall.user.pojo.OtherConfig;
import com.mall.user.pojo.UserInfo;
import com.mall.user.repository.UserInfoRepository;
import com.mall.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息服务实现类.
 * <p>
 * 创建时间: 2021/5/28 18:47
 *
 * @author KevinHwang
 */
@Service
public class UserInfoServiceImpl implements UserInfoService, UserDetailsService {
    private final UserInfoRepository userInfoRepository;
    private final OtherConfig otherConfig;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, OtherConfig otherConfig) {
        this.userInfoRepository = userInfoRepository;
        this.otherConfig = otherConfig;
    }

    @Override
    public List<UserInfo> getValidUser() {
        return userInfoRepository.getValidUser();
    }

    @Override
    public UserInfo getUserInfoById(Long id) {
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(id);
        if (!optionalUserInfo.isPresent()) {
            throw new UsernameNotFoundException("User' " + id + "' not found");
        }
        return optionalUserInfo.orElse(null);
    }

    @Override
    public Page<UserInfo> findUserInfo(int pageNum) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(pageNum, otherConfig.getPerPageRows(), Sort.by(orders));
        return userInfoRepository.findAll(pageable);
    }

    @Override
    public Page<UserInfo> findByCondition(Specification<UserInfo> specification, int pageNum) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(pageNum, otherConfig.getPerPageRows(), Sort.by(orders));
        return userInfoRepository.findAll(specification, pageable);
    }

    @Override
    public Long count() {
        return userInfoRepository.count();
    }

    @Override
    @Transactional
    public void modifyStatus(Long id, int flag) {
        userInfoRepository.modifyStatus(id, flag);
    }

    @Override
    public UserInfo findByUserName(String username) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUsername(username);
        if (!userInfoOptional.isPresent()) {
            throw new UsernameNotFoundException("Username' " + username + "' not found");
        }
        return userInfoOptional.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库获取用户信息
        return findByUserName(username);
    }
}
