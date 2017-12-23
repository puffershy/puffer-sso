package com.buyi.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jSubjectFactory;

/**
 * shiro配置
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午3:28:02
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfiguration extends AbstractShiroWebFilterConfiguration {

	@Resource
	private ShiroProperties properties;

	/************************* pac4j cas 配置 begin ************************/
	@Bean
	public Realm realm() {
		return new Pac4jRealm();
	}

	/**
	 * cas核心过滤器，把支持的client写上，filter过滤时才会处理，clients必须在casConfig.clients已经注册
	 * <p>
	 * 注意：该过滤器不要用spring注入，否则该过滤器会拦截所有的路径请求，除非自定义匹配规则
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午2:53:21
	 * @return
	 */
	public Filter casSecurityFilter() {
		SecurityFilter filter = new SecurityFilter();
		filter.setClients("cas");
		filter.setConfig(casConfig());
		return filter;
	}

	/**
	 * 定义cas客户端集合
	 * <p>
	 * 后期如果需要支持rest接口单点登录，就在这里扩展
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:20:10
	 * @param casClient
	 * @return
	 */
	@Bean
	public Clients clients() {
		Clients clients = new Clients();
		clients.setClients(casClient());
		clients.setDefaultClient(casClient());
		return clients;
	}

	/**
	 * 定义配置
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:22:53
	 * @param clients
	 * @return
	 */
	@Bean
	public Config casConfig() {
		Config config = new Config();
		config.setClients(clients());

		return config;
	}

	/**
	 * cas服务的基本设置，包括或url等等，rest调用协议等
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:09:35
	 * @return
	 */
	@Bean
	public CasConfiguration casConfiguration() {
		CasConfiguration casConfiguration = new CasConfiguration(properties.getCasLoginUrl());
		// 默认走CasProtocol.CAS30协议
		casConfiguration.setProtocol(CasProtocol.CAS30);
		casConfiguration.setPrefixUrl(properties.getCasServerUrlPrefix());
		return casConfiguration;
	}

	/**
	 * 定义cas客户端
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:09:26
	 * @param casConfiguration
	 * @return
	 */
	@Bean
	public CasClient casClient() {
		CasClient casClient = new CasClient();
		casClient.setConfiguration(casConfiguration());
		casClient.setCallbackUrl(properties.getServerCallBack());
		// 设置cas客户端名称为cas
		casClient.setName("cas");
		return casClient;
	}


	/************************* pac4j cas 配置 end ************************/

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {

		((DefaultWebSecurityManager) super.securityManager).setSubjectFactory(new Pac4jSubjectFactory());

		ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();

		filterFactoryBean.setFilters(shiroFilters());

		return filterFactoryBean;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
		definition.addPathDefinition("/callback", "callbackFilter");
		definition.addPathDefinition("/admin/login", "anon");
		definition.addPathDefinition("/admin/logout", "logoutFilter");
		definition.addPathDefinition("/**", "casSecurityFilter");

		return definition;
	}

	@Bean
	public Map<String, Filter> shiroFilters() {
		// 过滤器设置
		Map<String, Filter> filters = new HashMap<>();
		filters.put("casSecurityFilter", casSecurityFilter());

		
		CallbackFilter callbackFilter = new CallbackFilter();
		callbackFilter.setConfig(casConfig());
		filters.put("callbackFilter", callbackFilter);

		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setConfig(casConfig());
		logoutFilter.setCentralLogout(true);
		logoutFilter.setDefaultUrl(properties.getServerUrlPrefix());
		filters.put("logoutFilter", logoutFilter);

		return filters;
	}
}
