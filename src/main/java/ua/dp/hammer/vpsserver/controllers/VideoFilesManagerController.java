package ua.dp.hammer.vpsserver.controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.hammer.vpsserver.config.AppConfig;
import ua.dp.hammer.vpsserver.models.VideoFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@RestController
public class VideoFilesManagerController {
   private static final Logger LOGGER = LogManager.getLogger(VideoFilesManagerController.class);

   private static final Comparator<VideoFile> FILES_COMPARATOR_ASC = new Comparator<VideoFile>() {
      @Override
      public int compare(VideoFile file1, VideoFile file2) {
         long diff = file1.getCreationDate().getTime() - file2.getCreationDate().getTime();
         return (diff == 0) ? 0 : (diff > 0 ? 1 : -1);
      }
   };

   private static final Comparator<VideoFile> FILES_COMPARATOR_DESC = new Comparator<VideoFile>() {
      @Override
      public int compare(VideoFile file1, VideoFile file2) {
         long diff = file1.getCreationDate().getTime() - file2.getCreationDate().getTime();
         return (diff == 0) ? 0 : (diff > 0 ? -1 : 1);
      }
   };

   private String videoDirectory;

   @Autowired
   private AppConfig appConfig;

   @PostConstruct
   public void init() {
      videoDirectory = appConfig.getVideoDirectory();
   }

   @RequestMapping("/home")
   public String loadHomePage(Model m) {
      return "index";
   }

   @RequestMapping(path = "/files", produces = "application/json", method = RequestMethod.GET)
   public SortedSet<VideoFile> getFiles(HttpServletRequest httpServletRequest) {
      final SortedSet<VideoFile> videoFiles = new TreeSet<>(FILES_COMPARATOR_DESC);

      LOGGER.info(httpServletRequest.getRequestURL());

      try {
         Files.walkFileTree(FileSystems.getDefault().getPath(videoDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
               if (Files.isReadable(filePath)) {
                  File foundFile = filePath.toFile();
                  videoFiles.add(new VideoFile(foundFile.getName(), new Date(foundFile.lastModified()), foundFile.length()));
               }
               return FileVisitResult.CONTINUE;
            }
         });
      } catch (IOException e) {
         LOGGER.error(e);
      }
      return videoFiles;
   }
}
