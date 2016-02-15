package ua.dp.hammer.vpsserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import ua.dp.hammer.vpsserver.beans.FileLoaderBean;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("ua.dp.hammer.vpsserver.beans")
@EnableAsync
public class AppConfig {

   private String videoDirectory;

   @Autowired
   private Environment environment;

   @Autowired
   private FileLoaderBean fileLoaderBean;

   @PostConstruct
   public void init() {
      videoDirectory = environment.getRequiredProperty("JAVA_VPS_SERVER_VIDEO_DIRECTORY");
      fileLoaderBean.listenConnections();
   }

   public String getVideoDirectory() {
      return videoDirectory;
   }
}