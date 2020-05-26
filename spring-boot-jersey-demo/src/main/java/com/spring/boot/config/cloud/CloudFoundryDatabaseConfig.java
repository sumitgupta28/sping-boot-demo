package com.spring.boot.config.cloud;

import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.spring.boot.common.UserServiceConstants;

@Configuration
@Profile({ "cloud", "pcf" })
public class CloudFoundryDatabaseConfig extends AbstractCloudConfig {

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = cloud().getServiceConnector(UserServiceConstants.USER_SERVICE_DB, DataSource.class,
				null);
		return dataSource;
	}

}
