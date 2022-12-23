//
// package com.idev4.setup.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.web.config.EnableSpringDataWebSupport;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
// import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
// @Configuration
// @EnableWebMvc
// @EnableSpringDataWebSupport
// public class CustomWebConfiguration extends WebMvcConfigurationSupport {
//
// @Bean
// public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//
// RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
// handlerMapping.setUseSuffixPatternMatch( false );
// return handlerMapping;
// }
// }
