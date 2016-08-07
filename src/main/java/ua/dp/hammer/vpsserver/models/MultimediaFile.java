package ua.dp.hammer.vpsserver.models;

import java.util.Date;

public class MultimediaFile {

   private String name;
   private Date creationDate;
   private long size;

   public MultimediaFile(String name, Date creationDate, long size) {
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

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof MultimediaFile)) {
         return false;
      }

      MultimediaFile thatFile = (MultimediaFile) o;
      return creationDate != null && creationDate.equals(thatFile.getCreationDate());
   }

   @Override
   public int hashCode() {
      return creationDate != null ? creationDate.hashCode() : -1;
   }
}
