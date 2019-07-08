package demo.pac4j.sevice;

import demo.shiro.model.UserInfo;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);
}