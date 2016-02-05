package ua.dp.hammer.vpsserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("ua.dp.hammer.vpsserver.beans")
public class AppConfig {

   private String videoDirectory;

   @Autowired
   private Environment environment;

   @PostConstruct
   public void init() {
      videoDirectory = environment.getRequiredProperty("JAVA_VPS_SERVER_VIDEO_DIRECTORY");
   }

   public String getVideoDirectory() {
      return videoDirectory;
   }
}