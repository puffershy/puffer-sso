package com.buyi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ShiroProperties.SHIRO_PREFIX)
public class ShiroProperties {
	public static final String SHIRO_PREFIX = "shiro";

	private String casServerUrlPrefix;
	private String casLoginUrl;
	private String casLogoutUrl;
	
	/**
	 * 当前客户端地址
	 */
	private String serverUrlPrefix;
	private String serverCallBack;
	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}
	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}
	public String getCasLoginUrl() {
		return casLoginUrl;
	}
	public void setCasLoginUrl(String casLoginUrl) {
		this.casLoginUrl = casLoginUrl;
	}
	public String getCasLogoutUrl() {
		return casLogoutUrl;
	}
	public void setCasLogoutUrl(String casLogoutUrl) {
		this.casLogoutUrl = casLogoutUrl;
	}
	public String getServerUrlPrefix() {
		return serverUrlPrefix;
	}
	public void setServerUrlPrefix(String serverUrlPrefix) {
		this.serverUrlPrefix = serverUrlPrefix;
	}
	public String getServerCallBack() {
		return serverCallBack;
	}
	public void setServerCallBack(String serverCallBack) {
		this.serverCallBack = serverCallBack;
	}
}
