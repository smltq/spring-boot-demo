package demo.pac4j.config;

import demo.shiro.model.SysPermission;
import demo.shiro.model.SysRole;
import demo.shiro.model.UserInfo;
import demo.shiro.sevice.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("调用授权方法");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        for (SysRole role : userInfo.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission p : role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 认证(主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确)
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("调用认证方法");
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        if (username == null) {
            throw new AuthenticationException("账号名为空，登录失败！");
        }

        log.info("credentials:" + token.getCredentials());

        //TODO:根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            throw new AuthenticationException("不存在的账号，登录失败！");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,                                               //用户
                userInfo.getPassword(),                                 //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),   //加盐后的密码
                getName()                                               //指定当前 Realm 的类名
        );
        return authenticationInfo;
    }
}