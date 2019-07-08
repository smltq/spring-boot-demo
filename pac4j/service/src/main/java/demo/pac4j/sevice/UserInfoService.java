package demo.pac4j.sevice;

import demo.pac4j.model.UserInfo;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);
}