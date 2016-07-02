package ua.dp.hammer.vpsserver.beans;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ua.dp.hammer.vpsserver.config.AppConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileLoaderBean {

   private static final Logger LOGGER = LogManager.getLogger(FileLoaderBean.class);

   private static final String FILE_NAME_PATTERN = "yyyy-MM-dd_HH-mm-ss-SSS";
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(FILE_NAME_PATTERN);
   private static final int BUFFER_SIZE = 10 * 1024 * 1024;

   private ServerSocket serverSocket;

   @Autowired
   private Environment environment;
   @Autowired
   private AppConfig appConfig;

   @Async
   public void listenConnections() {
      String fileExtension = environment.getRequiredProperty("JAVA_VPS_SERVER_VIDEO_FILES_EXTENSION");
      String videoDirectory = appConfig.getVideoDirectory();
      String serverSocketPort = environment.getRequiredProperty("JAVA_VPS_SERVER_SOCKET_PORT");

      try {
         serverSocket = new ServerSocket(Integer.parseInt(serverSocketPort));
      } catch (IOException e) {
         LOGGER.error(e);
      }

      while (true) {
         try {
            Socket clientSocket = serverSocket.accept();
            String fileName = SIMPLE_DATE_FORMAT.format(new Date()) + fileExtension;

            try (OutputStream outputStream = Files.newOutputStream(FileSystems.getDefault().getPath(videoDirectory + "/" + fileName));
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, BUFFER_SIZE);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream(), BUFFER_SIZE)) {
               LOGGER.info("Adding new file: " + fileName);

               int data;
               int bytes = 0;
               while ((data = bufferedInputStream.read()) != -1) {
                  bytes++;
                  bufferedOutputStream.write(data);
               }

               LOGGER.info("Added file size: " + (bytes / 1024 / 1024) + "MB");
            } catch (IOException e) {
               LOGGER.error(e);
            }
         } catch (IOException e) {
            LOGGER.error(e);
         }
      }
   }
}
