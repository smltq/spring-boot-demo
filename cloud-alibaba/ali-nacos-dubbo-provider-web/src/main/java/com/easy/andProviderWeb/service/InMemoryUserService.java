package com.easy.andProviderWeb.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.easy.and.api.service.UserService;
import com.easy.and.api.vo.User;
import org.apache.dubbo.config.annotation.Service;

@Service(protocol = "dubbo")
public class InMemoryUserService implements UserService {

    private Map<Long, User> usersRepository = new HashMap<>();

    @Override
    public boolean save(User user) {
        return usersRepository.put(user.getId(), user) == null;
    }

    @Override
    public boolean remove(Long userId) {
        return usersRepository.remove(userId) != null;
    }

    @Override
    public Collection<User> findAll() {
        return usersRepository.values();
    }
}