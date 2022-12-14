package com.example.restaurant_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  {

//	記得implement WebMvcConfigurer
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true).allowedMethods("POST")
//				.allowedHeaders("*");
//	}


//	適用於自訂義攔截器(filter)
	@Bean
	public CorsFilter corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfig());
	    return new CorsFilter(source);
	}
	
	private CorsConfiguration corsConfig() {
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    
	    corsConfiguration.addAllowedOriginPattern("*");
	    corsConfiguration.addAllowedHeader("*");
	    corsConfiguration.addAllowedMethod("*");
	    corsConfiguration.setAllowCredentials(true);
//	    corsConfiguration.setMaxAge(3600L);
	    return corsConfiguration;
	}
}
