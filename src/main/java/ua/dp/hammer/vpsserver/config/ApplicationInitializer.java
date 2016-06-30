package ua.dp.hammer.vpsserver.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.File;

public class ApplicationInitializer implements WebApplicationInitializer {

   private static final String LOG4J2_CONFIG_FILE_ENV_VARIABLE = "JAVA_VPS_SERVER_LOG4J2_CONFIG_FILE";
   private static final String LOG4J2_CONFIG_FILE = System.getenv(LOG4J2_CONFIG_FILE_ENV_VARIABLE);
   private static final String UPLOAD_TMP_LOCATION_ENV_VARIABLE = "JAVA_VPS_SERVER_UPLOAD_TMP_LOCATION";
   private static final String UPLOAD_TMP_LOCATION = System.getenv(UPLOAD_TMP_LOCATION_ENV_VARIABLE);
   private static final long UPLOAD_MAX_FILE_SIZE = 200 * 1024 * 1024;
   private static final long UPLOAD_MAX_REQUEST_SIZE = UPLOAD_MAX_FILE_SIZE;
   private static final int UPLOAD_FILE_SIZE_THRESHOLD = 1024 * 1024;
   private static final String ENVIRONMENT_VARIABLE_IS_NOT_SET = " environment variable is not set";

   @Override
   public void onStartup(ServletContext container) throws ServletException {
      if (LOG4J2_CONFIG_FILE == null) {
         throw new IllegalStateException(LOG4J2_CONFIG_FILE_ENV_VARIABLE + ENVIRONMENT_VARIABLE_IS_NOT_SET);
      }
      if (UPLOAD_TMP_LOCATION == null) {
         throw new IllegalStateException(UPLOAD_TMP_LOCATION_ENV_VARIABLE + ENVIRONMENT_VARIABLE_IS_NOT_SET);
      }

      // Sets Log4j2 config file location
      LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
      // this will force a reconfiguration
      loggerContext.setConfigLocation(new File(LOG4J2_CONFIG_FILE).toURI());

      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
      // Manage the lifecycle of the root application context
      container.addListener(new ContextLoaderListener(context));

      // Creates the root application context
      context.setConfigLocation("ua.dp.hammer.vpsserver.config");

      ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(context));
      registration.setLoadOnStartup(1);
      registration.addMapping("/");

      registration.setMultipartConfig(new MultipartConfigElement(UPLOAD_TMP_LOCATION, UPLOAD_MAX_FILE_SIZE,
            UPLOAD_MAX_REQUEST_SIZE, UPLOAD_FILE_SIZE_THRESHOLD));
   }
}
