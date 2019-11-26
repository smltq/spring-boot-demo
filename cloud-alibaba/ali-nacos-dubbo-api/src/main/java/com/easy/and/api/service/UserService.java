package com.easy.and.api.service;

import com.easy.and.api.vo.User;

import java.util.Collection;

public interface UserService {

    boolean save(User user);

    boolean remove(Long userId);

    Collection<User> findAll();
}