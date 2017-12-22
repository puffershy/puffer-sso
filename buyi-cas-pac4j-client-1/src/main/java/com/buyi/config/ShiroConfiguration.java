package com.buyi.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.pac4j.core.config.Config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.SecurityFilter;

/**
 * shiro配置
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午3:28:02
 */
@Configuration
public class ShiroConfiguration{

	/**
	 * shiro管理器
	 *
	 * @author buyi
	 * @date 2017年12月21日下午8:21:48
	 * @since 1.0.0
	 * @param pac4jRealm
	 * @param subjectFactory
	 * @return
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(Realm pac4jRealm, SubjectFactory subjectFactory) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(pac4jRealm);
		securityManager.setSubjectFactory(subjectFactory);
		return securityManager;
	}

	/**
	 * 注册过滤器
	 *
	 * @author buyi
	 * @date 2017年12月21日下午8:23:12
	 * @since 1.0.0
	 * @return
	 */
	@Bean
	public FilterRegistrationBean registeFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");

		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	/**
	 * 定义cas回调过滤器
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:25:35
	 * @param casConfig
	 * @return
	 */
	@Bean
	public CallbackFilter callbackFilter(Config casConfig) {
		CallbackFilter callbackFilter = new CallbackFilter();
		callbackFilter.setConfig(casConfig);

		return callbackFilter;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, Config config) {
		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
		
		filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
		
		filterFactoryBean.setSecurityManager(securityManager);

		// 过滤器设置
		Map<String, Filter> filters = new HashMap<String, Filter>();

		SecurityFilter securityFilter = new SecurityFilter();
		securityFilter.setClients("cas");
		securityFilter.setConfig(config);

		filters.put("casSecurityFilter", securityFilter);

		CallbackFilter callbackFilter = callbackFilter(config);

		filters.put("callbackFilter", callbackFilter);

		filterFactoryBean.setFilters(filters);

		return filterFactoryBean;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
		definition.addPathDefinition("/callback", "callbackFilter");

		definition.addPathDefinition("/admin/login", "anon");
		definition.addPathDefinition("/admin/**", "casSecurityFilter");
		definition.addPathDefinition("/index", "anon");
		// definition.addPathDefinition("/**", "anon");

		return definition;
	}
	
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

}
