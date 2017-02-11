/**
 * 
 */
package com.spring.boot.config;

import javax.annotation.PostConstruct;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * @author Sumit
 *
 */
@Component
public class JerseyConfig extends ResourceConfig {

	private static final String API_PACKAGE_NAME = "com.spring.boot.controller";

	public JerseyConfig(ObjectMapper objectMapper) {
		register(RequestContextFilter.class);
		register(LoggingFeature.class);
		packages(API_PACKAGE_NAME);
		register(new ObjectMapperContextResolver(objectMapper));

	}

	@Provider
	public static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
		private final ObjectMapper mapper;

		public ObjectMapperContextResolver(ObjectMapper mapper) {
			this.mapper = mapper;
		}

		@Override
		public ObjectMapper getContext(Class<?> type) {
			return mapper;
		}
	}

	@PostConstruct
	public void init() {
		this.configureSwagger();
	}

	private void configureSwagger() {
		// Available at localhost:port/swagger.json
		this.register(ApiListingResource.class);
		this.register(SwaggerSerializers.class);

		BeanConfig config = new BeanConfig();
		config.setConfigId("Swagger-config");
		config.setTitle("User Service Api Listing");
		config.setVersion("v1");
		config.setSchemes(new String[] { "http", "https" });
		config.setBasePath("/demo");
		config.setResourcePackage(API_PACKAGE_NAME);
		config.setPrettyPrint(true);
		config.setScan(true);
	}
}
