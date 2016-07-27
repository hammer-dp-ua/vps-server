package ua.dp.hammer.vpsserver.models;

public class Picture {
   private String name;
   private String uri;

   public Picture(String name, String videoFileName) {
      this.name = name;
      this.uri = "images/" + videoFileName + "/" + name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUri() {
      return uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Picture)) {
         return false;
      }

      Picture thatPicture = (Picture) o;
      return name != null && name.equals(thatPicture.getName());
   }

   @Override
   public int hashCode() {
      return name != null ? name.hashCode() : -1;
   }
}
