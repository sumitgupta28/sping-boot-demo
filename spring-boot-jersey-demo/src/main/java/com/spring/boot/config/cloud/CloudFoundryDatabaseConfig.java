package com.spring.boot.config.cloud;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"cloud","pcf"})
public class CloudFoundryDatabaseConfig {
	
	@Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }

    @Bean
    public DataSource dataSource() {
    	List<ServiceInfo>	serviceInfos= cloud().getServiceInfos();
    	for(ServiceInfo serviceInfo:	serviceInfos){
    		System.out.println( "serviceInfo :: " + serviceInfo.getId());	
    	}
        DataSource dataSource = cloud().getServiceConnector("user-service-db", DataSource.class, null);
        System.out.println( "dataSource :: " + dataSource);
        return dataSource;
    }
}
