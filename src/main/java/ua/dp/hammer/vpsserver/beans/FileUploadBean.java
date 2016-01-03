package ua.dp.hammer.vpsserver.beans;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FileUploadBean {

   private static final Logger LOGGER = LogManager.getLogger(FileUploadBean.class);

   private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);
   private static final String FILE_NAME_PATTERN = "yyyy-MM-dd_HH-mm-ss-SSS";
   private static final String FILE_EXTENSION = ".ts";
   private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(FILE_NAME_PATTERN);
   private static final int BUFFER_SIZE = 10 * 1024 * 1024;

   private ServerSocket serverSocket;

   @PostConstruct
   public void init() {
      try {
         serverSocket = new ServerSocket(8081);
         listenConnections();
      } catch (IOException e) {
         LOGGER.error(e);
      }
   }

   @Async
   public void listenConnections() throws IOException {
      while (true) {
         try {
            Socket clientSocket = serverSocket.accept();
            THREAD_POOL.execute(new Handler(clientSocket));
         } catch (IOException e) {
            THREAD_POOL.shutdown();
            throw new IOException(e);
         }
      }
   }

   private static class Handler implements Runnable {
      private final Socket socket;

      public Handler(Socket socket) {
         this.socket = socket;
      }

      public void run() {
         String fileName = SIMPLE_DATE_FORMAT.format(new Date()) + FILE_EXTENSION;
         LOGGER.info("Adding new file: " + fileName);

         try (OutputStream outputStream = Files.newOutputStream(FileSystems.getDefault().getPath("C:/" + fileName));
              BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, BUFFER_SIZE);
              BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE)) {

            int data;
            int bytes = 0;
            while ((data = bufferedInputStream.read()) != -1) {
               bytes++;
               bufferedOutputStream.write(data);
            }
            LOGGER.info("Added file size: " + (bytes / 1024) + "KB");
         } catch (IOException e) {
            LOGGER.error(e);
         }
      }
   }
}
