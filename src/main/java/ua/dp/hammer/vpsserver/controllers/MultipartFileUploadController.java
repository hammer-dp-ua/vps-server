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

import java.io.File;
import java.io.IOException;

@Controller
public class MultipartFileUploadController {
   private static final Logger LOGGER = LogManager.getLogger(MultipartFileUploadController.class);

   @Autowired
   private AppConfig appConfig;

   @RequestMapping(value = "/videoUpload", method = RequestMethod.POST)
   public void handleVideoFileUpload(@RequestParam("name") String name,
                                     @RequestParam("file") MultipartFile file) {
      if (!file.isEmpty()) {
         try {
            LOGGER.info(name + " file is being uploaded");
            File fileDestination = new File(appConfig.getVideoDirectory() + "/" + name);
            file.transferTo(fileDestination);
            LOGGER.info(name + " file has been uploaded. Size: " + (fileDestination.length() / 1024 / 1024) + "MB");
         } catch (IOException e) {
            LOGGER.error(e);
         }
      }
   }
}
