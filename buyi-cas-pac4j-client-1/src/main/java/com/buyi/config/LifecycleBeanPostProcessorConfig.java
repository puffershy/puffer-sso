package com.buyi.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Shiro生命周期处理器,开启了就会启动报错不知道为什么？---设置为优先启动！！见：LifecycleBeanPostProcessorConfig
 * 
 * @Description:TODO
 * @author:hsj qq:2356899074
 * @time:2017年12月13日 上午11:34:49
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LifecycleBeanPostProcessorConfig {

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
