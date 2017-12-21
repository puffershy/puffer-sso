package com.buyi.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;

public class CustomPac4jRealm extends Pac4jRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		return super.doGetAuthenticationInfo(authenticationToken);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取cas传递回来的username
		Pac4jPrincipal pac4jPrincipal =  (Pac4jPrincipal) principals.getPrimaryPrincipal();
		String username =pac4jPrincipal.getProfile().getId();
		System.out.println("登陆名"+username);
		
		return super.doGetAuthorizationInfo(principals);
	}

}
