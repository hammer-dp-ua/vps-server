package ua.dp.hammer.vpsserver.models;

import java.util.Date;

public class VideoFile {

   private String name;
   private Date creationDate;
   private long size;

   public VideoFile(String name, Date creationDate, long size) {
      this.name = name;
      this.creationDate = creationDate;
      this.size = size;
   }

   public String getName() {
      return name;
   }

   public Date getCreationDate() {
      return creationDate;
   }

   public long getSize() {
      return size;
   }
}
