package com.buyi.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;

public class CustomPac4jRealm extends Pac4jRealm {
	private static final Logger logger = LoggerFactory.getLogger(CustomPac4jRealm.class);

	private static final String PERM_ADMIN_USER_DETAIL = "admin:user:detail";

	/**
	 * 登陆认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		Object principal = authenticationToken.getPrincipal();
		return super.doGetAuthenticationInfo(authenticationToken);
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户名
		Pac4jPrincipal principal = principals.oneByType(Pac4jPrincipal.class);

		logger.info("用户【{}】授权", principal.getProfile().getAttribute("user_id"));
		SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) super.doGetAuthorizationInfo(principals);
		info.addStringPermission(PERM_ADMIN_USER_DETAIL);
		return info;

		// return super.doGetAuthorizationInfo(principals);
	}

}
