package easy.web.demo.config;

import easy.web.demo.model.SysPermission;
import easy.web.demo.model.SysRole;
import easy.web.demo.model.UserInfo;
import easy.web.demo.sevice.UserInfoService;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.token.Pac4jToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

@Slf4j
public class CasRealm extends Pac4jRealm {

    @Resource
    private UserInfoService userInfoService;

    private String clientName;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;

        log.info("调用认证方法");

        //获取用户的输入的账号.
        String username = (String) pac4jToken.getPrincipal();
        if (username == null) {
            throw new AuthenticationException("账号名为空，登录失败！");
        }

        log.info("credentials:" + pac4jToken.getCredentials());

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

    /**
     * 授权/验权（todo 后续有权限在此增加）
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
