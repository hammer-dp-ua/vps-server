package ua.dp.hammer.vpsserver.controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.dp.hammer.vpsserver.config.AppConfig;
import ua.dp.hammer.vpsserver.models.MultimediaFile;
import ua.dp.hammer.vpsserver.models.Picture;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@RestController
@RequestMapping(path = "/multimedia/rest")
public class MultimediaFilesManagerController {
   private static final Logger LOGGER = LogManager.getLogger(MultimediaFilesManagerController.class);

   @Autowired
   private Environment environment;

   private String videoFilesExtension;

   private static final Comparator<MultimediaFile> VIDEO_FILES_COMPARATOR_ASC = new Comparator<MultimediaFile>() {
      @Override
      public int compare(MultimediaFile file1, MultimediaFile file2) {
         long diff = file1.getCreationDateOrig().getTime() - file2.getCreationDateOrig().getTime();
         return (diff == 0) ? 0 : (diff > 0 ? 1 : -1);
      }
   };

   private static final Comparator<MultimediaFile> VIDEO_FILES_COMPARATOR_DESC = new Comparator<MultimediaFile>() {
      @Override
      public int compare(MultimediaFile file1, MultimediaFile file2) {
         long diff = file1.getCreationDateOrig().getTime() - file2.getCreationDateOrig().getTime();
         return (diff == 0) ? 0 : (diff > 0 ? -1 : 1);
      }
   };

   static final Comparator<Picture> IMAGE_FILES_COMPARATOR_ASC = new Comparator<Picture>() {
      @Override
      public int compare(Picture picture1, Picture picture2) {
         return picture1.getName().compareTo(picture2.getName());
      }
   };

   private String videoDirectory;

   @Autowired
   private AppConfig appConfig;

   @PostConstruct
   public void init() {
      videoDirectory = appConfig.getVideoDirectory();
      videoFilesExtension = environment.getRequiredProperty("JAVA_VPS_SERVER_VIDEO_FILES_EXTENSION");
   }

   @RequestMapping(path = "/videoFiles", produces = "application/json", method = RequestMethod.GET)
   public SortedSet<MultimediaFile> getVideoFiles(HttpServletRequest httpServletRequest) {
      final SortedSet<MultimediaFile> videoFiles = new TreeSet<>(VIDEO_FILES_COMPARATOR_DESC);

      LOGGER.info("Get video files request. Remote address: " + httpServletRequest.getRemoteAddr());

      try {
         Files.walkFileTree(FileSystems.getDefault().getPath(videoDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
               if (filePath.getFileName().toString().endsWith(videoFilesExtension) && Files.isReadable(filePath)) {
                  if (LOGGER.isDebugEnabled()) {
                     File file = filePath.toFile();
                     LOGGER.debug("Found file: " + filePath + "; Size: " + (file.length() / 1024 / 1024) + "MB");
                  }

                  File foundFile = filePath.toFile();
                  videoFiles.add(new MultimediaFile(foundFile.getName(),
                        new Date(foundFile.lastModified()), foundFile.length()));
               }
               return FileVisitResult.CONTINUE;
            }
         });
      } catch (IOException e) {
         LOGGER.error(e);
      }
      return videoFiles;
   }

   @RequestMapping(path = "/videoFiles/{fileName:.+}", consumes = "application/json", method = RequestMethod.DELETE)
   public void deleteVideoFile(@PathVariable("fileName") String fileName,
                               HttpServletRequest httpServletRequest) {
      LOGGER.info("Delete video file request. File to be deleted: " + fileName + ". Remote address: " +
            httpServletRequest.getRemoteAddr());

      if (fileName == null) {
         return;
      }

      Path foundFile = findFile(FileSystems.getDefault().getPath(videoDirectory), fileName);

      if (foundFile != null) {
         File file = foundFile.toFile();

         if (file.delete()) {
            LOGGER.info("Deleted video file: " + fileName);
         } else {
            LOGGER.error("Could not delete video file: " + fileName);
         }
      }

      deleteImageFiles(getImagesDirPath(fileName));
   }

   @RequestMapping(path = "/videoFiles/{fileName:.+}", method = RequestMethod.GET)
   public void downloadFile(@PathVariable("fileName") String fileName,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse) {
      LOGGER.info("Downloading video file: " + fileName + ". Remote address: " + httpServletRequest.getRemoteAddr());

      Path foundFile = findFile(FileSystems.getDefault().getPath(videoDirectory), fileName);

      if (foundFile == null) {
         httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
         try {
            int copiedBytes = StreamUtils.copy(Files.newInputStream(foundFile), httpServletResponse.getOutputStream());

            LOGGER.info("Downloaded: " + (copiedBytes / 1024 / 1024) + "MB");
         } catch (IOException e) {
            LOGGER.error(e);
         }
      }
   }

   @RequestMapping(path = "/imageFiles/{videoFileName:.+}", produces = "application/json", method = RequestMethod.GET)
   public SortedSet<Picture> getImageFiles(@PathVariable("videoFileName") final String videoFileName,
                                           HttpServletRequest httpServletRequest,
                                           HttpServletResponse httpServletResponse) {
      LOGGER.info("Get image files request. Remote address: " + httpServletRequest.getRemoteAddr());
      Path imagesDir = getImagesDirPath(videoFileName);
      final SortedSet<Picture> imageFiles = new TreeSet<>(IMAGE_FILES_COMPARATOR_ASC);

      try {
         Files.walkFileTree(imagesDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
               if (LOGGER.isDebugEnabled()) {
                  File file = filePath.toFile();
                  LOGGER.debug("Found file: " + filePath + "; Size: " + (file.length() / 1024) + "KB");
               }

               imageFiles.add(new Picture(filePath.getFileName().toString(), videoFileName));
               return FileVisitResult.CONTINUE;
            }
         });
      } catch (IOException e) {
         LOGGER.error(e);
      }

      if (imageFiles.size() == 0) {
         httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
      }
      return imageFiles;
   }

   private Path findFile(Path path, final String fileName) {
      final List<Path> foundFile = new LinkedList<>();

      try {
         Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
               String foundFileName = filePath.getFileName().toString();

               if (foundFileName.equals(fileName)) {
                  foundFile.add(filePath);
                  return FileVisitResult.TERMINATE;
               }
               return FileVisitResult.CONTINUE;
            }
         });
      } catch (IOException e) {
         LOGGER.error(e);
      }
      return foundFile.size() == 0 ? null : foundFile.get(0);
   }

   private void deleteImageFiles(Path imagesDir) {
      Set<File> filesToBeDeleted = findImageFiles(imagesDir);
      boolean allFilesWereDeleted = true;

      for (File file : filesToBeDeleted) {
         if (!file.delete()) {
            allFilesWereDeleted = false;
            LOGGER.error("Could not delete image file: " + file);
         }
      }

      if (allFilesWereDeleted) {
         if (!imagesDir.toFile().delete()) {
            LOGGER.error("Could not delete images directory: " + imagesDir);
         }
         LOGGER.info(filesToBeDeleted.size() + " image files were deleted");
      }
   }

   private Set<File> findImageFiles(Path dirPath) {
      if (dirPath == null || !dirPath.toFile().isDirectory()) {
         throw new IllegalArgumentException("Parameter is not a directory: " + dirPath);
      }

      final Set<File> foundFiles = new HashSet<>();

      try {
         Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
               foundFiles.add(filePath.toFile());
               return FileVisitResult.CONTINUE;
            }
         });
      } catch (IOException e) {
         LOGGER.error(e);
      }
      return foundFiles;
   }

   private Path getImagesDirPath(String videoFileName) {
      final String imagesDirName = videoFileName.replace(videoFilesExtension, "");
      return FileSystems.getDefault().getPath(videoDirectory + File.separator + imagesDirName);
   }
}
