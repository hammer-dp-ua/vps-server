package ua.dp.hammer.vpsserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebMvc
@ComponentScan("ua.dp.hammer.vpsserver.controllers")
public class WebConfig extends WebMvcConfigurerAdapter {

   private String videoDirectory;

   @Autowired
   private Environment environment;

   @PostConstruct
   public void init() {
      videoDirectory = environment.getRequiredProperty(AppConfig.VIDEO_DIRECTORY_ENV_VARIABLE);
   }

   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("index");
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/classic/**").addResourceLocations("/WEB-INF/classic/");
      registry.addResourceHandler("/ext/**").addResourceLocations("/WEB-INF/ext/");
      registry.addResourceHandler("/classic.json").addResourceLocations("/WEB-INF/classic.json");
      registry.addResourceHandler("/cache.appcache").addResourceLocations("/WEB-INF/cache.appcache");
      registry.addResourceHandler("/images/**").addResourceLocations("file:" + videoDirectory + "/");
   }

   @Override
   public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
      configurer.defaultContentType(MediaType.APPLICATION_JSON);
      configurer.ignoreAcceptHeader(true);
   }

   @Bean
   public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
      ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
      resolver.setContentNegotiationManager(manager);
      return resolver;
   }

   @Bean
   public ViewResolver getJspViewResolver() {
      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
      resolver.setPrefix("WEB-INF/jsp/");
      resolver.setSuffix(".jsp");
      resolver.setViewClass(JstlView.class);
      return resolver;
   }

   @Bean(name = "multipartResolver")
   public StandardServletMultipartResolver resolver() {
      return new StandardServletMultipartResolver();
   }
}
