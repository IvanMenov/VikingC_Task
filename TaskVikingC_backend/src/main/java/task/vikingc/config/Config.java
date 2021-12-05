package task.vikingc.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableAsync
public class Config {
    private final long MAX_AGE_SECS = 3600;

	@Bean(name = "threadPoolTaskExecutor", destroyMethod = "shutdown")
	public Executor getAsyncExecutor() {
		// TODO Auto-generated method stub
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(50);
		executor.setThreadNamePrefix("RESTCallService-");
		executor.initialize();
		return executor;
	}
	
	 @Bean
	    public CorsFilter corsFilter() {
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.setAllowedOriginPatterns(Collections.singletonList("*"));
	        config.setAllowedHeaders(Arrays.asList("*"));
	        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	        config.setMaxAge(MAX_AGE_SECS);
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }  
}
