package fr.esiea.pair.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// Create root application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(BackendApplicationConfiguration.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
		// Servlet
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(WebMvcConfig.class);
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet(
						"dispatcher", 
						new DispatcherServlet(dispatcherContext)
						);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/rest/*");
	}

}