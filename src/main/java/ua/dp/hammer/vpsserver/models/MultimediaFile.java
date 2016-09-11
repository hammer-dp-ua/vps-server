package ua.dp.hammer.vpsserver.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MultimediaFile {

   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

   private String name;
   private Date creationDateOrig;
   private String creationDate;
   private long size;

   public MultimediaFile(String name, Date creationDateOrig, long size) {
      this.name = name;
      this.creationDateOrig = creationDateOrig;
      this.creationDate = dateFormat.format(creationDateOrig);
      this.size = size;
   }

   public String getName() {
      return name;
   }

   public Date getCreationDateOrig() {
      return creationDateOrig;
   }

   public String getCreationDate() {
      return creationDate;
   }

   public long getSize() {
      return size;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof MultimediaFile)) {
         return false;
      }

      MultimediaFile thatFile = (MultimediaFile) o;
      return creationDateOrig != null && creationDateOrig.equals(thatFile.getCreationDateOrig());
   }

   @Override
   public int hashCode() {
      return creationDateOrig != null ? creationDateOrig.hashCode() : -1;
   }
}
