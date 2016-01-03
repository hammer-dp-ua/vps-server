package ua.dp.hammer.vpsserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
@ComponentScan(basePackages = "ua.dp.hammer.vpsserver.beans")
public class AppConfig {

   @Autowired
   private Environment environment;

   @PostConstruct
   public void init() {
      // Sets Log4j2 config file location
      LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
      // this will force a reconfiguration
      context.setConfigLocation(new File(environment.getRequiredProperty("log4j2ConfigFile")).toURI());
   }
}