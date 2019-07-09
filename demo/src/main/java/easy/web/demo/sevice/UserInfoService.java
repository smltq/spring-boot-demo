package easy.web.demo.sevice;

import easy.web.demo.model.UserInfo;

public interface UserInfoService {
    /**
     * 通过username查找用户信息;
     */
    UserInfo findByUsername(String username);
}