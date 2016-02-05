package ua.dp.hammer.vpsserver.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("ua.dp.hammer.vpsserver.controllers")
public class WebConfig extends WebMvcConfigurerAdapter {

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/resources/**")
            .addResourceLocations("/resources/");
   }

   /*@Bean
   public InternalResourceViewResolver viewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setViewClass(JstlView.class);
      viewResolver.setPrefix("/WEB-INF/jsp/");
      viewResolver.setSuffix(".jsp");
      return viewResolver;
   }*/

   /*@Bean
   public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {

      List< ViewResolver > resolvers = new ArrayList<>();

      InternalResourceViewResolver r1 = new InternalResourceViewResolver();
      r1.setPrefix("/WEB-INF/pages/");
      r1.setSuffix(".jsp");
      r1.setViewClass(JstlView.class);

      resolvers.add(r1);

      JsonViewResolver r2 = new JsonViewResolver();
      resolvers.add(r2);

      ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
      resolver.setViewResolvers(resolvers);
      resolver.setContentNegotiationManager(manager);
      return resolver;

   }*/

   @Override
   public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
      configurer.defaultContentType(MediaType.APPLICATION_JSON);
   }
}
