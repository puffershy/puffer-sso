package com.buyi.config;

import javax.annotation.Resource;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.buji.pac4j.subject.Pac4jSubjectFactory;

/**
 * 定义pac4j cas配置
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午3:26:54
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class Pac4jCasConfiguration {
	@Resource
	private ShiroProperties properties;

	@Bean
	public Realm realm() {
		return new CustomPac4jRealm();
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
		CasConfiguration casConfiguration = new CasConfiguration();
		casConfiguration.setLoginUrl(properties.getCasLoginUrl());
		casConfiguration.setPrefixUrl(properties.getCasServerUrlPrefix());
		// 默认走CasProtocol.CAS30协议
		// casConfiguration.setProtocol(CasProtocol.CAS20);
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
	public CasClient casClient(CasConfiguration casConfiguration) {
		CasClient casClient = new CasClient();
		casClient.setConfiguration(casConfiguration);
		casClient.setCallbackUrl(properties.getServerCallBack());
		// 设置cas客户端名称为cas
		casClient.setName("cas");
		return casClient;
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
	public Clients clients(CasClient casClient) {
		Clients clients = new Clients();

		clients.setClients(casClient);
		clients.setDefaultClient(casClient);
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
	public Config casConfig(Clients clients) {
		Config config = new Config();
		config.setClients(clients);

		return config;
	}

	/**
	 * 使用cas代理了用户，所以必须通过pac4j cas进行创建对象
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午3:30:08
	 * @return
	 */
	@Bean
	public SubjectFactory subjectFactory() {
		return new Pac4jSubjectFactory();
	}

}
