package ua.dp.hammer.vpsserver.controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.dp.hammer.vpsserver.config.AppConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/multipart")
public class MultipartFileUploadController {
   private static final Logger LOGGER = LogManager.getLogger(MultipartFileUploadController.class);

   @Autowired
   private AppConfig appConfig;

   @RequestMapping(value = "/videoUpload", method = RequestMethod.POST)
   public void handleVideoFileUpload(@RequestParam("file") MultipartFile file,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) {
      try {
         LOGGER.info(file.getOriginalFilename() + " file is ready to be uploaded. Remote address: " +
               httpServletRequest.getRemoteAddr());

         File fileDestination = new File(appConfig.getVideoDirectory() + "/" + file.getOriginalFilename());
         file.transferTo(fileDestination);
         httpServletResponse.setStatus(HttpServletResponse.SC_OK);

         LOGGER.info(file.getOriginalFilename() + " file has been uploaded. Size: " + (fileDestination.length() / 1024 / 1024) + "MB");
      } catch (IOException e) {
         httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         LOGGER.error(e);
      }
   }

   @RequestMapping(value = "/imagesUpload", method = RequestMethod.POST)
   public void handleImageFilesUpload(@RequestParam("videoFileName") String videoFileName,
                                      @RequestParam("file") List<MultipartFile> files,
                                      HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) {
      LOGGER.info("Received image files amount: " + files.size());

      StringBuilder fileNames = new StringBuilder("File names: ");
      for (MultipartFile file : files) {
         fileNames.append(file.getOriginalFilename());
         fileNames.append(";");
      }
      fileNames.deleteCharAt(fileNames.length() - 1);

      LOGGER.info(fileNames + " files are ready to be uploaded. Remote address: " + httpServletRequest.getRemoteAddr());

      int lastIndexOfDot = videoFileName.lastIndexOf('.');

      if (lastIndexOfDot == -1) {
         LOGGER.error("Video file name is without file extension: " + videoFileName);
         httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         return;
      }

      String videoFileNameWithoutExtension = videoFileName.substring(0, lastIndexOfDot);
      File newDirectoryPath = new File(appConfig.getVideoDirectory() + File.separator + videoFileNameWithoutExtension);

      if (!newDirectoryPath.mkdir()) {
         LOGGER.error(newDirectoryPath.getPath() + " directory wasn't created");
         httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         return;
      }

      try {
         for (MultipartFile file : files) {
            file.transferTo(new File(newDirectoryPath + File.separator + file.getOriginalFilename()));

            if (LOGGER.isDebugEnabled()) {
               LOGGER.debug(file.getOriginalFilename() + " file has been saved in " + newDirectoryPath.getPath());
            }
         }
      } catch (IOException e) {
         LOGGER.error("Files could not be saved in " + newDirectoryPath.getPath(), e);
         httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         return;
      }

      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
   }
}
