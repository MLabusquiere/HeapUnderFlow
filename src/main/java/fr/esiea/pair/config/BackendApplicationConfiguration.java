package fr.esiea.pair.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "fr.esiea.pair.api")
@EnableWebMvc
public class BackendApplicationConfiguration {
 
	
}
