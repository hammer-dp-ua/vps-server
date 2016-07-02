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
                                     HttpServletResponse httpServletResponse) {
      try {
         LOGGER.info(file.getOriginalFilename() + " file is ready to be uploaded");

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
                                      HttpServletResponse httpServletResponse) throws IOException {
      LOGGER.info("Received image files amount: " + files.size());

      StringBuilder stringBuilder = new StringBuilder("File names: ");
      for (MultipartFile file : files) {
         stringBuilder.append(file.getOriginalFilename());
         stringBuilder.append(";");
      }
      LOGGER.info(stringBuilder);
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
   }
}
