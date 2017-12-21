package com.buyi.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 * @author buyi
 * @since 1.0.0
 * @date 2017下午3:28:02
 */
@Configuration
public class ShiroConfiguration {
	
	
	@Bean
	public DefaultSecurityManager securityManager(Realm pac4jRealm,SubjectFactory subjectFactory){
		DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(pac4jRealm);
		securityManager.setSubjectFactory(subjectFactory);
		return securityManager;
	}
	
	
}
